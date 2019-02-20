package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.SystemTransactionDetailDomain;

public interface SystemTransactionDetailDAO {
	int insertBaseInfo(SystemTransactionDetailDomain systemTransactionDetailDomain);
	//查询全部交易
	List<SystemTransactionDetailDomain> selectAllTransactionDetailByItcode(@Param("itcode")String itcode);
	//查询支付交易
	List<SystemTransactionDetailDomain> selectPaidTransactionDetailByItcode(@Param("itcode")String itcode);
	//查询收入交易
	List<SystemTransactionDetailDomain> selectIncomeTransactionDetailByItcode(@Param("itcode")String itcode);
	
	//查询未更新Hash值的交易（供定时任务使用）
	List<SystemTransactionDetailDomain> selectTurnhashIs0x0TransactionDetail();
	
	void updateTransactionDetailsWhereTimeOut();
	
	//定时任务使用，查询Hash值不为空，backup为
	List<SystemTransactionDetailDomain> selectTransactionDetailWhereHashIsNotNullAndTimerIs0();
	
	List<SystemTransactionDetailDomain> selectTransactionDetailWhereFlagIs0Or1AndTimerIs2();
	void updateTimerTo2(@Param("id")int id);
}
