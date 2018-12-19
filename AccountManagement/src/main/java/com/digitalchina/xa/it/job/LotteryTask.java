package com.digitalchina.xa.it.job;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.digitalchina.xa.it.dao.CashBackDAO;
import com.digitalchina.xa.it.dao.PaidVoteDetailDAO;
import com.digitalchina.xa.it.dao.TPaidlotteryDetailsDAO;
import com.digitalchina.xa.it.dao.TPaidlotteryInfoDAO;
import com.digitalchina.xa.it.dao.VirtualMachineDAO;
import com.digitalchina.xa.it.dao.WalletAccountDAO;
import com.digitalchina.xa.it.dao.WalletTransactionDAO;
import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;
import com.digitalchina.xa.it.model.TPaidlotteryInfoDomain;
import com.digitalchina.xa.it.service.TPaidlotteryService;

import scala.util.Random;

@Component
public class LotteryTask {
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
	
	private static String[] ip = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};
	

	@Autowired
    private TPaidlotteryDetailsDAO tPaidlotteryDetailsDAO;
	@Autowired
    private TPaidlotteryInfoDAO tPaidlotteryInfoDAO;
	@Autowired
    private TPaidlotteryService tPaidlotteryService;
	
	@Scheduled(fixedRate=15000)
	public void updateTurnResultStatusJob(){
		System.err.println("定时任务_更新超时写入中交易状态");
		tPaidlotteryDetailsDAO.updateLotteryDetailsWhereTimeOut();
		
		Web3j web3j = Web3j.build(new HttpService(ip[new Random().nextInt(5)]));
		List<TPaidlotteryDetailsDomain> wtdList = tPaidlotteryDetailsDAO.selectLotteryDetailsWhereHashIsNotNullAndBackup3Is0();
		if(wtdList == null) {
			web3j.shutdown();
			return;
		}
		try {
			for(int i = 0; i < wtdList.size(); i++) {
				String transactionHash = wtdList.get(i).getHashcode();
				System.out.println("定时任务_链上未确认_transactionHash:" + transactionHash);
				TransactionReceipt tr = web3j.ethGetTransactionReceipt(transactionHash).sendAsync().get().getResult();
				if(tr == null) {
					System.out.println(transactionHash + "仍未确认，查询下一个未确认交易");
					continue;
				}
				if(!tr.getBlockHash().contains("00000000")) {
					System.out.println("更新ID为" + wtdList.get(i).getId() + "的交易状态为已完成");
					tPaidlotteryService.updateHashcodeAndJudge(transactionHash, wtdList.get(i).getId());
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
	
	@Transactional
	@Scheduled(cron="20,50 * * * * ?")
	public void runLottery(){
		List<TPaidlotteryInfoDomain> tpidList = tPaidlotteryInfoDAO.selectRunLottery();
		if(tpidList.size() == 0) {
			return;
		}
		TPaidlotteryInfoDomain tpid = tpidList.get(0);
		tPaidlotteryService.runALottery(tpid);
	}
	
	@Transactional
	@Scheduled(cron="5,35 * * * * ?")
	public void lotteryControl(){
		List<TPaidlotteryInfoDomain> tpidList = tPaidlotteryInfoDAO.selectUnfinishedLottery();
		if(tpidList.size() == 0) {
			return;
		}
		for(int index = 0; index < tpidList.size(); index++) {
			TPaidlotteryInfoDomain tpid = tpidList.get(index);
			int count1 = tPaidlotteryDetailsDAO.selectCountByBackup3(tpid.getId(), 1);
			List<TPaidlotteryDetailsDomain> errorList = tPaidlotteryDetailsDAO.selectDetailByBackup3(tpid.getId(), 2);
			if(errorList.size() > 0) {
				//更新Info表nowSumAmount，backup4
				tPaidlotteryInfoDAO.updateNowSumAmountAndBackup4Sub(tpid.getId(), errorList.size());
				for(int j = 0; j < errorList.size(); j++) {
					tPaidlotteryDetailsDAO.updateBackup3From2To3(errorList.get(j).getId());
				}
			}
			if(count1 == tpid.getBackup4()) {
				tPaidlotteryInfoDAO.updateBackup4To0(tpid.getId());
			}
		}
	}
}
