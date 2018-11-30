package com.digitalchina.xa.it.service;

import java.util.List;

public interface TConfigService {
	List<String> selectIpArr();
	
	Boolean UpdateEthNodesStatus(String cfgValue, Integer cfgStatus);
	
	String selectValueByKey(String cfgKey);
}
