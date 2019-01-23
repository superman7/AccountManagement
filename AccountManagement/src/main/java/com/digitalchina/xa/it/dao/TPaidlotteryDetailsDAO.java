package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;

public interface TPaidlotteryDetailsDAO {
	//购买奖票时插入基本信息
	int insertLotteryBaseInfo(TPaidlotteryDetailsDomain tPaidlotteryDetailsDomain);
	
	//交易成功后更新ticket, transactionId字段
	int updateTicket(@Param("ticket")String ticket, @Param("transactionId")int transactionId);
	
	//交易成功后更新ticket, transactionId字段
	int updateInviteTicket(@Param("ticket")String ticket, @Param("transactionId")int transactionId, @Param("account")String account);

	//根据itcode查询某用户的抽奖记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcode(@Param("itcode")String itcode);
	
	//根据itcode、lotteryId查询某用户的某次抽奖购买记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcodeAndLotteryId(@Param("itcode")String itcode, @Param("lotteryId")int lotteryId);
	
	//根据itcode、lotteryId查询某用户的某次抽奖被邀请记录
	List<TPaidlotteryDetailsDomain> selectInviteLotteryDetailsByItcodeAndLotteryId(@Param("itcode")String itcode, @Param("lotteryId")int lotteryId);
	
	//根据itcode、lotteryId查询某用户的某次抽奖购买记录(非他人邀请)
	List<TPaidlotteryDetailsDomain> selectUninviteLotteryDetailsByItcodeAndLotteryId(@Param("itcode")String itcode, @Param("lotteryId")int lotteryId);
	
	//根据lotteryId查询某次抽奖的参与用户信息
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByLotteryId(@Param("lotteryId")int lotteryId);
	
	//生成ticket
	List<String> generateWinTicket(@Param("lotteryId")int lotteryId, @Param("winCount")int winCount);
	
	//生成ticketNew
	List<String> generateWinTicketNew(@Param("lotteryId")int lotteryId, @Param("winCount")int winCount, @Param("backup4")int backup4);
	
	//生成ticketNew
	List<String> generateWinTicketNew1(@Param("lotteryId")int lotteryId, @Param("backup4")int backup4);
		
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
	//将backup4=2改为0
	int updateBackup4From5To0(@Param("id")int id);
	//将backup4=2改为7
	int updateBackup4From5To7(@Param("id")int id);
	//将backup4=2改为8
	int updateBackup4From5To8(@Param("id")int id);
	//根据itcode获取不同result的参与记录
	List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcodeAndResult(@Param("itcode")String itcode,@Param("result")int result);
	
	//根据itcode、lotteryId、backup4>4查询该用户已邀请条数
	List<TPaidlotteryDetailsDomain> selectHaveInvitedByItcodeAndLotteryId(@Param("itcode")String itcode, @Param("lotteryId")int lotteryId);
	
	//根据itcode、invitedItcode、lotteryId、backup4>4查询用户是否已邀请invitedItcode
	List<TPaidlotteryDetailsDomain> selectIfInvitedByItcodeAndLotteryId(@Param("itcode")String itcode, @Param("invitedItcode")String invitedItcode, @Param("lotteryId")int lotteryId);

	List<TPaidlotteryDetailsDomain> selectAcceptInviteLotteryDetailsByItcodeAndLotteryId(@Param("itcode")String itcode, @Param("lotteryId")int lotteryId);
}
