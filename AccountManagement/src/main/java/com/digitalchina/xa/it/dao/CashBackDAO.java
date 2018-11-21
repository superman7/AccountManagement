package com.digitalchina.xa.it.dao;

import org.apache.ibatis.annotations.Param;

public interface CashBackDAO {
	Integer selectLimitFlagByItcode(@Param("itcode")String itcode);
}
