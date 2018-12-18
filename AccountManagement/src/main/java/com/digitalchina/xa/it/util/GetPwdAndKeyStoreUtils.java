package com.digitalchina.xa.it.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName: GetPwdAndKeyStoreUtils 
 * @Description: 查询所给名词账户地址的私钥和keystore文件
 * @author fannl
 * @date 2018-11-16 11:56:14 
 * @version 1.0 
 *
 */
public class GetPwdAndKeyStoreUtils {
	
	public static Map<String, String> getDefaultPwdAndKeyStoreUtils(String param){
		Map<String, String> resultMap = new HashMap<String, String>();
		
		ECKeyPair ecKeyPair= ECKeyPair.create(getSHA2HexValue(param));
		try {
			WalletFile walletFile = Wallet.createLight(TConfigUtils.selectValueByKey("default_password"), ecKeyPair);
			String keystore = ((JSONObject) JSONObject.toJSON(walletFile)).toJSONString();
			resultMap.put("keystore", keystore);
		} catch (CipherException e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	public static Map<String, String> getPwdAndKeyStoreUtils(String param){
		Map<String, String> resultMap = new HashMap<String, String>();
		
		ECKeyPair ecKeyPair= ECKeyPair.create(getSHA2HexValue(param));
		String address = "0x" + Keys.getAddress(ecKeyPair);
		
		List<String> mnemonicList = new ArrayList<String>();
		mnemonicList.add(param);
		MerkleTrees merkleTrees = new MerkleTrees(mnemonicList);
	    merkleTrees.merkle_tree();
	    String pwd = merkleTrees.getRoot().substring(0, 10);
		try {
			WalletFile walletFile = Wallet.createLight(pwd, ecKeyPair);
			String keystore = ((JSONObject) JSONObject.toJSON(walletFile)).toJSONString();
			resultMap.put("keystore", keystore);
		} catch (CipherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		resultMap.put("pwd", pwd);
		resultMap.put("address", address);
		return resultMap;
	}
	
	private static byte[] getSHA2HexValue(String str) {
        byte[] cipher_byte;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            cipher_byte = md.digest();
            return cipher_byte;
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return null;
	}
}
