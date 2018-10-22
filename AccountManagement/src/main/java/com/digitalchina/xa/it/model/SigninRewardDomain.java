package com.digitalchina.xa.it.model;

import java.sql.Timestamp;

public class SigninRewardDomain {
	private Integer id;
	private String itcode;
	private String accountkey;
	private Timestamp signtime;
	//1:日期字典 0:用户数据
	private Integer type;
	private Integer rewards;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItcode() {
		return itcode;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	public String getAccountkey() {
		return accountkey;
	}
	public void setAccountkey(String accountkey) {
		this.accountkey = accountkey;
	}
	public Timestamp getSigntime() {
		return signtime;
	}
	public void setSigntime(Timestamp signtime) {
		this.signtime = signtime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getRewards() {
		return rewards;
	}
	public void setRewards(Integer rewards) {
		this.rewards = rewards;
	}
}
