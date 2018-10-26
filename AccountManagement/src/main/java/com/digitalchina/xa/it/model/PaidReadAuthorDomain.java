package com.digitalchina.xa.it.model;

public class PaidReadAuthorDomain {
	
	private Integer id;

    private String itcode;

    private String nickname;
    
    private String backup1;
    
    private String backup2;
    
    private String backup3;
    
    public PaidReadAuthorDomain(){
    	
    }
    
    public PaidReadAuthorDomain(Integer id, String itcode, String nickname, String backup1, String backup2, String backup3){
    	this.id = id;
    	this.itcode = itcode;
    	this.nickname = nickname;
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

	public String getItcode() {
		return itcode;
	}

	public void setItcode(String itcode) {
		this.itcode = itcode;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
