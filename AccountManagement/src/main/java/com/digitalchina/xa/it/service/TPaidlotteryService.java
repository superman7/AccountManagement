package com.digitalchina.xa.it.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;
import com.digitalchina.xa.it.model.TPaidlotteryInfoDomain;

public interface TPaidlotteryService {
	//购买奖票时插入基本信息
	int insertLotteryBaseInfo(TPaidlotteryDetailsDomain tPaidlotteryDetailsDomain);
	
	//交易成功后更新hashcode，并判断是否开奖
	int updateHashcodeAndJudge(String hashcode, String transactionId);
	
	/*Info*/
	//获取结束或未结束的抽奖
	List<TPaidlotteryInfoDomain> selectLotteryInfoByFlag(int flag);
	
	//根据id获取抽奖Info
	TPaidlotteryInfoDomain selectLotteryInfoById(int id);
	
	/*Detail*/
	//根据itcode查询某用户的抽奖记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcode(String itcode);
	
	//根据itcode、lotteryId查询某用户的某次抽奖购买记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcodeAndLotteryId(String itcode, int lotteryId);
	
	//根据lotteryId查询某次抽奖的参与用户信息
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByLotteryId(int lotteryId);
}
