package com.digitalchina.xa.it.dao;

import org.apache.ibatis.annotations.Param;

public interface InsertTestDAO {
	int insertData(@Param("dataStr")String dataStr, @Param("timeStamp")String timeStamp);
}
