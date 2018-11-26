package com.digitalchina.xa.it.fanwei.service;

public interface QiandaoContractService {
	/**
	 * 给合约账户充值
	 * 
	 * @param turnCountBean
	 */
	public abstract String chargeToContract(String value);
	
	/**
	 * 签到获取奖励
	 * 
	 * @return 调用合约那笔交易的hash值
	 */
	public abstract String signinReward(String itcode);
	
	/**
	 * 签到获取奖励(连续签到)
	 * 
	 * @return 调用合约那笔交易的hash值
	 */
	public abstract String signinReward(String itcode, int reward);

	/**
	 * 投票获取奖励
	 * 
	 * @return 调用合约那笔交易的hash值
	 */
	public abstract String voteReward(String itcode);

	/**
	 * 考勤奖励
	 * 
	 */
	public abstract void attendanceReward(String employeeNumber);
	
	/**
	 * 邀请好友奖励发放
	 * 
	 */
	public abstract void inviteFriendsReward(String itcodeStr);
}
