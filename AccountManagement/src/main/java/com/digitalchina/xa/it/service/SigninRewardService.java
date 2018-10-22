package com.digitalchina.xa.it.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.xa.it.model.SigninRewardDomain;

public interface SigninRewardService {
	int addSigninReward(SigninRewardDomain sr);
	
	List<SigninRewardDomain> selectSigninStatus(String itcode);
	
	List<SigninRewardDomain> selectTodaySigninStatus(String itcode);

	List<SigninRewardDomain> selectTodaySigninCounter();
	
	String checkSigninStatus(String itcode);
	
	String saveSigninInfo(String itcode);

	String saveSigninInfoConstant(String itcode);

	Map<String, Object> addLuckyNumber(String param);
}
