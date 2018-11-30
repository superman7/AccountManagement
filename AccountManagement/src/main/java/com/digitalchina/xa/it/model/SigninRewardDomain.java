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
	private String transactionhash;
	private String backup1;
	private String backup2;
	private String backup3;
	
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
	public String getTransactionhash() {
		return transactionhash;
	}
	public void setTransactionhash(String transactionhash) {
		this.transactionhash = transactionhash;
	}
	public String getBackup1() {
		return backup1;
	}
	public void setBackup1(String backup1) {
		this.backup1 = backup1;
	}
	public String getBackup2() {
		return backup2;
	}
	public void setBackup2(String backup2) {
		this.backup2 = backup2;
	}
	public String getBackup3() {
		return backup3;
	}
	public void setBackup3(String backup3) {
		this.backup3 = backup3;
	}
}
