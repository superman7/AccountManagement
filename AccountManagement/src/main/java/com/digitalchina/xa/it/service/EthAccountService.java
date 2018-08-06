package com.digitalchina.xa.it.service;

import com.digitalchina.xa.it.model.EthAccountDomain;
import com.github.pagehelper.PageInfo;

public interface EthAccountService {
	PageInfo<EthAccountDomain> findAllEthAccount(int pageNum, int pageSize);
}
