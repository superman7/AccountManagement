package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;

public interface TPaidlotteryDetailsDAO {
	int insertLotteryBaseInfo(TPaidlotteryDetailsDomain tPaidlotteryDetailsDomain);
	
	int updateHashcode(@Param("hashcode")String hashcode, @Param("ticket")String ticket, @Param("transactionId")String transactionId);
	
	List<String> generateWinTicket(@Param("lotteryId")int lotteryId, @Param("winCount")int winCount);
}
