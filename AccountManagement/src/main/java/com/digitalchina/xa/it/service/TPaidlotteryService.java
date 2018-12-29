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
	
	//更新nowSumAmount、backup4
	Boolean updateNowSumAmountAndBackup4(int id);
	
	//开奖
	void runALottery(TPaidlotteryInfoDomain tpid);
	
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
	
	/******************Detail**********************/
	//根据itcode查询某用户的抽奖记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcode(String itcode);
	
	//根据itcode、lotteryId查询某用户的某次抽奖购买记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcodeAndLotteryId(String itcode, int lotteryId);
	
	//根据lotteryId查询某次抽奖的参与用户信息
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByLotteryId(int lotteryId);
	
	//根据id查询
	TPaidlotteryDetailsDomain selectLotteryDetailsById(int id);
	
	//根据itcode获取不同result的参与记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcodeAndResult(String itcode,int result);
	
	//查询最近的红包抽奖typeCode=0
	Integer selectLastRMBLottery();
}
