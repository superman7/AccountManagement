package com.digitalchina.xa.it.model;

public class SystemTransactionDetailDomain {
	    private int id;

		private String fromcount;

	    private String tocount;

	    private Double value;
	    
	    private Double gas;	

	    private String turndate;
	    
	    private int flag;
	    
	    private String remark;
	    
	    private String fromitcode;
	    
	    private String toitcode;
	    
	    private String turnhash;
	    
	    private int timer;
	    
	    private String contracttype;
	    
	    private int contractid;
	    
	    private String backup1;
	    
	    private String backup2;
	    
	    private String backup3;
	    
	    private int backup4;
	    
	    private int backup5;
	    
	    public SystemTransactionDetailDomain(){
	    	
	    }
	    
		public SystemTransactionDetailDomain(String fromcount, String tocount, Double value, Double gas, String turndate, int flag, String remark, String fromitcode, String toitcode, 
				String turnhash, int timer, String contracttype, int contractid) {
			this.fromcount = fromcount;
			this.tocount = tocount;
			this.value = value;
			this.gas = gas;
			this.turndate = turndate;
			this.flag = flag;
			this.remark = remark;
			this.fromitcode = fromitcode;
			this.toitcode = toitcode;
			this.turnhash = turnhash;
			this.timer = timer;
			this.contracttype = contracttype;
			this.contractid = contractid;
		}
		
		public SystemTransactionDetailDomain(String fromcount, String tocount, Double value, Double gas, String turndate, int flag, String remark, String fromitcode, String toitcode, 
				String turnhash, int timer, String contracttype, int contractid, String backup1, String backup2, String backup3, int backup4, int backup5) {
			this.fromcount = fromcount;
			this.tocount = tocount;
			this.value = value;
			this.gas = gas;
			this.turndate = turndate;
			this.flag = flag;
			this.remark = remark;
			this.fromitcode = fromitcode;
			this.toitcode = toitcode;
			this.turnhash = turnhash;
			this.timer = timer;
			this.contracttype = contracttype;
			this.contractid = contractid;
			this.backup1 = backup1;
			this.backup2 = backup2;
			this.backup3 = backup3;
			this.backup4 = backup4;
			this.backup5 = backup5;
		}
	    
		public int getId() {
			return id;
		}

		public void setId(int id) {
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

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
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

		public int getTimer() {
			return timer;
		}

		public void setTimer(int timer) {
			this.timer = timer;
		}

		public String getContracttype() {
			return contracttype;
		}

		public void setContracttype(String contracttype) {
			this.contracttype = contracttype;
		}

		public int getContractid() {
			return contractid;
		}

		public void setContractid(int contractid) {
			this.contractid = contractid;
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

		public int getBackup4() {
			return backup4;
		}

		public void setBackup4(int backup4) {
			this.backup4 = backup4;
		}

		public int getBackup5() {
			return backup5;
		}

		public void setBackup5(int backup5) {
			this.backup5 = backup5;
		}
}
