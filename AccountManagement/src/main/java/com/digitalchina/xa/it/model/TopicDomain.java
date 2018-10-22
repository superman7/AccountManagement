package com.digitalchina.xa.it.model;

public class TopicDomain {
	    private int id;

		private String name;

	    private String type;

	    private int popularity;
	    
	    private int available;	

	    private int reader;
	    
	    private int priority;
	    
	    private String backup1;
	    
	    private String backup2;
	    
	    private String backup3;
	    
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

		public int getReader() {
			return reader;
		}

		public void setReader(int reader) {
			this.reader = reader;
		}

		public int getPriority() {
			return priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
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
