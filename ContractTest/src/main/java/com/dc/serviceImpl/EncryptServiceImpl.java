package com.dc.serviceImpl;

import java.net.URLDecoder;

import com.dc.service.EncryptService;
import com.dc.util.Encrypt;
import com.dc.util.EncryptImpl;

public class EncryptServiceImpl implements EncryptService{

	@Override
	public String decrypt(String jsonValue) throws Exception {
		String data = null;
		Encrypt encrypt = new EncryptImpl();
    	String decrypt = null;
    	decrypt = encrypt.decrypt(jsonValue);
    	data = URLDecoder.decode(decrypt, "utf-8");
		
		return data;
	}
}
