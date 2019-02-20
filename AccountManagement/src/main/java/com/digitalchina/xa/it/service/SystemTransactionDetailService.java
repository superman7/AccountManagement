package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.SystemTransactionDetailDomain;
import com.github.pagehelper.PageInfo;

public interface SystemTransactionDetailService {
	//查询全部交易
	PageInfo<SystemTransactionDetailDomain> selectAllTransactionDetailByItcode(String itcode, int pageNum, int pageSize);
	//查询支付交易
	PageInfo<SystemTransactionDetailDomain> selectPaidTransactionDetailByItcode(String itcode, int pageNum, int pageSize);
	//查询收入交易
	PageInfo<SystemTransactionDetailDomain> selectIncomeTransactionDetailByItcode(String itcode, int pageNum, int pageSize);
	
	//查询未更新Hash值的交易（供定时任务使用）
	List<SystemTransactionDetailDomain> selectTurnhashIs0x0TransactionDetail();
	//将完成的交易timer更新为2
	void updateTimerTo2(int id);
}
