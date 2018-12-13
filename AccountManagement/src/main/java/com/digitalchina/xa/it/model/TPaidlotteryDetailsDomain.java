package com.digitalchina.xa.it.model;

import java.sql.Timestamp;

public class TPaidlotteryDetailsDomain {
	
		private Integer id;
	
	    private Integer lotteryId;

		private String itcode;
		
		private String account;
		
	    private String hashcode;

	    private String ticket;
	    
	    private Integer result;
	    
	    private String winTicket;
	    
	    private String winReward;
	    
	    private Timestamp buyTime;
	    
	    private String backup1;
	    
	    private String backup2;
	    
	    private Integer backup3;
	    
	    private Integer backup4;
	    
	    public TPaidlotteryDetailsDomain(){
	    	
	    }
	    public TPaidlotteryDetailsDomain(Integer lotteryId, String itcode, String account, String hashcode, String ticket, Integer result, String winTicket, String winReward, Timestamp buyTime, String backup1, String backup2, Integer backup3, Integer backup4) {
	    	this.lotteryId = lotteryId;
	    	this.itcode = itcode;
	    	this.account = account;
	    	this.hashcode = hashcode;
	    	this.ticket = ticket;
	    	this.result = result;
	    	this.winTicket = winTicket;
	    	this.winReward = winReward;
	    	this.buyTime = buyTime;
	    	this.backup1 = backup1;
	    	this.backup2 = backup2;
	    	this.backup3 = backup3;
	    	this.backup4 = backup4;
	    }
	    
	    public TPaidlotteryDetailsDomain(Integer lotteryId, String itcode, String account, String hashcode, String ticket, Integer result, String winTicket, String winReward, Timestamp buyTime) {
	    	this.lotteryId = lotteryId;
	    	this.itcode = itcode;
	    	this.account = account;
	    	this.hashcode = hashcode;
	    	this.ticket = ticket;
	    	this.result = result;
	    	this.winTicket = winTicket;
	    	this.winReward = winReward;
	    	this.buyTime = buyTime;
	    }
	    
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public Integer getLotteryId() {
			return lotteryId;
		}

		public void setLotteryId(Integer lotteryId) {
			this.lotteryId = lotteryId;
		}

		public String getItcode() {
			return itcode;
		}

		public void setItcode(String itcode) {
			this.itcode = itcode;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public String getHashcode() {
			return hashcode;
		}

		public void setHashcode(String hashcode) {
			this.hashcode = hashcode;
		}

		public String getTicket() {
			return ticket;
		}

		public void setTicket(String ticket) {
			this.ticket = ticket;
		}

		public Integer getResult() {
			return result;
		}

		public void setResult(Integer result) {
			this.result = result;
		}

		public String getWinTicket() {
			return winTicket;
		}

		public void setWinTicket(String winTicket) {
			this.winTicket = winTicket;
		}

		public String getWinReword() {
			return winReward;
		}

		public void setWinReword(String winReward) {
			this.winReward = winReward;
		}

		public Timestamp getBuyTime() {
			return buyTime;
		}

		public void setBuyTime(Timestamp buyTime) {
			this.buyTime = buyTime;
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

		public Integer getBackup3() {
			return backup3;
		}

		public void setBackup3(Integer backup3) {
			this.backup3 = backup3;
		}

		public Integer getBackup4() {
			return backup4;
		}

		public void setBackup4(Integer backup4) {
			this.backup4 = backup4;
		}
}

