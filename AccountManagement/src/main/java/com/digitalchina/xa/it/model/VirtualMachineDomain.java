package com.digitalchina.xa.it.model;

public class VirtualMachineDomain {
	private Integer id;
	private String ip;
	private String user;
	private String password;
	//0:不可使用；1:可使用；2:已被使用;3：正在被申请中
	private Integer status;
	private String usableDate;
	private String userItcode;
	private String spare1;
	private String spare2;
	private String spare3; 
	private String applystatus;
	private String transactionHash;
	private String resourceid;
	private String taskid;
	private String editdate;
	private String hasnet;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getSpare1() {
		return spare1;
	}
	public void setSpare1(String spare1) {
		this.spare1 = spare1;
	}
	public String getSpare2() {
		return spare2;
	}
	public void setSpare2(String spare2) {
		this.spare2 = spare2;
	}
	public String getSpare3() {
		return spare3;
	}
	public void setSpare3(String spare3) {
		this.spare3 = spare3;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserItcode() {
		return userItcode;
	}
	public void setUserItcode(String userItcode) {
		this.userItcode = userItcode;
	}
	public String getUsableDate() {
		return usableDate.substring(0, usableDate.indexOf("."));
	}
	public void setUsableDate(String usableDate) {
		this.usableDate = usableDate;
	}
	public String getApplystatus() {
		return applystatus;
	}
	public void setApplystatus(String applystatus) {
		this.applystatus = applystatus;
	}
	public String getTransactionHash() {
		return transactionHash;
	}
	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}
	public String getResourceid() {
		return resourceid;
	}
	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getEditdate() {
		return editdate;
	}
	public void setEditdate(String editdate) {
		this.editdate = editdate;
	}
	public String getHasnet() {
		return hasnet;
	}
	public void setHasnet(String hasnet) {
		this.hasnet = hasnet;
	}
	
}
