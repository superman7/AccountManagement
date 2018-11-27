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
import com.digitalchina.xa.it.util.DecryptAndDecodeUtils;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;


@Controller
@RequestMapping(value = "/ethTransaction")
public class WalletTransactionController {
	@Autowired
	private WalletTransactionService walletTransactionService;
	@Autowired
	private EthAccountService ethAccountService;
	private static String address = "0x024a3c0d945739237eedf78c80c6ae5daf22c010";
	
	//查询用户的所有交易记录
	@ResponseBody
	@GetMapping("/transRecords")
	public Map<String, Object> transRecords(
			@RequestParam(name = "param", required = true) String jsonValue) {
		Map<String, Object> modelMap = DecryptAndDecodeUtils.decryptAndDecode(jsonValue);
		if(!(boolean) modelMap.get("success")){
			return modelMap;
		}
		JSONObject jsonObj = JSONObject.parseObject((String) modelMap.get("data"));
    	String itcode = jsonObj.getString("itcode");
		List<WalletTransactionDomain> wtdList = walletTransactionService.selectRecordsByItcode(itcode);
		
		if(wtdList != null) {
			modelMap.put("records", wtdList);
		} else {
			modelMap.put("success", false);
		}
		
		return modelMap;
	}
}
