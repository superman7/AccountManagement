package com.digitalchina.xa.it.dao;

import java.util.List;

import com.digitalchina.xa.it.model.WalletAccountDomain;

public interface WalletAccountDAO {
	List<WalletAccountDomain> selectWallatAccount();
}
