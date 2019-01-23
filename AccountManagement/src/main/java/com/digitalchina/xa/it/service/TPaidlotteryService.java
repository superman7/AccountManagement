package com.digitalchina.xa.it.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;
import com.digitalchina.xa.it.model.TPaidlotteryInfoDomain;

public interface TPaidlotteryService {
	//购买奖票时插入基本信息
	int insertLotteryBaseInfo(TPaidlotteryDetailsDomain tPaidlotteryDetailsDomain);
	
	//交易成功后更新hashcode，并判断是否开奖
	Boolean updateHashcodeAndJudge(String hashcode, int transactionId);
	
	//传入lotteryInfoId,itcode,hashcode,生成ticket
	String generateTicket(int lotteryId, String itcode, String hashcode);
		
	//传入lotteryInfoId,生成win ticket
	List<String> generateWinTicket(int lotteryId, int winCount);
	
	//传入lotteryInfoId和option(backup4),生成win ticket
	List<String> generateWinTicketNew(int lotteryId, int winCount, int option);
	
	//更新nowSumAmount、backup4
	Boolean updateNowSumAmountAndBackup4(int id);
	
	//单项类开奖（定时任务自动）
	void runALottery(TPaidlotteryInfoDomain tpid);
	
	//双项类开奖（手动提交结果，结果为1/2，对应info中backup1和backup2选项）
	void runOptionLottery(Integer lotteryId, Integer option);
	
	/*******************Info***********************/
	//获取结束或未结束的抽奖
	List<TPaidlotteryInfoDomain> selectLotteryInfoByFlag(int flag);
	
	//根据id获取抽奖Info
	TPaidlotteryInfoDomain selectLotteryInfoById(int id);
	
	//获取smb夺宝
	TPaidlotteryInfoDomain selectOneSmbTpid();
	
	//获取红包夺宝
	List<TPaidlotteryInfoDomain> selectHbTpids();
		
	//获取其他夺宝
	List<TPaidlotteryInfoDomain> selectOtherTpids();
	
	//获取最近揭晓
	List<TPaidlotteryInfoDomain> selectNewOpen(int count);
	
	Boolean updateLotteryReward(int id, String reward);
	
	/******************Detail**********************/
	//根据itcode查询某用户的抽奖记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcode(String itcode);
	
	//根据itcode、lotteryId查询某用户的某次抽奖购买记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcodeAndLotteryId(String itcode, int lotteryId);
	
	//根据itcode、lotteryId查询某用户的某次抽奖被邀请
	List<TPaidlotteryDetailsDomain> selectInviteLotteryDetailsByItcodeAndLotteryId(String itcode, int lotteryId);
	
	//根据itcode、lotteryId查询某用户的某次抽奖购买记录
	List<TPaidlotteryDetailsDomain> selectUninviteLotteryDetailsByItcodeAndLotteryId(String itcode, int lotteryId);
	
	//根据lotteryId查询某次抽奖的参与用户信息
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByLotteryId(int lotteryId);
	
	//根据id查询
	TPaidlotteryDetailsDomain selectLotteryDetailsById(int id);
	
	//根据itcode获取不同result的参与记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcodeAndResult(String itcode,int result);
	
	//查询最近的红包抽奖typeCode=0
	Integer selectLastRMBLottery();
	
	//根据itcode、lotteryId、backup4>4查询该用户已邀请条数
	List<TPaidlotteryDetailsDomain> selectHaveInvitedByItcodeAndLotteryId(String itcode, int lotteryId);
	
	//根据itcode、invitedItcode、lotteryId、backup4>4查询用户是否已邀请invitedItcode
	List<TPaidlotteryDetailsDomain> selectIfInvitedByItcodeAndLotteryId(String itcode, String invitedItcode, int lotteryId);

	List<TPaidlotteryDetailsDomain> selectAcceptInviteLotteryDetailsByItcodeAndLotteryId(String itcode, int lotteryId);
}
