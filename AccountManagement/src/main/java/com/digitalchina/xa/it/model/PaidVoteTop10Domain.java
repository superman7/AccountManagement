package com.digitalchina.xa.it.model;

public class PaidVoteTop10Domain {
	
	private Integer id;

	private Integer topicId;
	
    private String beVotedAddress;

    private String beVoteItcode;
    
    private Integer numberOfVotes;
    
    private Integer order;
    
    private String backup1;
    
    private String backup2;
    
    private String backup3;

    public PaidVoteTop10Domain(){
    	
    }
    
    public PaidVoteTop10Domain(Integer topicId, String beVotedAddress, String beVoteItcode, Integer numberOfVotes, 
    		Integer order, String backup1, String backup2, String backup3){
    	this.topicId = topicId;
    	this.beVotedAddress = beVotedAddress;
    	this.beVoteItcode = beVoteItcode;
    	this.numberOfVotes = numberOfVotes;
    	this.order = order;
    	this.backup1 = backup1;
    	this.backup2 = backup2;
    	this.backup3 = backup3;
    }
    
    public PaidVoteTop10Domain(Integer topicId, String beVotedAddress, String beVoteItcode, Integer numberOfVotes, Integer order){
    	this.topicId = topicId;
    	this.beVotedAddress = beVotedAddress;
    	this.beVoteItcode = beVoteItcode;
    	this.numberOfVotes = numberOfVotes;
    	this.order = order;
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public void setTopicId(Integer topicId) {
		this.topicId = topicId;
	}

	public String getBeVotedAddress() {
		return beVotedAddress;
	}

	public void setBeVotedAddress(String beVotedAddress) {
		this.beVotedAddress = beVotedAddress;
	}

	public String getBeVoteItcode() {
		return beVoteItcode;
	}

	public void setBeVoteItcode(String beVoteItcode) {
		this.beVoteItcode = beVoteItcode;
	}

	public Integer getNumberOfVotes() {
		return numberOfVotes;
	}

	public void setNumberOfVotes(Integer numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
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
