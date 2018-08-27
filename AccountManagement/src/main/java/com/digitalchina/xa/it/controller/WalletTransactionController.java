package com.digitalchina.xa.it.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.WalletTransactionDomain;
import com.digitalchina.xa.it.service.EthAccountService;
import com.digitalchina.xa.it.service.WalletTransactionService;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;


@Controller
@RequestMapping(value = "/ethTransaction")
public class WalletTransactionController {
	@Autowired
	private WalletTransactionService walletTransactionService;
	@Autowired
	private EthAccountService ethAccountService;
	private static String[] ip = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};
	private static String address = "0x024a3c0d945739237eedf78c80c6ae5daf22c010";
	
	@ResponseBody
	@GetMapping("/transRecords")
	public Map<String, Object> transRecords(
			@RequestParam(name = "param", required = true) String jsonValue) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Encrypt encrypt = new EncryptImpl();
    	String decrypt = null;
		try {
			decrypt = encrypt.decrypt(jsonValue);
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
    	System.err.println("解密的助记词，密码及itcode的JSON为:" + data);
    	String itcode = JSONObject.parseObject(data).getString("itcode");
		List<WalletTransactionDomain> wtdList = walletTransactionService.selectRecordsByItcode(itcode);
		
		if(wtdList != null) {
			modelMap.put("success", true);
			modelMap.put("records", wtdList);
		} else {
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
}
