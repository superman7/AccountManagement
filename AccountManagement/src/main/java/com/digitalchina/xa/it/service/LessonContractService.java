package com.digitalchina.xa.it.service;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

public interface LessonContractService {
	//创建web3j对象
	Web3j build();
	
	//创建凭证
	Credentials loadCredentials(String keystore, String filePath, String password) throws IOException, CipherException;
	
	//购买章节，credentials为用户账户的
	String buyChapter(Credentials credentials, String lesson, BigInteger value) throws Exception;
	
	//添加课程发布者，credentials为合约部署者账户的，lesson为课程名，address为课程发布者的账户地址
/*	void addPublisher(Web3j web3j, Credentials credentials, String lesson, String address);
	
	
	String queryPublisher(Web3j web3j, Credentials credentials, String lesson);
	
	
	void removePublisher(Web3j web3j, Credentials credentials, String lesson);
	
	
	void changePublisher(Web3j web3j, Credentials credentials, String lesson, String address);
	
	
	Double queryBalance(Web3j web3j, Credentials credentials, String address);
	
	
	void setDiscount(Web3j web3j, Credentials credentials, Double discount);*/
}
