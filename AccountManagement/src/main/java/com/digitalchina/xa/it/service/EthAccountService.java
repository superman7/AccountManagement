package com.digitalchina.xa.it.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.EthAccountDomain;
import com.github.pagehelper.PageInfo;

public interface EthAccountService {
	PageInfo<EthAccountDomain> findAllEthAccount(int pageNum, int pageSize);
	
	List<EthAccountDomain> selectEthAccountByItcode(String itcode);
	
	EthAccountDomain selectEthAccountByAddress(String address);
	
	EthAccountDomain selectDefaultEthAccount(String itcode);
	
	String selectKeystoreByAccount(EthAccountDomain ethAccountDomain);
	
	String selectKeystoreByItcode(String itcode);
	
	Boolean insertItcodeAndAccount(EthAccountDomain ethAccountDomain);
	
	Boolean updateAccountBalance(String address, Double balance);
	
	Boolean updateDefaultBalance(String itcode, String status, BigDecimal balance);
	
	Boolean updateKeystoreAndAlias(String keystore, String alias, String address, Integer available);

	void refreshBalance(String itcode);

	Boolean insert(EthAccountDomain ethAccountDomain);
}
