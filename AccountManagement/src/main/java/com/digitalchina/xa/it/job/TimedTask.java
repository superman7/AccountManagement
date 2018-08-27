package com.digitalchina.xa.it.job;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.digitalchina.xa.it.dao.EthAccountDAO;
import com.digitalchina.xa.it.dao.WalletAccountDAO;
import com.digitalchina.xa.it.dao.WalletTransactionDAO;
import com.digitalchina.xa.it.model.WalletTransactionDomain;
import com.digitalchina.xa.it.service.WalletTransactionService;

import scala.util.Random;

@Component
public class TimedTask {
	@Autowired
    private WalletAccountDAO walletAccountDAO;
	@Autowired
	private WalletTransactionDAO walletTransactionDAO;
	private static String[] ip = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};

	@Transactional
	@Scheduled(cron="10,40 * * * * ?")
	public void updateVoteTopic(){
		Web3j web3j = Web3j.build(new HttpService(ip[new Random().nextInt(5)]));
		List<WalletTransactionDomain> wtdList = walletTransactionDAO.selectHashAndAccounts();
		if(wtdList == null) {
			return;
		}
		try {
			for(int i = 0; i < wtdList.size(); i++) {
				String transactionHash = wtdList.get(i).getTransactionHash();
				TransactionReceipt tr = web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get().getResult();
				if(tr == null) {
					return;
				}
				if(!tr.getBlockHash().contains("00000000")) {
					String accountFrom = wtdList.get(i).getAccountFrom();
					String accountTo = wtdList.get(i).getAccountTo();
					
					BigInteger balanceFrom = web3j.ethGetBalance(accountFrom,DefaultBlockParameterName.LATEST).send().getBalance();
					BigInteger balanceTo = web3j.ethGetBalance(accountTo,DefaultBlockParameterName.LATEST).send().getBalance();
					
					BigInteger gasUsed = tr.getGasUsed();
					BigInteger blockNumber = tr.getBlockNumber();
					WalletTransactionDomain wtd = new WalletTransactionDomain();
					wtd.setTransactionHash(transactionHash);
					wtd.setBalanceFrom(Double.parseDouble(balanceFrom.toString()));
					wtd.setBalanceTo(Double.parseDouble(balanceTo.toString()));
					wtd.setGas(Double.valueOf(gasUsed.toString()));
					wtd.setConfirmBlock(Integer.valueOf(blockNumber.toString()));
					wtd.setStatus(1);
					walletTransactionDAO.updateByTransactionHash(wtd);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//每天8点30分30秒执行前一天考勤奖励
	@Transactional
	@Scheduled(cron="30 30 08 * * ?")
//	@Scheduled(cron="50 38 17 * * ?")
	public void sendAttendanceRewards(){
		System.err.println("开始考勤奖励员工编号获取...");
		String result = "";
		List<String> resultList = walletAccountDAO.selectUserNoAfter21();
		resultList.addAll(walletAccountDAO.selectUserNoBefore8());
		
		for (String string : resultList) {
			result += (string + ",");
		}
		result = result.substring(0, result.length() - 1 );
		//FIXME 发送get请求，http://http//10.0.5.217:8080/eth/attendanceReward/result
	}
}
