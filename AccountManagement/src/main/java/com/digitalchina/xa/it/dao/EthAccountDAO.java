package com.digitalchina.xa.it.dao;

import java.math.BigDecimal;
import java.util.List;

import com.digitalchina.xa.it.model.EthAccountDomain;

public interface EthAccountDAO {
	List<EthAccountDomain> selectEthAccount();
	
	List<EthAccountDomain> selectEthAccountByItcode(String itcode);
	
	EthAccountDomain selectEthAccountByAddress(String address);
	
	EthAccountDomain selectDefaultEthAccount(String itcode, String status);
	
	int insertItcodeAndAccount(EthAccountDomain ethAccountDomain);
	
	int updateDefaultBalance(String itcode, String status, BigDecimal balance);
	
	int updateKeystoreAndAlias(EthAccountDomain ethAccountDomain);

	int updateAccountBalance(EthAccountDomain xxxx);
}
