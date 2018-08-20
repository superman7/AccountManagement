package com.digitalchina.xa.it.dao;

import java.util.List;

import com.digitalchina.xa.it.model.WalletAccountDomain;

public interface WalletAccountDAO {
	List<WalletAccountDomain> selectWalletAccount();
	
	WalletAccountDomain selectWalletAccountByItcode(String itcode);
	
	int insertWalletAccount(WalletAccountDomain walletAccountDomain);
	
	List<String> selectUserNoBefore8();
	
	List<String> selectUserNoAfter21();
}

