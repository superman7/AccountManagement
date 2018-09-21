package com.digitalchina.xa.it.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import com.digitalchina.xa.it.contract.Lesson;
import com.digitalchina.xa.it.service.LessonContractService;

import scala.util.Random;

@Service(value = "LessonContractService")
public class LessonContractServiceImpl implements LessonContractService{
    private static String[] ipArr = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};
    private static String address = "0x861b6f2ca079e1cfa5da9b429fa9d82a6645b419";
    private volatile static Web3j web3j;
    
	@Override
	public Web3j build() {
		return Web3j.build(new HttpService(ipArr[new Random().nextInt(5)]));
	}
	
	@Override
	public Credentials loadCredentials(String keystore, String filePath, String password) throws IOException, CipherException {
		File file = keystoreToFile(keystore, filePath);
		Credentials credentials = WalletUtils.loadCredentials(password, file);
		file.delete();
		return credentials;
	}
    
	@Override
	public String buyChapter(Credentials credentials, String lesson, BigInteger value) throws Exception {
		Integer index = (int)(Math.random()*5);
    	String ip = ipArr[index];
		System.err.println("转账时以太坊链接的ip为"+ip);
		if(web3j==null){
            synchronized (LessonContractService.class){
                if(web3j==null){
                    web3j = Web3j.build(new HttpService(ip));
                }
            }
        }
		TransactionManager transactionManager = this.buildTransactionManager(web3j, credentials);
		if(transactionManager == null){
			System.err.println("转账模块:构建" + "交易管理异常！");
			return "error";
		}
		Lesson contract = Lesson.load(address, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		
		try {
			TransactionReceipt transactionReceipt = contract.buyChapter(value).send();
			String resultStr = transactionReceipt.getTransactionHash();
			System.out.println("购买交易hash：" + resultStr);
			//FIXME 记录交易哈希值
	    	web3j.shutdown();
			return resultStr;
		} catch (Exception e) {
			e.printStackTrace();
			web3j.shutdown();
			return "error";
		}
	}
	
/*	@Override
	public void addPublisher(Web3j web3j, Credentials credentials, String lesson, String address) {
		Lesson contract = loadContract(web3j, credentials);
		contract.addPublisher(new Utf8String(lesson), new Address(address));
	}
	
	@Override
	public String queryPublisher(Web3j web3j, Credentials credentials, String lesson) {
		Lesson contract = loadContract(web3j, credentials);
		return contract.queryPublisher(new Utf8String(lesson)).toString();
	}
	
	@Override
	public void removePublisher(Web3j web3j, Credentials credentials, String lesson) {
		Lesson contract = loadContract(web3j, credentials);
		contract.removePublisher(new Utf8String(lesson));
	}
	
	@Override
	public void changePublisher(Web3j web3j, Credentials credentials, String lesson, String address) {
		Lesson contract = loadContract(web3j, credentials);
		contract.changePublisher(new Utf8String(lesson), new Address(address));
	}
	
	@Override
	public Double queryBalance(Web3j web3j, Credentials credentials, String address) {
		Lesson contract = loadContract(web3j, credentials);
		contract.queryBalance(new Address(address));
		return Double.valueOf(contract.queryBalance(new Address(address)).toString());
	}
	
	@Override
	public void setDiscount(Web3j web3j, Credentials credentials, Double discount) {
		Lesson contract = loadContract(web3j, credentials);
		contract.setDiscount(new Uint256(discount.longValue()));
	}*/
	
	private File keystoreToFile(String keystore, String filePath) throws IOException {
		File file = new File(filePath);
        if(!file.exists()){
         file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(keystore);
        bw.close();
        
        return file;
	}
	
	private Lesson loadContract(Web3j web3j, Credentials credentials) {
		return Lesson.load(address, web3j, credentials, BigInteger.valueOf(2200000000L), BigInteger.valueOf(4300000L));
	}
	
	public TransactionManager buildTransactionManager(Web3j web3j1, Credentials credentials){
		TransactionReceiptProcessor transactionReceiptProcessor = new NoOpProcessor(web3j1);
		TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, (byte) 10, transactionReceiptProcessor);
		return transactionManager;
	}
}
