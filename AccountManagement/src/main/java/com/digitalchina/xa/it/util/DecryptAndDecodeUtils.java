package com.digitalchina.xa.it.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class DecryptAndDecodeUtils {
	public static Map<String, Object> decryptAndDecode(String param){
		Map<String, Object> modelMap = new HashMap<String, Object>();

		Encrypt encrypt = new EncryptImpl();
		String decrypt = null;
		try {
			decrypt = encrypt.decrypt(param);
		} catch (Exception e1) {
			e1.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "解密失败！");
			return modelMap;
		}
		String data = null;
		try {
			data = URLDecoder.decode(decrypt, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", "解密失败！非utf-8编码。");
			return modelMap;
		}
		modelMap.put("success", true);
		modelMap.put("data", data);
		
		return modelMap;
	}
}
