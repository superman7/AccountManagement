package com.digitalchina.xa.it.model;

public class KeywordToAccountDomain {
	
	private String keyword;
    
    private String account;
    
    private String backup1;
    
    private String backup2;
    
    private String backup3;
    
    public KeywordToAccountDomain(String keyword, String account, String backup1, String backup2, String backup3){
    	this.account = account;
    	this.keyword = keyword;
    	this.backup1 = backup1;
    	this.backup2 = backup2;
    	this.backup3 = backup3;
    }
    
    public KeywordToAccountDomain(String keyword, String account){
    	this.account = account;
    	this.keyword = keyword;
    }
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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
