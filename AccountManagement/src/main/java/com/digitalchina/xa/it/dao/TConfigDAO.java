package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TConfigDAO {
	List<String> selectIpArr();
	
	int UpdateEthNodesStatus(@Param("cfgValue")String cfgValue, @Param("cfgStatus")Integer cfgStatus);
	
	String selectValueByKey(@Param("cfgKey")String cfgKey);
}
