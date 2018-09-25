package com.digitalchina.xa.it.kafka;

import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

public class KafkaConsumerBean {
    //链ID
    private static final byte chainId = (byte) 10;
    //交易记录表主键字段（update交易hash使用）
    private Integer transactionDetailId;
	//合约java类
	private String contractName;
	//用户凭证
	private Credentials credentials;
	//web3j连接
    private Web3j web3j;
    //合约地址
	private String address;
	//gas价格
    private BigInteger gasPrice;
    //gas限制
    private BigInteger gasLimit;
    //转账金额
    private BigInteger turnBalance;
    
    KafkaConsumerBean(Integer transactionDetailId, String contractName, Credentials credentials, Web3j web3j, String address, BigInteger gasPrice, BigInteger gasLimit, BigInteger turnBalance){
    	this.transactionDetailId = transactionDetailId;
    	this.contractName = contractName;
    	this.credentials = credentials;
    	this.web3j = web3j;
    	this.address = address;
    	this.gasPrice = gasPrice;
    	this.gasLimit = gasLimit;
    	this.turnBalance = turnBalance;
    }
    
    KafkaConsumerBean(Integer transactionDetailId, String contractName, Credentials credentials, Web3j web3j, String address, BigInteger turnBalance){
    	this.transactionDetailId = transactionDetailId;
    	this.contractName = contractName;
    	this.credentials = credentials;
    	this.web3j = web3j;
    	this.address = address;
    	this.gasPrice = ManagedTransaction.GAS_PRICE;
    	this.gasLimit = Contract.GAS_LIMIT;
    	this.turnBalance = turnBalance;
    }
    
	public Integer getTransactionDetailId() {
		return transactionDetailId;
	}
	public void setTransactionDetailId(Integer transactionDetailId) {
		this.transactionDetailId = transactionDetailId;
	}
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public Web3j getWeb3j() {
		return web3j;
	}
	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public BigInteger getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(BigInteger gasPrice) {
		this.gasPrice = gasPrice;
	}
	public BigInteger getGasLimit() {
		return gasLimit;
	}
	public void setGasLimit(BigInteger gasLimit) {
		this.gasLimit = gasLimit;
	}
	public static byte getChainid() {
		return chainId;
	}

	public BigInteger getTurnBalance() {
		return turnBalance;
	}

	public void setTurnBalance(BigInteger turnBalance) {
		this.turnBalance = turnBalance;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
}
