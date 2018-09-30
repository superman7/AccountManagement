package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.TransactionDetailDomain;
import com.github.pagehelper.PageInfo;

public interface TransactionDetailService {
	//查询全部交易
	PageInfo<TransactionDetailDomain> selectAllTransactionDetailByItcode(String itcode, int pageNum, int pageSize);
	//查询支付交易
	PageInfo<TransactionDetailDomain> selectPaidTransactionDetailByItcode(String itcode, int pageNum, int pageSize);
	//查询收入交易
	PageInfo<TransactionDetailDomain> selectIncomeTransactionDetailByItcode(String itcode, int pageNum, int pageSize);
	
	//查询未更新Hash值的交易（供定时任务使用）
	List<TransactionDetailDomain> selectTurnhashIs0x0TransactionDetail();
}
