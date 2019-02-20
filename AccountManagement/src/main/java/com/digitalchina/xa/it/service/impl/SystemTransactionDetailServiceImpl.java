package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.SystemTransactionDetailDAO;
import com.digitalchina.xa.it.model.SystemTransactionDetailDomain;
import com.digitalchina.xa.it.service.SystemTransactionDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service(value = "systemTransactionDetailService")
public class SystemTransactionDetailServiceImpl implements SystemTransactionDetailService{
	
	@Autowired
	private SystemTransactionDetailDAO systemTransactionDetailDAO;
	
	@Override
	public PageInfo<SystemTransactionDetailDomain> selectAllTransactionDetailByItcode(String itcode, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SystemTransactionDetailDomain> temp = systemTransactionDetailDAO.selectAllTransactionDetailByItcode(itcode);
		PageInfo<SystemTransactionDetailDomain> result = new PageInfo<SystemTransactionDetailDomain>(temp);
		return result;
	}

	@Override
	public PageInfo<SystemTransactionDetailDomain> selectPaidTransactionDetailByItcode(String itcode, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SystemTransactionDetailDomain> temp = systemTransactionDetailDAO.selectPaidTransactionDetailByItcode(itcode);
		PageInfo<SystemTransactionDetailDomain> result = new PageInfo<SystemTransactionDetailDomain>(temp);
		return result;
	}

	@Override
	public PageInfo<SystemTransactionDetailDomain> selectIncomeTransactionDetailByItcode(String itcode, int pageNum,
			int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<SystemTransactionDetailDomain> temp = systemTransactionDetailDAO.selectIncomeTransactionDetailByItcode(itcode);
		PageInfo<SystemTransactionDetailDomain> result = new PageInfo<SystemTransactionDetailDomain>(temp);
		return result;
	}

	@Override
	public List<SystemTransactionDetailDomain> selectTurnhashIs0x0TransactionDetail() {
		return systemTransactionDetailDAO.selectTurnhashIs0x0TransactionDetail();
	}

	@Override
	public void updateTimerTo2(int id) {
		systemTransactionDetailDAO.updateTimerTo2(id);
	}

}
