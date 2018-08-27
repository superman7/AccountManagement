package com.dc.serviceImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

@Service
public class ContractServiceImpl implements ContractService {
	private static String ip = "http://10.0.22.234:8545";
	private static String address = "0x41bff95576a283b32ac9b4a22cc6b992873ba1a9";
	private static String keystoreAdd = "C://temp/keystore.json";
	private static String[] userKeystoreAdd = {"C://temp/userkeystore.json","C://temp/userkeystore1.json","C://temp/userkeystore2.json","C://temp/userkeystore3.json","C://temp/userkeystore4.json","C://temp/userkeystore5.json","C://temp/userkeystore6.json","C://temp/userkeystore7.json","C://temp/userkeystore8.json","C://temp/userkeystore9.json"};
	private static List<String> list = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public void transferToFixedAccount() {
		System.out.println("任务开始……");
		Long startTime = System.currentTimeMillis();
		list = Arrays.asList(userKeystoreAdd);
		
        int size = 4;
        int count = list.size()/size;
        if(count*size != list.size()){
            count ++;
        }
        int countNum = 0;
        final CountDownLatch countDownLatch =  new CountDownLatch(count);
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
        while (countNum < userKeystoreAdd.length){
            countNum += size;
            ConCallable callable = new ConCallable();
            //截取list的数据，分给不同线程处理
            callable.setList(ImmutableList.copyOf(list.subList(countNum - size,countNum < list.size() ? countNum : list.size())));
            @SuppressWarnings("rawtypes")
			ListenableFuture listenableFuture = listeningExecutorService.submit(callable);
            Futures.addCallback(listenableFuture, new FutureCallback<List<String>>(){

				@Override
				public void onFailure(Throwable arg0) {
					countDownLatch.countDown();
					System.out.println("处理出错");
				}

				@Override
				public void onSuccess(List<String> arg0) {
					countDownLatch.countDown();
				}
            	
            });
        }
        try {
			countDownLatch.await(30, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//		for(int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i) + "_开始");
//			Long start = System.currentTimeMillis();
//			
//			Web3j web3j =Web3j.build(new HttpService(ip));
//			Credentials credentials;
//			try {
//				credentials = WalletUtils.loadCredentials("123456", list.get(i));
//				ForTest contract = ForTest.load(address, web3j, credentials, BigInteger.valueOf(2200000000L), BigInteger.valueOf(4300000L));
//				web3j.shutdown();
//				
//				RemoteCall<TransactionReceipt> transactionReceipt = contract.rewordUser(BigInteger.valueOf(1L));
//				String transactionHash = transactionReceipt.sendAsync().get().getTransactionHash();
//				System.out.println(list.get(i) + "_" + "_从合约提现hashcode:" + transactionHash);
//				Thread.sleep(1000);
//				Long end = System.currentTimeMillis();
//				Long ti = end - start;
//				System.out.println(list.get(i) + "_" + "用时：" + ti);
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (CipherException e) {
//				e.printStackTrace();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
//		}
        
        Long endTime = System.currentTimeMillis();
        Long time = endTime - startTime;
        System.out.println("任务结束……	用时：" + time);
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
		Credentials credentials = WalletUtils.loadCredentials(password, filePath);
		ForTest contract = ForTest.load(address, web3j, credentials, BigInteger.valueOf(2200000000L), BigInteger.valueOf(4300000L));
		web3j.shutdown();
		return contract;
	}
}
