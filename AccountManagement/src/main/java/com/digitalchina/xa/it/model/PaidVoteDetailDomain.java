package com.digitalchina.xa.it.model;

public class PaidVoteDetailDomain {
	
	private Integer id;

	private Integer topicId;
	
	private String votedAddress;
	
    private String beVotedAddress;

    private String transactionHash;
    
    private String beVoteItcode;
    
    private Integer numberOfVotes;
    
    private Integer transactionStatus;
    
    private String backup1;
    
    private String backup2;
    
    private String backup3;

    public PaidVoteDetailDomain(Integer topicId, String votedAddress, String beVotedAddress, String transactionHash,
    		String beVoteItcode, Integer numberOfVotes, Integer transactionStatus, String backup1, String backup2, String backup3){
    	this.topicId = topicId;
    	this.votedAddress = votedAddress;
    	this.beVotedAddress = beVotedAddress;
    	this.transactionHash = transactionHash;
    	this.beVoteItcode = beVoteItcode;
    	this.numberOfVotes = numberOfVotes;
    	this.transactionStatus = transactionStatus;
    	this.backup1 = backup1;
    	this.backup2 = backup2;
    	this.backup3 = backup3;
    }
    
    public PaidVoteDetailDomain(Integer topicId, String votedAddress, String beVotedAddress, String transactionHash, String beVoteItcode, Integer numberOfVotes, Integer transactionStatus){
    	this.topicId = topicId;
    	this.votedAddress = votedAddress;
    	this.beVotedAddress = beVotedAddress;
    	this.transactionHash = transactionHash;
    	this.beVoteItcode = beVoteItcode;
    	this.numberOfVotes = numberOfVotes;
    	this.transactionStatus = transactionStatus;
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

	public String getVotedAddress() {
		return votedAddress;
	}

	public void setVotedAddress(String votedAddress) {
		this.votedAddress = votedAddress;
	}

	public String getBeVotedAddress() {
		return beVotedAddress;
	}

	public void setBeVotedAddress(String beVotedAddress) {
		this.beVotedAddress = beVotedAddress;
	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
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

	public Integer getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(Integer transactionStatus) {
		this.transactionStatus = transactionStatus;
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
