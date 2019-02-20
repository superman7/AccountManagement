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
import com.digitalchina.xa.it.dao.SystemTransactionDetailDAO;
import com.digitalchina.xa.it.dao.TConfigDAO;
import com.digitalchina.xa.it.dao.TopicDAO;
import com.digitalchina.xa.it.dao.VirtualMachineDAO;
import com.digitalchina.xa.it.dao.WalletAccountDAO;
import com.digitalchina.xa.it.dao.WalletTransactionDAO;
import com.digitalchina.xa.it.model.SystemTransactionDetailDomain;
import com.digitalchina.xa.it.model.TopicDomain;
import com.digitalchina.xa.it.model.VirtualMachineDomain;
import com.digitalchina.xa.it.model.WalletTransactionDomain;
import com.digitalchina.xa.it.service.SigninRewardService;
import com.digitalchina.xa.it.service.SystemTransactionDetailService;
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
	private SystemTransactionDetailService systemTransactionDetailService;
	
	@Autowired
	private SystemTransactionDetailDAO systemTransactionDetailDAO;
	
	
	@Transactional
	@Scheduled(fixedRate=20000)
	public void updateTranscationStatus(){
		systemTransactionDetailDAO.updateTransactionDetailsWhereTimeOut();
		
		Web3j web3j = Web3j.build(new HttpService(TConfigUtils.selectIp()));
		List<SystemTransactionDetailDomain> wtdList = systemTransactionDetailDAO.selectTransactionDetailWhereHashIsNotNullAndTimerIs0();
		if(wtdList == null) {
			web3j.shutdown();
			return;
		}
		try {
			for(int i = 0; i < wtdList.size(); i++) {
				String transactionHash = wtdList.get(i).getTurnhash();
				System.out.println("定时任务_链上未确认_transactionHash:" + transactionHash);
				TransactionReceipt tr = web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get().getResult();
				if(tr == null) {
					System.out.println(transactionHash + "仍未确认，查询下一个未确认交易");
					continue;
				}
				if(!tr.getBlockHash().contains("00000000000")) {
					System.out.println("更新ID为" + wtdList.get(i).getId() + "的交易状态为已完成");
					//更新timer
					systemTransactionDetailService.updateTimerTo2(wtdList.get(i).getId());
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			web3j.shutdown();
		}
	}
}
