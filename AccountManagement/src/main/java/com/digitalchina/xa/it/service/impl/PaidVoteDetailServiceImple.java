package com.digitalchina.xa.it.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import com.digitalchina.xa.it.contract.PaidVote;
import com.digitalchina.xa.it.dao.EthAccountDAO;
import com.digitalchina.xa.it.dao.PaidVoteDetailDAO;
import com.digitalchina.xa.it.model.EthAccountDomain;
import com.digitalchina.xa.it.model.PaidVoteDetailDomain;
import com.digitalchina.xa.it.service.PaidVoteDetailService;

@Service(value = "paidVoteDetailService")
public class PaidVoteDetailServiceImple implements PaidVoteDetailService{
	private volatile static Web3j web3j;
    private static String address = "0x941b8f05561e57f5b9d366c168b85baf900b037c";
    private static String[] ipArr = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};
//    private static String tempFilePath = "c://";
    private static String tempFilePath = "/eth/temp/";

    @Autowired
    private PaidVoteDetailDAO paidVoteDetailDAO;
    
    @Autowired
    private EthAccountDAO ethAccountDAO;
    
	@Override
	public String voteToSomebody(String toaccount, String fromaccount, String toitcode, String fromitcode,
			String turncount, String remark, Integer topicId) {
		PaidVoteDetailDomain paidVoteDetailDomain = new PaidVoteDetailDomain();
		//投票主题
		paidVoteDetailDomain.setTopicId(topicId);
		//被投票人的相关信息
		paidVoteDetailDomain.setBeVotedAddress(toaccount);
		paidVoteDetailDomain.setBeVotedItcode(toitcode);
		//投票张数
		paidVoteDetailDomain.setNumberOfVotes(Integer.valueOf(turncount));
		//投票人
		paidVoteDetailDomain.setVoteAddress(fromaccount);
		paidVoteDetailDomain.setTransactionStatus(0);
		
		paidVoteDetailDAO.insertPaidVoteDetail(paidVoteDetailDomain);
		
		System.err.println(paidVoteDetailDomain.getId());
		
		//FIXME 这里添加扣费逻辑

		Integer index = (int)(Math.random()*5);
    	String ip = ipArr[index];
		if(web3j==null){
            synchronized (PaidVoteDetailService.class){
                if(web3j==null){
                    web3j =Web3j.build(new HttpService(ip));
                }
            }
        }
		TransactionManager transactionManager = null;
		
		TransactionReceiptProcessor transactionReceiptProcessor = new NoOpProcessor(web3j);
		
		EthAccountDomain ethAccountDomain = new EthAccountDomain();
		ethAccountDomain.setAccount(fromaccount);
		String keystore = ethAccountDAO.selectKeystoreByAccount(ethAccountDomain);
		
		try {
			File keystoreFile = new File(tempFilePath + "temp.json");
	        if(!keystoreFile.exists()){
	        	keystoreFile.createNewFile();
	        }
	        FileWriter fw = new FileWriter(keystoreFile.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	        bw.write(keystore);
	        bw.close();
	        System.out.println("创建keystore成功。。。");
	        
			System.out.println("开始解锁。。。");
			Credentials credentials = WalletUtils.loadCredentials("mini0823", keystoreFile);
			System.out.println("解锁成功。。。");
			keystoreFile.delete();
			System.out.println("删除临时keystore文件成功。。。");
			
			transactionManager = new RawTransactionManager(web3j, credentials, (byte) 10, transactionReceiptProcessor);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CipherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		if(transactionManager == null){
			System.err.println("付费投票模块:构建" + fromitcode + "交易管理异常！");
			return "error";
		}
		
		PaidVote contract = PaidVote.load(address, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		
		try {
			TransactionReceipt transactionReceipt = contract.buyChapter(new Address(toaccount), 
					BigInteger.valueOf(10000000000000000L).multiply(BigInteger.valueOf(Long.valueOf(turncount)))).send();
			
			String result = transactionReceipt.getTransactionHash();
			paidVoteDetailDAO.updateTransactionHash(paidVoteDetailDomain.getId(), result);

			web3j.shutdown();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			web3j.shutdown();
			return "error";
		}
	}

	@Override
	public List<Map<String, Object>> selectTop10(Integer topicId) {
		return paidVoteDetailDAO.selectTop5(topicId);
	}

	@Override
	public List<PaidVoteDetailDomain> selectPaidVoteDetailByBeVotedItcode(String beVotedItcode, Integer topicId) {
		return paidVoteDetailDAO.selectPaidVoteDetailByBeVotedItcode(beVotedItcode, topicId);
	}
}
