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
public class SystemTransactionTask {
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
	
	@Transactional
//	@Scheduled(fixedRate=30000)
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
}
