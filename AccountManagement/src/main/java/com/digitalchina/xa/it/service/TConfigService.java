package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.TConfigDomain;

public interface TConfigService {
	List<String> selectIpArr();
  
	Boolean UpdateEthNodesStatus(String cfgValue, Boolean cfgStatus);
	
	String selectValueByKey(String cfgKey);
	
	List<TConfigDomain> selectEthNodesInfo();
	
	List<TConfigDomain> selectContractInfo();
}
