package com.dc.serviceImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.dc.contract.ForTest;
import com.dc.service.ContractService;

@Service
public class ContractServiceImpl implements ContractService {
	private static String ip = "http://10.0.22.234:8545";
	private static String address = "0x41bff95576a283b32ac9b4a22cc6b992873ba1a9";
	private static String keystoreAdd = "C://temp/keystore.json";
	private static String[] userKeystoreAdd = {"C://temp/userkeystore.json","C://temp/userkeystore1.json","C://temp/userkeystore2.json","C://temp/userkeystore3.json","C://temp/userkeystore4.json","C://temp/userkeystore5.json","C://temp/userkeystore6.json","C://temp/userkeystore7.json","C://temp/userkeystore8.json","C://temp/userkeystore9.json"};
	
	@Override
	public void transferToFixedAccount() {
		try {
			System.out.println("开始");
			Long startTime = System.currentTimeMillis();
			for(int j = 0; j < 1; j++) {
				for(int i = 0; i < userKeystoreAdd.length; i++) {
					Thread thread = new Thread();
					Long start = System.currentTimeMillis();
					ForTest contract = createContract(ip, "123456", userKeystoreAdd[i]);
					RemoteCall<TransactionReceipt> transactionReceipt = contract.rewordUser(BigInteger.valueOf(1L));
					String transactionHash = transactionReceipt.sendAsync().get().getTransactionHash();
					System.out.println(j + "_" + i + "_从合约提现hashcode:" + transactionHash);
					Long end = System.currentTimeMillis();
					Long t = end - start;
					System.out.println("用时：" + t);
					Thread.sleep(1000);
				}
			}
			Long endTime = System.currentTimeMillis();
			Long time = endTime - startTime;
			System.out.println("用时：" + time);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CipherException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void chargeToContract(int value) {
		try {
			ForTest contract = createContract(ip, "123456", keystoreAdd);
			RemoteCall<TransactionReceipt> transactionReceipt = contract.chargeToContract(BigInteger.valueOf(value*1000000000000000000L));
			String transactionHash = transactionReceipt.sendAsync().get().getTransactionHash();
			System.out.println("向合约充值hashcode:" + transactionHash);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CipherException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setTransferValue(Uint256 value) {
		try {
			ForTest contract = createContract(ip, "123456", keystoreAdd);
			contract.setValue(value);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CipherException e1) {
			e1.printStackTrace();
		}
	}
	
	private ForTest createContract(String ip, String password, String filePath) throws IOException, CipherException {
		Web3j web3j =Web3j.build(new HttpService(ip));
		Long start = System.currentTimeMillis();
		Credentials credentials = WalletUtils.loadCredentials(password, filePath);
		Long end = System.currentTimeMillis();
		Long t = end - start;
		System.out.println("解锁用时：" + t);
		Long start1 = System.currentTimeMillis();
		ForTest contract = ForTest.load(address, web3j, credentials, BigInteger.valueOf(2200000000L), BigInteger.valueOf(4300000L));
		Long end1 = System.currentTimeMillis();
		Long t1 = end1 - start1;
		System.out.println("加载合约用时：" + t1);
		web3j.shutdown();
		return contract;
	}
}
