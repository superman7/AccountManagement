package com.digitalchina.xa.it.service;

import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;

public interface TPaidlotteryService {
	int insertLotteryBaseInfo(TPaidlotteryDetailsDomain tPaidlotteryDetailsDomain);
	
	int updateHashcodeAndJudge(String hashcode, String transactionId);
}
