package com.digitalchina.xa.it.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TPaidlotteryInfoDomain;

public interface TPaidlotteryInfoDAO {
	//获取结束或未结束的抽奖
	List<TPaidlotteryInfoDomain> selectLotteryInfoByFlag(@Param("flag")int flag);
	
	//根据id获取抽奖
	TPaidlotteryInfoDomain selectLotteryInfoById(@Param("id")int id);
	
	//更新nowSumAmount
	int updateNowSumAmountAndBackup4(@Param("id")int id);
	
	//交易确认后更新backup4
	int updateBackup4AfterDeal(@Param("id")int id);
	
	//更新flag，lotteryTime，winner，winTicket
	int updateAfterLotteryFinished(@Param("id")int id, @Param("lotteryTime")Timestamp lotteryTime, @Param("winner")String winner, @Param("winTicket")String winTicket);
	
	//获取需要开奖的抽奖
	List<TPaidlotteryInfoDomain> selectRunLottery();
}
