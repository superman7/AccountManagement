package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;

public interface TPaidlotteryDetailsDAO {
	//购买奖票时插入基本信息
	int insertLotteryBaseInfo(TPaidlotteryDetailsDomain tPaidlotteryDetailsDomain);
	
	//交易成功后更新ticket, transactionId字段
	int updateTicket(@Param("ticket")String ticket, @Param("transactionId")int transactionId);
	
	//根据itcode查询某用户的抽奖记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcode(@Param("itcode")String itcode);
	
	//根据itcode、lotteryId查询某用户的某次抽奖购买记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcodeAndLotteryId(@Param("itcode")String itcode, @Param("lotteryId")int lotteryId);
	
	//根据lotteryId查询某次抽奖的参与用户信息
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByLotteryId(@Param("lotteryId")int lotteryId);
	
	//生成ticket
	List<String> generateWinTicket(@Param("lotteryId")int lotteryId, @Param("winCount")int winCount);
	
	//根据id查询
	TPaidlotteryDetailsDomain selectLotteryDetailsById(@Param("id")int id);
	
	//定时任务使用，查询Hash值不为空，backup为
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsWhereHashIsNotNullAndBackup3Is0();
	
	//定时任务使用，更新超时任务
	void updateLotteryDetailsWhereTimeOut();
	
	//开奖后更新result，winTicket，winReword
	int updateDetailAfterLotteryFinished(@Param("id")int id, @Param("result")int result, @Param("winTicket")String winTicket, @Param("winReward")String winReward);
	
	//根据lottery，backup3查询个数
	int selectCountByBackup3(@Param("lotteryId")int lotteryId, @Param("backup3")int backup3);
	
	//根据lottery，backup3查询个数
	List<TPaidlotteryDetailsDomain> selectDetailByBackup3(@Param("lotteryId")int lotteryId, @Param("backup3")int backup3);
	
	//将backup3=2改为3
	int updateBackup3From2To3(@Param("id")int id);
}
