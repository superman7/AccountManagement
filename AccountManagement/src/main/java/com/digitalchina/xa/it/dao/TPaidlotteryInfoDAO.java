package com.digitalchina.xa.it.dao;

import java.util.List;

import com.digitalchina.xa.it.model.TPaidlotteryInfoDomain;

public interface TPaidlotteryInfoDAO {
	//获取结束或未结束的抽奖
	List<TPaidlotteryInfoDomain> selectLotteryInfoByFlag(int flag);
	
	//根据id获取抽奖
	TPaidlotteryInfoDomain selectLotteryInfoById(int id);
}
