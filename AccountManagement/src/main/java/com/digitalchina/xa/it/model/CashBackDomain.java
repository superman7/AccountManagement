package com.digitalchina.xa.it.model;

public class CashBackDomain {
		private Integer id;
	
	    private String itcode;

		private String account;

	    private Double cashBackValue;

	    private Integer limitFlag;
	    
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

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}

		public Double getCashBackValue() {
			return cashBackValue;
		}

		public void setCashBackValue(Double cashBackValue) {
			this.cashBackValue = cashBackValue;
		}

		public Integer getLimitFlag() {
			return limitFlag;
		}

		public void setLimitFlag(Integer limitFlag) {
			this.limitFlag = limitFlag;
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
