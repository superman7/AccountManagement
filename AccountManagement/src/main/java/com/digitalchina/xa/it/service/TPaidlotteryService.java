package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;

public interface TPaidlotteryService {
	int insertLotteryBaseInfo(TPaidlotteryDetailsDomain tPaidlotteryDetailsDomain);
	
	int updateHashcodeAndJudge(String hashcode, String transactionId);
	
	//传入lotteryInfoId,itcode,hashcode,生成ticket
	String generateTicket(int lotteryId, String itcode, String hashcode);
	//传入lotteryInfoId,生成win ticket
	List<String> generateWinTicket(int lotteryId, int winCount);
}
