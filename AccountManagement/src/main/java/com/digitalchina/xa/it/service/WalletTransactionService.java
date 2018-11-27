package com.digitalchina.xa.it.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.model.WalletTransactionDomain;

@Service
public interface WalletTransactionService {
	List<WalletTransactionDomain> selectHashAndAccounts();
	
	List<WalletTransactionDomain> selectRecordsByItcode(String itcode);
	
	Integer insertBaseInfo(WalletTransactionDomain walletTransactionDomain);
	
	Boolean updateByTransactionHash(WalletTransactionDomain walletTransactionDomain);
}
