package com.digitalchina.xa.it.job;

import java.sql.Timestamp;
import java.util.Date;
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
import com.digitalchina.xa.it.dao.TConfigDAO;
import com.digitalchina.xa.it.dao.TPaidlotteryDetailsDAO;
import com.digitalchina.xa.it.dao.TPaidlotteryInfoDAO;
import com.digitalchina.xa.it.dao.VirtualMachineDAO;
import com.digitalchina.xa.it.dao.WalletAccountDAO;
import com.digitalchina.xa.it.dao.WalletTransactionDAO;
import com.digitalchina.xa.it.model.TConfigDomain;
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
	private TConfigDAO tconfigDAO;
	@Autowired
	private TPaidlotteryInfoDAO tpaidLotteryInfoDAO;
	
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
	
	//定时更新抽奖Detail的链上状态
	@Transactional
	@Scheduled(fixedRate=10000)
	public void updateTurnResultStatusJob(){
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
	//遇到交易异常的状态，回退开奖信息记录的状态
	@Transactional
	@Scheduled(fixedRate=30000)
	public void lotteryUnfinishedUpdate(){
		//查询未结束的抽奖
		List<TPaidlotteryInfoDomain> tpidList = tPaidlotteryInfoDAO.selectUnfinishedLottery();
		if(tpidList.size() == 0) {
			return;
		}
		for(int index = 0; index < tpidList.size(); index++) {
			TPaidlotteryInfoDomain tpid = tpidList.get(index);
			//查询抽奖details中，区块链交易已失败的个数
			List<TPaidlotteryDetailsDomain> errorList = tPaidlotteryDetailsDAO.selectDetailByBackup3(tpid.getId(), 2);
			if(errorList.size() > 0) {
				//更新Info表nowSumAmount，backup4
				//将已失败交易的金额累计清除，更新交易状态为3
				System.out.println("将LotteryID-" + tpid.getId() + "-已失败交易的金额累计清除，更新交易状态为3,清除金额为-" + errorList.size() + "*单价");
				tPaidlotteryInfoDAO.updateNowSumAmountAndBackup4Sub(tpid.getId(), errorList.size());
				for(int j = 0; j < errorList.size(); j++) {
					tPaidlotteryDetailsDAO.updateBackup3From2To3(errorList.get(j).getId());
				}
			}
		}
	}

	//定时处理需要开奖的信息
	@Transactional
	@Scheduled(cron="20,50 * * * * ?")
	public void runLottery(){
		//开奖条件：1.flag = 0；2.winSumAmount = nowSumAmount；3.backup4 = 0
		List<TPaidlotteryInfoDomain> tpidList0 = tPaidlotteryInfoDAO.selectRunLottery();
		if(tpidList0.size() == 0) {
			return;
		}
		TPaidlotteryInfoDomain tpid0 = tpidList0.get(0);
		tPaidlotteryService.runALottery(tpid0);
	}
	
	//将开奖信息更新为待开奖
	@Transactional
	@Scheduled(cron="5,35 * * * * ?")
	public void lotteryControl(){
		//查询未结束的抽奖
		List<TPaidlotteryInfoDomain> tpidList = tPaidlotteryInfoDAO.selectUnfinishedLottery();
		if(tpidList.size() == 0) {
			return;
		}
		for(int index = 0; index < tpidList.size(); index++) {
			TPaidlotteryInfoDomain tpid = tpidList.get(index);
			//查询抽奖details中，区块链交易已确认的个数
			int count1 = tPaidlotteryDetailsDAO.selectCountByBackup3(tpid.getId(), 1);
			if(count1 >= (tpid.getWinSumAmount() / tpid.getUnitPrice())) {
				tPaidlotteryInfoDAO.updateBackup4To0(tpid.getId());
			}
		}
	}
	
	//创建神州币抽奖
	@Transactional
	@Scheduled(fixedRate=60000)
	public void lotterySZBCreate(){
		System.err.println("插入新的SZB抽奖信息...");
		List<TPaidlotteryInfoDomain> tpidList = tPaidlotteryInfoDAO.selectUnfinishedSZBLottery();
		if(tpidList.size() > 0) {
			return;
		}
		TPaidlotteryInfoDomain tpid = new TPaidlotteryInfoDomain();
		List<TConfigDomain> tconfigList = tconfigDAO.selectConfigByExtra("LotterySzbInfo");
		String lotteryInfo = tconfigList.get((int) (Math.random() * tconfigList.size())).getCfgValue();
		
		String[] infoList = lotteryInfo.split("##");
		
		tpid.setName(infoList[0]);
		tpid.setDescription(infoList[1]);
		tpid.setWinSumAmount(Integer.valueOf(infoList[2]));
		tpid.setWinSumPerson(Integer.valueOf(infoList[3]));
		tpid.setReward(infoList[4]);
		tpid.setUnitPrice(Integer.valueOf(infoList[5]));
		tpid.setLimitEveryday(Integer.valueOf(infoList[6]));
		tpid.setWinCount(Integer.valueOf(infoList[7]));
		
		tpid.setFlag(0);
		//1为神州币抽奖
		tpid.setTypeCode(1);
		tpid.setNowSumAmount(0);
		tpid.setBackup4(0);
		tpid.setBackup5(0);
		tpid.setLotteryTime(new Timestamp(new Date().getTime()));
		
		tpid.setNowSumPerson(0);
		tpid.setWinDate("");
		tpid.setBackup1("");
		tpid.setBackup2("");
		tpid.setBackup3("");
		tpaidLotteryInfoDAO.insertLotteryInfo(tpid);
	}
	
	//创建人民币抽奖
	@Transactional
	@Scheduled(cron="00 30 09 * * ?")
	public void lotteryRMBCreate(){
		//查询上一日抽奖是否已完成
		List<TPaidlotteryInfoDomain> tpidList = tPaidlotteryInfoDAO.selectUnfinishedSZBLottery();
		if(tpidList.size() > 0) {
			return;
		}
		System.err.println("插入当日的RMB抽奖信息...");
		
		TPaidlotteryInfoDomain tpid = new TPaidlotteryInfoDomain();
		List<TConfigDomain> tconfigList = tconfigDAO.selectConfigByExtra("LotteryRmbInfo");
		String lotteryInfo = tconfigList.get((int) (Math.random() * tconfigList.size())).getCfgValue();
		String[] infoList = lotteryInfo.split("##");
		
		tpid.setName(infoList[0]);
		tpid.setDescription(infoList[1]);
		tpid.setWinSumAmount(Integer.valueOf(infoList[2]));
		tpid.setWinSumPerson(Integer.valueOf(infoList[3]));
		tpid.setReward(infoList[4]);
		tpid.setUnitPrice(Integer.valueOf(infoList[5]));
		tpid.setLimitEveryday(Integer.valueOf(infoList[6]));
		tpid.setWinCount(Integer.valueOf(infoList[7]));
		
		tpid.setFlag(0);
		//0为现金红包抽奖
		tpid.setTypeCode(0);
		tpid.setNowSumAmount(0);
		tpid.setBackup4(0);
		tpid.setBackup5(0);
		tpid.setLotteryTime(new Timestamp(new Date().getTime()));
		
		tpid.setNowSumPerson(0);
		tpid.setWinDate("");
		tpid.setBackup1("");
		tpid.setBackup2("");
		tpid.setBackup3("");
		tpaidLotteryInfoDAO.insertLotteryInfo(tpid);
	}
}
