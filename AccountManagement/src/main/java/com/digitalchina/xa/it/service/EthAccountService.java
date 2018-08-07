package com.digitalchina.xa.it.service;

import java.math.BigDecimal;
import java.util.List;

import com.digitalchina.xa.it.model.EthAccountDomain;
import com.github.pagehelper.PageInfo;

public interface EthAccountService {
	PageInfo<EthAccountDomain> findAllEthAccount(int pageNum, int pageSize);
	
	List<EthAccountDomain> selectEthAccountByItcode(String itcode);
	
	EthAccountDomain selectEthAccountByAddress(String address);
	
	EthAccountDomain selectDefaultEthAccount(String itcode, String status);
	
	Boolean insertEthAccount(EthAccountDomain ethAccountDomain);
	
	Boolean updateAccountBalance(String address, BigDecimal balance);
	
	Boolean updateDefaultBalance(String itcode, String status, BigDecimal balance);
}
