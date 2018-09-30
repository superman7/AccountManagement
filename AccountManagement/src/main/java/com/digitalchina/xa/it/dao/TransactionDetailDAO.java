package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TransactionDetailDomain;

public interface TransactionDetailDAO {
	//查询全部交易
	List<TransactionDetailDomain> selectAllTransactionDetailByItcode(@Param("itcode")String itcode);
	//查询支付交易
	List<TransactionDetailDomain> selectPaidTransactionDetailByItcode(@Param("itcode")String itcode);
	//查询收入交易
	List<TransactionDetailDomain> selectIncomeTransactionDetailByItcode(@Param("itcode")String itcode);
	
	//查询未更新Hash值的交易（供定时任务使用）
	List<TransactionDetailDomain> selectTurnhashIs0x0TransactionDetail();
}
