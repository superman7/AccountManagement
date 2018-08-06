package com.digitalchina.xa.it.service;

import com.digitalchina.xa.it.model.WalletAccountDomain;
import com.github.pagehelper.PageInfo;

public interface WalletAccountService {
	PageInfo<WalletAccountDomain> findAllWalletAccount(int pageNum, int pageSize);
}
