package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.PaidReadAuthorDomain;

public interface PaidReadAuthorDAO {
	
	Integer selectAuthorIfSaved(@Param(value = "itcode") String itcode);
	
	Integer insertPaidReadAuthor(PaidReadAuthorDomain paidReadAuthorDomain);
	
	Integer selectAuthorByItcode(@Param(value = "itcode") String itcode);
}
