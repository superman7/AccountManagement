package com.digitalchina.xa.it.model;

public class PaidVoteTopicDomain {
	
	private Integer id;

    private String topicName;

    private Integer available;
    
    private String backup1;
    
    private String backup2;
    
    private String backup3;

    public PaidVoteTopicDomain(){
    	
    }
    
    public PaidVoteTopicDomain(String topicName, Integer available, String backup1, String backup2, String backup3){
    	this.topicName = topicName;
    	this.available = available;
    	this.backup1 = backup1;
    	this.backup2 = backup2;
    	this.backup3 = backup3;
    }
    
    public PaidVoteTopicDomain(String topicName, Integer available){
    	this.topicName = topicName;
    	this.available = available;
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
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
