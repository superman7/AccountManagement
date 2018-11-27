package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalchina.xa.it.dao.WalletTransactionDAO;
import com.digitalchina.xa.it.model.WalletTransactionDomain;
import com.digitalchina.xa.it.service.WalletTransactionService;

@Service(value = "walletTransactionService")
public class WalletTransactionServiceImpl implements WalletTransactionService{
	@Autowired
	private WalletTransactionDAO walletTransactionDAO;
	
	@Override
	public List<WalletTransactionDomain> selectHashAndAccounts() {
		return walletTransactionDAO.selectHashAndAccounts();
	}

	@Override
	@Transactional
	public Integer insertBaseInfo(WalletTransactionDomain walletTransactionDomain) {
		if(walletTransactionDomain == null) {
			throw new RuntimeException("walletAccountDomain为null");
		}
		try {
			int effectedNumber = walletTransactionDAO.insertBaseInfo(walletTransactionDomain);
			if(effectedNumber > 0) {
				return walletTransactionDomain.getId();
			} else {
				throw new RuntimeException("插入信息失败");
			}
		} catch(Exception e) {
			throw new RuntimeException("插入信息失败 " + e.getMessage());
		}
	}

	@Override
	public Boolean updateByTransactionHash(WalletTransactionDomain walletTransactionDomain) {
		if(walletTransactionDomain == null) {
			throw new RuntimeException("walletAccountDomain为null");
		}
		if(walletTransactionDomain.getTransactionHash() != null && walletTransactionDomain.getTransactionHash() != "") {
			try {
				int effectedNumber = walletTransactionDAO.updateByTransactionHash(walletTransactionDomain);
				if(effectedNumber > 0) {
					return true;
				} else {
					throw new RuntimeException("更新信息失败");
				}
			} catch(Exception e) {
				throw new RuntimeException("更新信息失败 " + e.getMessage());
			}
		} else {
			throw new RuntimeException("更新用户transactionHash不能为空");
		}
	}

	@Override
	public List<WalletTransactionDomain> selectRecordsByItcode(String itcode) {
		return walletTransactionDAO.selectRecordsByItcode(itcode);
	}
}
