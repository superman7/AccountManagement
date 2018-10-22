package com.digitalchina.xa.it.model;

import java.sql.Timestamp;

public class VoteDomain {
	    private int id;

		private int topicID;

	    private int optionID;

	    private Timestamp votetime;
	    
	    private String account;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getTopicID() {
			return topicID;
		}

		public void setTopicID(int topicID) {
			this.topicID = topicID;
		}

		public int getOptionID() {
			return optionID;
		}

		public void setOptionID(int optionID) {
			this.optionID = optionID;
		}

		public Timestamp getVotetime() {
			return votetime;
		}

		public void setVotetime(Timestamp votetime) {
			this.votetime = votetime;
		}

		public String getAccount() {
			return account;
		}

		public void setAccount(String account) {
			this.account = account;
		}
}
