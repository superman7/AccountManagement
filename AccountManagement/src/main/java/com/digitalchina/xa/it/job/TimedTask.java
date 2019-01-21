package com.digitalchina.xa.it.job;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.digitalchina.xa.it.dao.CashBackDAO;
import com.digitalchina.xa.it.dao.PaidVoteDetailDAO;
import com.digitalchina.xa.it.dao.TConfigDAO;
import com.digitalchina.xa.it.dao.TopicDAO;
import com.digitalchina.xa.it.dao.VirtualMachineDAO;
import com.digitalchina.xa.it.dao.WalletAccountDAO;
import com.digitalchina.xa.it.dao.WalletTransactionDAO;
import com.digitalchina.xa.it.model.TopicDomain;
import com.digitalchina.xa.it.model.VirtualMachineDomain;
import com.digitalchina.xa.it.model.WalletTransactionDomain;
import com.digitalchina.xa.it.service.SigninRewardService;
import com.digitalchina.xa.it.service.TConfigService;
import com.digitalchina.xa.it.service.TopicOptionService;
import com.digitalchina.xa.it.service.TopicService;
import com.digitalchina.xa.it.service.VoteService;
import com.digitalchina.xa.it.util.HttpRequest;
import com.digitalchina.xa.it.util.TConfigUtils;

import scala.util.Random;

@Component
public class TimedTask {
	@Autowired
    private WalletAccountDAO walletAccountDAO;
	@Autowired
	private WalletTransactionDAO walletTransactionDAO;
	
	@Autowired
	private PaidVoteDetailDAO paidVoteDetailDAO;
	@Autowired
	private CashBackDAO cashBackDAO;
	@Autowired
	private VirtualMachineDAO virtualMachineDAO;
	@Autowired
	private TConfigDAO tconfigDAO;
	
	@Autowired
	private TopicService topicService;
	@Autowired
	private TopicOptionService topicOptionService;
	@Autowired
	private VoteService voteService;
	@Autowired
    private TopicDAO topicDAO;
	
/*	@Autowired
	private SigninRewardService srService;*/

	@Transactional
	@Scheduled(cron="55 59 23 * * ?")
//	每周六更新
//	@Scheduled(cron="0 40 21 ? * SAT")
	
//	更换主题投票
	public void updateVoteTopic(){
		System.out.println("执行定时任务");
		
		Calendar localTime = Calendar.getInstance();
		int z = localTime.get(Calendar.DATE);
		
		List<TopicDomain> perList = topicDAO.selectPersonalityAsc();
		List<TopicDomain> topicList = topicDAO.selectTopicToday();
		List<TopicDomain> newsList = topicDAO.selectNewsAsc();
		if(topicList.size() == 0){
			return;
		}
		TopicDomain topic = topicList.get(0);
		topicDAO.updateAvailableBefore(topic.getId());
		int nextTopicId;
		if(z == 1){
			nextTopicId = topicDAO.selectNextTopicStock();
		}else{
			if(newsList.size() != 0){
				nextTopicId = newsList.get(0).getId();
			}else if(perList.size() != 0) {
				nextTopicId = perList.get(0).getId();
			} else {
				nextTopicId = topicDAO.selectNextTopic();
			}
		}
		System.out.println("新topicid为：" + nextTopicId);
		topicDAO.updateAvailable(nextTopicId);
	}

	@Transactional
	@Scheduled(cron="10,40 * * * * ?")
	public void updateTranscationStatus(){
		Web3j web3j = Web3j.build(new HttpService(TConfigUtils.selectIp()));
		List<WalletTransactionDomain> wtdList = walletTransactionDAO.selectHashAndAccounts();
		if(wtdList == null) {
			web3j.shutdown();
			return;
		}
		try {
			for(int i = 0; i < wtdList.size(); i++) {
				String transactionHash = wtdList.get(i).getTransactionHash();
				System.out.println("定时任务_链上未确认_transactionHash:" + transactionHash);
				TransactionReceipt tr = web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get().getResult();
				if(tr == null) {
					System.out.println(transactionHash + "仍未确认，查询下一个未确认交易");
					continue;
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
		} finally {
			web3j.shutdown();
		}
	}
	
/*	//每天8点30分30秒执行前一天考勤奖励
	@Transactional
	@Scheduled(cron="30 30 08 * * ?")
//	@Scheduled(cron="55 30 14 * * ?")
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
//		HttpRequest.sendGet("http://10.0.5.217:8080/eth/attendanceReward/" + result, "");

		srService.attendanceReward(result);
	}
	*/

	@Scheduled(fixedRate=15000)
	public void updateTurnResultStatusJob(){
    	String ipAddress = TConfigUtils.selectIp();
		
		Admin admin = Admin.build(new HttpService(ipAddress));
		
		List<String> list1 = paidVoteDetailDAO.selectUnfinishedTransaction();
		if(list1.size() > 0){
			System.err.println("更新交易状态时以太坊链接的ip为"+ipAddress);
			for (String transactionHash : list1) {
				if(transactionHash == ""){
					continue;
				}
				EthTransaction ethTransaction = null;
				try {
					ethTransaction = admin.ethGetTransactionByHash(transactionHash).sendAsync().get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				org.web3j.protocol.core.methods.response.Transaction result = ethTransaction.getResult();
				if(result == null){
					System.err.println(transactionHash);
					continue;
				}
		    	String blockHash = result.getBlockHash();
		    	System.err.println("blockHash为"+blockHash);
		    	//更新状态
		    	if(!blockHash.contains("0000000000000000000")){
		    		paidVoteDetailDAO.updateTransactionStatus(transactionHash);
		    	}
			}
		}
	}
	
	//每天7:30:30返还前一天购买虚拟机神州币
	@Transactional
	@Scheduled(cron="30 30 07 * * ?")
//	@Scheduled(cron="5 * * * * ?")
	public void backToUser(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		String startTime = sdf.format(date) + " 00:00:00";
		String endTime = sdf.format(date) + " 23:59:59";
		
		System.out.println(startTime + "***" + endTime);
		
		System.err.println("开始查询返现用户...");
		String url = TConfigUtils.selectValueByKey("kafka_address") + "/cashBack/processDeduction";
		List<VirtualMachineDomain> cashBackUsers = virtualMachineDAO.selectCashBackUsers(startTime, endTime);
		Map<String, Integer> cashBackMap = new HashMap<String, Integer>();
		
		for(int index = 0; index < cashBackUsers.size(); index++) {
			String itcode = cashBackUsers.get(index).getUserItcode();
			String cashValue =  cashBackUsers.get(index).getSpare2();
			Integer cash = Integer.valueOf(cashValue.substring(0, cashValue.indexOf("神")));
			if(cashBackMap.get(itcode) == null || cashBackMap.get(itcode) < cash) {
				cashBackMap.put(itcode, cash);
			}
		}
		
		for(String key : cashBackMap.keySet()) {
			Integer flag = cashBackDAO.selectLimitFlagByItcode(key);
			if(flag == null) {
				//最高返现额暂定300SZB
				Integer turnBalance = cashBackMap.get(key) > 300 ? 300 : cashBackMap.get(key);
				String postParam = "itcode=" + key + "&turnBalance=" + turnBalance;
				HttpRequest.sendPost(url, postParam);
				System.out.println(postParam);
			}
		}
	}
	
	//检查区块链节点工作状态是否正常
	@Transactional
	@Scheduled(cron="5 * * * * ?")
	public void checkEthNodes(){
		String textAddress = "0x8a950e851344715a51036567ca1b44aab3f15110";
		List<String> ipArr = TConfigUtils.selectIpArr();
		for(int index = 0; index < ipArr.size(); index++) {
			Web3j web3j =Web3j.build(new HttpService(ipArr.get(index)));
			try {
				web3j.ethGetBalance(textAddress,DefaultBlockParameterName.LATEST).send().getBalance();
				tconfigDAO.UpdateEthNodesStatus(ipArr.get(index), true);
	        } catch (IOException e) {
	        	if(e.getMessage().contains("Failed to connect to")) {
	        		System.out.println(e.getMessage());
	        		tconfigDAO.UpdateEthNodesStatus(ipArr.get(index), false);
	        	}
			}
		}
	}
	
//	//为新用户
//	@Transactional
//	@Scheduled(cron="30 30 07 * * ?")
//	public void creatKeystore(){
//		String textAddress = "0x8a950e851344715a51036567ca1b44aab3f15110";
//		String[] ipArr = TConfigUtils.selectIpArr();
//		for(int index = 0; index < ipArr.length; index++) {
//			Web3j web3j =Web3j.build(new HttpService(ipArr[index]));
//			try {
//				web3j.ethGetBalance(textAddress,DefaultBlockParameterName.LATEST).send().getBalance();
//				tconfigDAO.UpdateEthNodesStatus(ipArr[index], 1);
//	        } catch (IOException e) {
//	        	if(e.getMessage().contains("Failed to connect to")) {
//	        		System.out.println(e.getMessage());
//	        		tconfigDAO.UpdateEthNodesStatus(ipArr[index], 0);
//	        	}
//			}
//		}
//	}
}
