package com.digitalchina.xa.it.model;

public class PaidReadDetailDomain {
	
	private Integer id;

    private String paidItcode;
    
    private String paidAddress;

    private Integer articleId;
    
    private Integer paidValue;
    
    private String transactionHash;
	
	private String transactionStatus;
    
    private String backup1;
    
    private String backup2;
    
    private String backup3;

    public PaidReadDetailDomain(){
    	
    }
    
    public PaidReadDetailDomain(Integer id, String paidItcode, String paidAddress, Integer articleId, Integer paidValue, String transactionHash, String transactionStatus, String backup1, String backup2, String backup3){
    	this.id = id;
    	this.paidItcode = paidItcode;
    	this.paidAddress = paidAddress;
    	this.articleId = articleId;
    	this.paidValue = paidValue;
    	this.transactionHash = transactionHash;
    	this.transactionStatus = transactionStatus;
    	this.backup1 = backup1;
    	this.backup2 = backup2;
    	this.backup3 = backup3;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPaidItcode() {
		return paidItcode;
	}

	public void setPaidItcode(String paidItcode) {
		this.paidItcode = paidItcode;
	}

	public String getPaidAddress() {
		return paidAddress;
	}

	public void setPaidAddress(String paidAddress) {
		this.paidAddress = paidAddress;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public Integer getPaidValue() {
		return paidValue;
	}

	public void setPaidValue(Integer paidValue) {
		this.paidValue = paidValue;
	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
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
