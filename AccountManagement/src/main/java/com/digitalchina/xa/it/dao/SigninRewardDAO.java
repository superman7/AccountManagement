package com.digitalchina.xa.it.dao;

import java.util.List;

import com.digitalchina.xa.it.model.SigninRewardDomain;

public interface SigninRewardDAO {
	int insert(SigninRewardDomain srDomain);
	
	List<SigninRewardDomain> selectSigninStatus(String itcode);
	
	List<SigninRewardDomain> selectTodaySigninStatus(String itcode);
	
	List<SigninRewardDomain> selectTodaySigninCounter();
}
