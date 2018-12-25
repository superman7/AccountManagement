package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TConfigDomain;

public interface TConfigDAO {
	List<String> selectIpArr();
	
	int UpdateEthNodesStatus(@Param("cfgValue")String cfgValue, @Param("cfgStatus")Boolean cfgStatus);
	
	String selectValueByKey(@Param("cfgKey")String cfgKey);
	
	List<TConfigDomain> selectEthNodesInfo();
	
	List<TConfigDomain> selectContractInfo();
	
	List<TConfigDomain> selectConfigByExtra(@Param("cfgExtra")String cfgExtra);
}
