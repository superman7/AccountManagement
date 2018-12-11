package com.digitalchina.xa.it.dao;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;

public interface TPaidlotteryDetailsDAO {
	int insertLotteryBaseInfo(TPaidlotteryDetailsDomain tPaidlotteryDetailsDomain);
	
	int updateHashcode(@Param("hashcode")String hashcode, @Param("ticket")String ticket, @Param("transactionId")String transactionId);
}
