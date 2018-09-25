package com.digitalchina.xa.it.kafka;

import org.web3j.tx.response.TransactionReceiptProcessor;

import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.NoOpProcessor;

public class KafkaConsumerProcess {
	public String processor(KafkaConsumerBean bean){
		TransactionReceiptProcessor transactionReceiptProcessor = new NoOpProcessor(bean.getWeb3j());
		TransactionManager transactionManager = null;
		try{
			transactionManager = new RawTransactionManager(bean.getWeb3j(), bean.getCredentials(), KafkaConsumerBean.getChainid(), transactionReceiptProcessor);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("构建交易管理异常！");
			return "error, build transaction manager failed.";
		}
		
		Qiandao contract = Qiandao.load(bean.getAddress(), bean.getWeb3j(), transactionManager, bean.getGasPrice(), bean.getGasLimit());
		
		TransactionReceipt transactionReceipt;
		try {
			transactionReceipt = contract.qiandaoReward(new Uint256(bean.getTurnBalance())).send();
			
			String resultHash = transactionReceipt.getTransactionHash();
			
			//FIXME 此处添加根据bean.getTransactionDetailId()更新resultHash。若 resultHash == null则为失败。需重新发送

			return resultHash;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error, transaction hash is null.";
		}
	}
}
