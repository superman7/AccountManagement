package com.dc.util;

import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import com.dc.contract.ForTest;

public class Test {
	public static void main(String[] args) {
		String url = "http://10.0.22.234:8545";
		Web3j web3j = Web3j.build(new HttpService(url));
		Web3ClientVersion web3ClientVersion;
		try {
			web3ClientVersion = web3j.web3ClientVersion().send();
			Credentials credentials = WalletUtils.loadCredentials("123456", "C://temp/keystore.json");
			
			ForTest contract = ForTest.deploy(web3j, credentials, BigInteger.valueOf(2200000000L), BigInteger.valueOf(4300000L)).send();
			String address = contract.getContractAddress();
			System.out.println(web3ClientVersion);
			System.out.println(address);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
