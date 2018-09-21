package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.protocol.Web3j;

import com.digitalchina.xa.it.dao.TransactionDetailDAO;
import com.digitalchina.xa.it.model.TransactionDetailDomain;
import com.digitalchina.xa.it.service.TransactionDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class TransactionDetailServiceImpl implements TransactionDetailService {

	private volatile static Web3j web3j;
    private static String ip = "http://10.7.10.124:8545";
    private static String[] ipArr = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};
    
    @Autowired
    private TransactionDetailDAO transactionDetailDAO;//这里会报错，但是并不会影响
    
	@Override
	public PageInfo<TransactionDetailDomain> selectAllTransactionDetailByItcode(String itcode, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TransactionDetailDomain> temp = transactionDetailDAO.selectAllTransactionDetailByItcode(itcode);
		PageInfo<TransactionDetailDomain> result = new PageInfo<TransactionDetailDomain>(temp);
		return result;
	}

	@Override
	public PageInfo<TransactionDetailDomain> selectPaidTransactionDetailByItcode(String itcode, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TransactionDetailDomain> temp = transactionDetailDAO.selectPaidTransactionDetailByItcode(itcode);
		PageInfo<TransactionDetailDomain> result = new PageInfo<TransactionDetailDomain>(temp);
		return result;
	}

	@Override
	public PageInfo<TransactionDetailDomain> selectIncomeTransactionDetailByItcode(String itcode, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<TransactionDetailDomain> temp = transactionDetailDAO.selectIncomeTransactionDetailByItcode(itcode);
		PageInfo<TransactionDetailDomain> result = new PageInfo<TransactionDetailDomain>(temp);
		return result;
	}

	@Override
	public List<TransactionDetailDomain> selectTurnhashIs0x0TransactionDetail() {
		return transactionDetailDAO.selectTurnhashIs0x0TransactionDetail();
	}

}
