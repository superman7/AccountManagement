package com.digitalchina.xa.it.model;

import java.sql.Timestamp;

public class LuckycashDomain {
	private Integer id;
	private String luckyNum;
	private Integer available;
	private String luckyGuys;
	private Integer luckyGuysCount;
	private Timestamp availableTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAvailable() {
		return available;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public String getLuckyGuys() {
		return luckyGuys;
	}
	public void setLuckyGuys(String luckyGuys) {
		this.luckyGuys = luckyGuys;
	}
	public Integer getLuckyGuysCount() {
		return luckyGuysCount;
	}
	public void setLuckyGuysCount(Integer luckyGuysCount) {
		this.luckyGuysCount = luckyGuysCount;
	}
	public Timestamp getAvailableTime() {
		return availableTime;
	}
	public void setAvailableTime(Timestamp availableTime) {
		this.availableTime = availableTime;
	}
	public String getLuckyNum() {
		return luckyNum;
	}
	public void setLuckyNum(String luckyNum) {
		this.luckyNum = luckyNum;
	}
}
