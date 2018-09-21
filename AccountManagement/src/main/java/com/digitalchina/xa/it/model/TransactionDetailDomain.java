package com.digitalchina.xa.it.model;

public class TransactionDetailDomain {
	
	private Integer id;
	
	private String fromcount;
	
	private String tocount;
	
	private Double value;
	
	private Double gas;
	
	private String turndate;
	
	private Integer flag;
	
	private String remark;
	
	private String fromitcode;
	
	private String toitcode;
	
	private String turnhash;
	
	private String timer;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFromcount() {
		return fromcount;
	}
	public void setFromcount(String fromcount) {
		this.fromcount = fromcount;
	}
	public String getTocount() {
		return tocount;
	}
	public void setTocount(String tocount) {
		this.tocount = tocount;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getGas() {
		return gas;
	}
	public void setGas(Double gas) {
		this.gas = gas;
	}
	public String getTurndate() {
		return turndate;
	}
	public void setTurndate(String turndate) {
		this.turndate = turndate;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFromitcode() {
		return fromitcode;
	}
	public void setFromitcode(String fromitcode) {
		this.fromitcode = fromitcode;
	}
	public String getToitcode() {
		return toitcode;
	}
	public void setToitcode(String toitcode) {
		this.toitcode = toitcode;
	}
	public String getTurnhash() {
		return turnhash;
	}
	public void setTurnhash(String turnhash) {
		this.turnhash = turnhash;
	}
	public String getTimer() {
		return timer;
	}
	public void setTimer(String timer) {
		this.timer = timer;
	}
}
