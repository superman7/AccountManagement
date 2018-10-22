package com.digitalchina.xa.it.model;

public class TopicDomain {
	    private int id;

		private String name;

	    private String type;

	    private int popularity;
	    
	    private int available;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public int getPopularity() {
			return popularity;
		}

		public void setPopularity(int popularity) {
			this.popularity = popularity;
		}

		public int getAvailable() {
			return available;
		}

		public void setAvailable(int available) {
			this.available = available;
		}
}
