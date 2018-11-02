package com.digitalchina.xa.it.model;

public class PaidReadArticleDomain {
	
	private Integer id;

    private String name;
    
    private Integer author;

    private Integer available;
    
    private Integer balance;
    
    private Integer freePart;
    
    private Integer price;
    
    private String content;
    
    private String backup1;
    
    private String backup2;
    
    private String backup3;

    public PaidReadArticleDomain(){
    	
    }

    public PaidReadArticleDomain(Integer id, String name, Integer author, Integer available, Integer balance, Integer freePart, Integer price, String content, String backup1, String backup2, String backup3){
    	this.id = id;
    	this.name = name;
    	this.author = author;
    	this.available = available;
    	this.balance = balance;
    	this.freePart = freePart;
    	this.price = price;
    	this.content = content;
    	this.backup1 = backup1;
    	this.backup2 = backup2;
    	this.backup3 = backup3;
    }
    public PaidReadArticleDomain(String name, Integer author, String content, Integer available, Integer balance, Integer freePart, Integer price){
    	this.name = name;
    	this.author = author;
    	this.available = available;
    	this.balance = balance;
    	this.freePart = freePart;
    	this.price = price;
    	this.content = content;
    }
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAuthor() {
		return author;
	}

	public void setAuthor(Integer author) {
		this.author = author;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getFreePart() {
		return freePart;
	}

	public void setFreePart(Integer freePart) {
		this.freePart = freePart;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
