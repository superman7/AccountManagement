package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TConfigDomain;

public interface TConfigDAO {
	List<String> selectIpArr();
	
	int UpdateEthNodesStatus(@Param("cfgValue")String cfgValue, @Param("cfgStatus")Integer cfgStatus);
	
	String selectValueByKey(@Param("cfgKey")String cfgKey);
	
	List<TConfigDomain> selectEthNodesInfo();
	
	List<TConfigDomain> selectContractInfo();
}
