package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.PaidReadAuthorDomain;

public interface PaidReadAuthorService {
	//查询该用户是否在已存在
	boolean selectAuthorIfSaved(String itcode);
	//插入新的作者数据
	int insertPaidReadAuthor(PaidReadAuthorDomain paidReadAuthorDomain);
	
}
