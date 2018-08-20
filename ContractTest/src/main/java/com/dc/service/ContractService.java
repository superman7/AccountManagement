package com.dc.service;

import org.web3j.abi.datatypes.generated.Uint256;

public interface ContractService {
	void transferToFixedAccount();
	
	void setTransferValue(Uint256 value);
	
	void chargeToContract(int value);
}
