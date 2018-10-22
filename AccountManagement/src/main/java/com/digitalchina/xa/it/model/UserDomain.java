package com.digitalchina.xa.it.model;

public class UserDomain {
	    private String itcode;

		private String account;

	    private Double balance;

	    private Integer status;
	    
	    private String keystore;

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

		public Double getBalance() {
			return balance;
		}

		public void setBalance(Double balance) {
			this.balance = balance;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getKeystore() {
			return keystore;
		}

		public void setKeystore(String keystore) {
			this.keystore = keystore;
		}
}
