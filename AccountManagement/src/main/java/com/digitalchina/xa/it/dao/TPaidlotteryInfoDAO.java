package com.digitalchina.xa.it.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TPaidlotteryInfoDomain;

public interface TPaidlotteryInfoDAO {
	//插入抽奖信息
	void insertLotteryInfo(TPaidlotteryInfoDomain tplid);
	
	//获取结束或未结束的抽奖
	List<TPaidlotteryInfoDomain> selectLotteryInfoByFlag(@Param("flag")int flag);
	
	//根据id获取抽奖
	TPaidlotteryInfoDomain selectLotteryInfoById(@Param("id")int id);
	
	//更新nowSumAmount
	int updateNowSumAmountAndBackup4(@Param("id")int id);
	
	//更新flag，lotteryTime，winner，winTicket
	int updateAfterLotteryFinished(@Param("id")int id, @Param("lotteryTime")Timestamp lotteryTime, @Param("winner")String winner, @Param("winTicket")String winTicket, @Param("backup6")int backup6);
	
	//获取需要开奖的抽奖
	List<TPaidlotteryInfoDomain> selectRunLottery();
	
	//获取flag=0的奖项
	List<TPaidlotteryInfoDomain> selectUnfinishedLottery();
	
	//出现交易失败时，更新nowSumAmount、backup4
	int updateNowSumAmountAndBackup4Sub(@Param("id")int id, @Param("count")int count);
	
	//成功交易个数与backup4相等时，backup4更新为0
	int updateBackup4To0(@Param("id")int id);
	
	//获取当前SZB抽奖的数量 flag=0,typeCode=1
	List<TPaidlotteryInfoDomain> selectUnfinishedSZBLottery();
	
	//获取当前RMB抽奖的数量 flag=0,typeCode=0
	List<TPaidlotteryInfoDomain> selectUnfinishedRMBLottery();
	
	//获取最新的RMB夺宝
	Integer selectLastRMBLottery();
	
	//更新reward字段
	int updateLotteryReward(@Param("id")int id, @Param("reward")String reward);
	
	//更新winDate(winBlockHash)字段
	int updateLotteryWinBlockHash(@Param("id")int id, @Param("winBlockHash")String winBlockHash);
	
	//获取smb夺宝
	TPaidlotteryInfoDomain selectOneSmbTpid();
	
	//获取红包夺宝
	List<TPaidlotteryInfoDomain> selectHbTpids();
		
	//获取其他夺宝
	List<TPaidlotteryInfoDomain> selectOtherTpids();
	
	//获取最新开奖，10个
	List<TPaidlotteryInfoDomain> selectNewOpen(@Param("count")int count);
}
