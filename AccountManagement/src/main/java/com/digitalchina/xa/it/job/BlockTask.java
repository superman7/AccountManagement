package com.digitalchina.xa.it.job;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.http.HttpService;

import com.digitalchina.xa.it.dao.SystemBlockInfoDAO;
import com.digitalchina.xa.it.model.SystemBlockInfoDomain;
import com.digitalchina.xa.it.util.TConfigUtils;

@Component
public class BlockTask {
	@Autowired
    private SystemBlockInfoDAO systemBlockInfoDAO;
	
	@Transactional
	@Scheduled(fixedRate=1000000)
	public void insertNewBlock(){
		Web3j web3j = Web3j.build(new HttpService(TConfigUtils.selectIp()));
		try {
			System.err.println("插入新的区块信息..........");
			DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(1);
			Block block = web3j.ethGetBlockByNumber(defaultBlockParameter, true).send().getResult();
			
			SystemBlockInfoDomain systemBlockInfoEntity = new SystemBlockInfoDomain();
			systemBlockInfoEntity.setId(block.getNumber().intValue());
			systemBlockInfoEntity.setNumber(block.getNumber().toString());
			systemBlockInfoEntity.setHash(block.getHash());
			systemBlockInfoEntity.setParentHash(block.getParentHash());
			systemBlockInfoEntity.setNonce(block.getNonce().toString());
			systemBlockInfoEntity.setSha3Uncles(block.getSha3Uncles());
			systemBlockInfoEntity.setLogsBloom(block.getLogsBloom());
			systemBlockInfoEntity.setTransactionsRoot(block.getTransactionsRoot());
			systemBlockInfoEntity.setStateRoot(block.getStateRoot());
			systemBlockInfoEntity.setReceiptsRoot(block.getReceiptsRoot());
			systemBlockInfoEntity.setAuthor(block.getAuthor());
			systemBlockInfoEntity.setMiner(block.getMiner());
			systemBlockInfoEntity.setMixHash(block.getMixHash());
			systemBlockInfoEntity.setDifficulty(block.getDifficulty().toString());
			systemBlockInfoEntity.setTotalDifficulty(block.getTotalDifficulty().toString());
			systemBlockInfoEntity.setExtraData(block.getExtraData());
			systemBlockInfoEntity.setSize(block.getSize().toString());
			systemBlockInfoEntity.setGasLimit(block.getGasLimit().toString());
			systemBlockInfoEntity.setGasUsed(block.getGasUsed().toString());
			systemBlockInfoEntity.setTimestamp(block.getTimestamp().toString());
			
			if((!block.getTransactions().equals(null)) && (block.getTransactions().size() > 0)){
				String transactions = "";
				for(TransactionResult temp : block.getTransactions()){
					TransactionObject temp1 = (TransactionObject) temp;
					transactions += temp1.getHash() + ";";
				}
				transactions = transactions.substring(0, transactions.length() - 1);
				systemBlockInfoEntity.setTransactions(transactions);
			}
			
			if((!block.getUncles().equals(null))&& block.getUncles().size() > 0){
				String uncles = "";
				for(String temp : block.getUncles()){
					uncles += temp + ";";
				}
				uncles = uncles.substring(0, uncles.length() -1 );
				systemBlockInfoEntity.setUncles(uncles);
			}

			if((!block.getSealFields().equals(null)) && block.getSealFields().size() > 0){
				String sealFields = "";
				for(String temp : block.getSealFields()){
					sealFields = temp + ";";
				}
				sealFields = sealFields.substring(0, sealFields.length() - 1);
				systemBlockInfoEntity.setSealFields(sealFields);
			}
			systemBlockInfoDAO.insert(systemBlockInfoEntity);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
