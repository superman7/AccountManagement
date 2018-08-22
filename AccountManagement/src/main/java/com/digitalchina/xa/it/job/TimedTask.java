package com.digitalchina.xa.it.job;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.digitalchina.xa.it.dao.WalletAccountDAO;
import com.digitalchina.xa.it.model.WalletTransactionDomain;
import com.digitalchina.xa.it.service.WalletTransactionService;
import com.digitalchina.xa.it.util.HttpRequest;

import scala.util.Random;

@Component
public class TimedTask {
	@Autowired
    private WalletAccountDAO walletAccountDAO;
	@Autowired
	private WalletTransactionService walletTransactionService;
	private static String[] ip = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};

	@Transactional
	@Scheduled(cron="10,40 * * * * ?")
	public void updateVoteTopic(){
		Web3j web3j = Web3j.build(new HttpService(ip[new Random().nextInt(5)]));
		List<String> transactionHashAll = walletTransactionService.selectTransactionHashUnconfirm();
		if(transactionHashAll == null) {
			return;
		}
		try {
			for(int i = 0; i < transactionHashAll.size(); i++) {
				String transactionHash = transactionHashAll.get(i);
				TransactionReceipt tr = web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get().getResult();
				if(tr == null) {
					return;
				}
				if(!tr.getBlockHash().contains("00000000")) {
					BigInteger gasUsed = tr.getGasUsed();
					BigInteger blockNumber = tr.getBlockNumber();
					WalletTransactionDomain wtd = new WalletTransactionDomain();
					wtd.setTransactionHash(transactionHash);
					wtd.setGas(Double.valueOf(gasUsed.toString()));
					wtd.setConfirmBlock(Integer.valueOf(blockNumber.toString()));
					wtd.setStatus(1);
					walletTransactionService.updateByTransactionHash(wtd);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	//每天8点30分30秒执行前一天考勤奖励
	@Transactional
//	@Scheduled(cron="30 30 08 * * ?")
	@Scheduled(cron="55 30 14 * * ?")
	public void sendAttendanceRewards(){
		System.err.println("开始考勤奖励员工编号获取...");
		String result = "";
		List<String> resultList = walletAccountDAO.selectUserNoAfter21();
		resultList.addAll(walletAccountDAO.selectUserNoBefore8());
		
		for (String string : resultList) {
			result += (string + ",");
		}
		if(result == ""){
			return;
		}
		result = result.substring(0, result.length() - 1 );
		System.err.println(result);
		//FIXME 发送get请求，http://10.0.5.217:8080/eth/attendanceReward/result
		HttpRequest.sendGet("http://10.0.5.217:8080/eth/attendanceReward/" + result, "");
	}
}
