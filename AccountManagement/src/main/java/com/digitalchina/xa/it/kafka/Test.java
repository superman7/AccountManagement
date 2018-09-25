package com.digitalchina.xa.it.kafka;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Test {

	public static void main(String[] args) {
		Integer transactionDetailId = 1;
		String contractName = "Qiandao";
		
		String keystore = "{\"address\":\"189abcd4cb82534d9d7b2ee181b28bcc86c64853\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"92030d37f698f10ceb08b65e8cf8fcb048d75b81437d7feda004008eb3fa69c8\",\"cipherparams\":{\"iv\":\"2ef3f82c19ee50efebd9f555f6a22fc5\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"5bee728b4c1a470d9514faab1ab17eaacbdfe8692d042b50ff37f5eacc6d288e\"},\"mac\":\"f094bbc466a969bf939e755ea58900002ffaa7911b36cc4541222f01234b23e4\"},\"id\":\"c337c7c8-df9d-42f8-b59f-176ee9a04d81\",\"version\":3}";
		File keystoreFile;
		System.out.println("开始解锁。。。");
		Credentials credentials = null;
		try {
			keystoreFile = keystoreToFile(keystore, "fannl.json");
			credentials = WalletUtils.loadCredentials("mini0823", keystoreFile);
			System.out.println("解锁成功。。。");
			keystoreFile.delete();
			System.out.println("删除临时keystore文件成功。。。");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CipherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Web3j web3j = Web3j.build(new HttpService("http://10.7.10.125:8545"));
		
		String address = "0xa3bd7ba69b93d2e1f7708fafd14ba5723ae4799a";
		BigInteger turnBalance = BigInteger.valueOf(10000000000000000L);
		
		KafkaConsumerBean bean = new KafkaConsumerBean(transactionDetailId, contractName, credentials, web3j, address, turnBalance);

	}
	
	public static File keystoreToFile(String keystore, String keystoreName) throws IOException {
		File file = new File("C://" + keystoreName);
        if(!file.exists()){
         file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(keystore);
        bw.close();
        
        return file;
	}

}
