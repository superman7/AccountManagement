package com.digitalchina.xa.it.model;

public class WalletTransactionDomain {
	private Integer id;

    private String itcode;

	private String accountFrom;
	
	private String accountTo;
	
	private Double balanceFrom;
	
	private Double balanceTo;
	
	private String aliasFrom;
	
	private String aliasTo;

    private Double balance;
    
    private Double gas;
    
    private String transactionHash;
    
    private String confirmTime;
    
    private Integer confirmBlock;
    
    private Integer status;

	public Double getBalanceFrom() {
		return balanceFrom;
	}

	public void setBalanceFrom(Double balanceFrom) {
		this.balanceFrom = balanceFrom;
	}

	public Double getBalanceTo() {
		return balanceTo;
	}

	public void setBalanceTo(Double balanceTo) {
		this.balanceTo = balanceTo;
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

	public String getAccountFrom() {
		return accountFrom;
	}

	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}

	public String getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}

	public String getAliasFrom() {
		return aliasFrom;
	}

	public void setAliasFrom(String aliasFrom) {
		this.aliasFrom = aliasFrom;
	}

	public String getAliasTo() {
		return aliasTo;
	}

	public void setAliasTo(String aliasTo) {
		this.aliasTo = aliasTo;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getGas() {
		return gas;
	}

	public void setGas(Double gas) {
		this.gas = gas;
	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}

	public Integer getConfirmBlock() {
		return confirmBlock;
	}

	public void setConfirmBlock(Integer confirmBlock) {
		this.confirmBlock = confirmBlock;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
