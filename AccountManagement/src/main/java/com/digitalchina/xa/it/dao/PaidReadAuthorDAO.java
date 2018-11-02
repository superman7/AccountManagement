package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.PaidReadAuthorDomain;

public interface PaidReadAuthorDAO {
	
	int selectAuthorIfSaved(@Param(value = "itcode") String itcode);
	
	int insertPaidReadAuthor(PaidReadAuthorDomain paidReadAuthorDomain);
}
