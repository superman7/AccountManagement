package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.PaidReadAuthorDomain;

public interface PaidReadAuthorService {
	//查询该用户是否在已存在
	Integer selectAuthorIfSaved(String itcode);
}
