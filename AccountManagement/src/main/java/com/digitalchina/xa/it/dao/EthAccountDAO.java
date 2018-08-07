package com.digitalchina.xa.it.dao;

import java.util.List;

import com.digitalchina.xa.it.model.EthAccountDomain;

public interface EthAccountDAO {
	List<EthAccountDomain> selectEthAccount();
}
