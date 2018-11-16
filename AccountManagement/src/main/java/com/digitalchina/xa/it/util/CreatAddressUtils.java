package com.digitalchina.xa.it.util;

import java.security.MessageDigest;

import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

/**
 * 
 * @ClassName: CreatAddressUtils 
 * @Description: 为所给名词创建以太坊账户地址
 * @author fannl
 * @date 2018-11-16 11:56:14 
 * @version 1.0 
 *
 */
public class CreatAddressUtils {
	public static String creatAddressUtils(String param){
		System.out.println("为'" + param + "'创建账户...");
		ECKeyPair ecKeyPair= ECKeyPair.create(getSHA2HexValue(param));
		String result = "0x" + Keys.getAddress(ecKeyPair);
		System.err.println("账户为:" + result);
		return result;
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
