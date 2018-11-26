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
	
	//NEWCODE START-泛微签到模块的奖励代码-START
	/**
	 * 给合约账户充值
	 */
	String chargeToContract(String value);
	/**
	 * 根据itcode 和 金额 发放签到奖励
	 */
	String signinReward(String itcode, int reward);
	/**
	 * 参与每日投票获得奖励
	 */
	String voteReward(String itcode);
	/**
	 * 每日考勤奖励
	 */
	void attendanceReward(String employeeNumber);
	
	//NEWCODE END-泛微签到模块的奖励代码-END
}
