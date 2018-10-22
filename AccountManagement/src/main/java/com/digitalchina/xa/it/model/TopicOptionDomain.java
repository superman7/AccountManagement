package com.digitalchina.xa.it.model;

public class TopicOptionDomain {
	    private int id;

		private String optionkey;

	    private int topicid;

	    private int popularity;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getPopularity() {
			return popularity;
		}

		public void setPopularity(int popularity) {
			this.popularity = popularity;
		}

		public int getTopicid() {
			return topicid;
		}

		public void setTopicid(int topicid) {
			this.topicid = topicid;
		}

		public String getOptionkey() {
			return optionkey;
		}

		public void setOptionkey(String optionkey) {
			this.optionkey = optionkey;
		}
}
