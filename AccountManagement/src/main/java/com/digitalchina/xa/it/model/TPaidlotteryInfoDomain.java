package com.digitalchina.xa.it.model;

import java.sql.Timestamp;

import org.joda.time.DateTime;

public class TPaidlotteryInfoDomain {
		private Integer id;
		
		private String name;
	
	    private String description;
	    
	    private Integer flag;
	    
	    private Integer typeCode;
	    
	    private Integer winCount;
	    
	    private Integer unitPrice;
	    
	    private Integer winSumAmount;

		private String winDate;
		
		private Integer winSumPerson;
		
		private Integer nowSumPerson;
		
		private Integer nowSumAmount;
	
		private String reward;
		
		private String winner;
		
		private String winTicket;
		
		private Integer limitEveryday;
		
		private Timestamp lotteryTime;
		
	    private String backup1;
	    
	    private String backup2;
	    
	    private String backup3;
	    
	    private Integer backup4;
	    
	    private Integer backup5;
	    
	    private Integer backup6;
	    
	    public TPaidlotteryInfoDomain() {
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Integer getFlag() {
			return flag;
		}

		public void setFlag(Integer flag) {
			this.flag = flag;
		}

		public Integer getTypeCode() {
			return typeCode;
		}

		public void setTypeCode(Integer typeCode) {
			this.typeCode = typeCode;
		}

		public Integer getWinCount() {
			return winCount;
		}

		public void setWinCount(Integer winCount) {
			this.winCount = winCount;
		}

		public Integer getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(Integer unitPrice) {
			this.unitPrice = unitPrice;
		}

		public Integer getWinSumAmount() {
			return winSumAmount;
		}

		public void setWinSumAmount(Integer winSumAmount) {
			this.winSumAmount = winSumAmount;
		}

		public String getWinDate() {
			return winDate;
		}

		public void setWinDate(String winDate) {
			this.winDate = winDate;
		}

		public Integer getWinSumPerson() {
			return winSumPerson;
		}

		public void setWinSumPerson(Integer winSumPerson) {
			this.winSumPerson = winSumPerson;
		}

		public Integer getNowSumPerson() {
			return nowSumPerson;
		}

		public void setNowSumPerson(Integer nowSumPerson) {
			this.nowSumPerson = nowSumPerson;
		}

		public Integer getNowSumAmount() {
			return nowSumAmount;
		}

		public void setNowSumAmount(Integer nowSumAmount) {
			this.nowSumAmount = nowSumAmount;
		}

		public String getReward() {
			return reward;
		}

		public void setReward(String reward) {
			this.reward = reward;
		}

		public String getWinner() {
			return winner;
		}

		public void setWinner(String winner) {
			this.winner = winner;
		}

		public String getWinTicket() {
			return winTicket;
		}

		public void setWinTicket(String winTicket) {
			this.winTicket = winTicket;
		}

		public Integer getLimitEveryday() {
			return limitEveryday;
		}

		public void setLimitEveryday(Integer limitEveryday) {
			this.limitEveryday = limitEveryday;
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

		public Integer getBackup4() {
			return backup4;
		}

		public void setBackup4(Integer backup4) {
			this.backup4 = backup4;
		}

		public Integer getBackup5() {
			return backup5;
		}

		public void setBackup5(Integer backup5) {
			this.backup5 = backup5;
		}

		public Timestamp getLotteryTime() {
			return lotteryTime;
		}

		public void setLotteryTime(Timestamp lotteryTime) {
			this.lotteryTime = lotteryTime;
		}

		public Integer getBackup6() {
			return backup6;
		}

		public void setBackup6(Integer backup6) {
			this.backup6 = backup6;
		}
}
