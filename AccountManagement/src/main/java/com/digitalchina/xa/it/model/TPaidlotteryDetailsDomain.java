package com.digitalchina.xa.it.model;

import org.joda.time.DateTime;

public class TPaidlotteryDetailsDomain {
		private Integer id;
	
	    private Integer lotteryId;

		private String itcode;
		
		private String account;
		
	    private String hashcode;

	    private String ticket;
	    
	    private Integer result;
	    
	    private String winTicket;
	    
	    private String winReword;
	    
	    private DateTime buyTime;
	    
	    private String backup1;
	    
	    private String backup2;
	    
	    private Integer backup3;
	    
	    private Integer backup4;
	    
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
			return winReword;
		}

		public void setWinReword(String winReword) {
			this.winReword = winReword;
		}

		public DateTime getBuyTime() {
			return buyTime;
		}

		public void setBuyTime(DateTime buyTime) {
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
