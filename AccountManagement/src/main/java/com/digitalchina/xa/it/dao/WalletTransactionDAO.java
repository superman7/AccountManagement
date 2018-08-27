package com.digitalchina.xa.it.dao;

import java.util.List;

import com.digitalchina.xa.it.model.WalletTransactionDomain;

public interface WalletTransactionDAO {
	List<WalletTransactionDomain> selectHashAndAccounts();
	
	List<WalletTransactionDomain> selectRecordsByItcode(String itcode);
	
	int insertBaseInfo(WalletTransactionDomain walletTransactionDomain);
	
	int updateByTransactionHash(WalletTransactionDomain walletTransactionDomain);
}
