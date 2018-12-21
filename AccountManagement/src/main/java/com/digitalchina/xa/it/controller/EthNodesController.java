package com.digitalchina.xa.it.controller;

import java.io.IOException;
import java.math.BigInteger;
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
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.TConfigDomain;
import com.digitalchina.xa.it.service.PaidVoteDetailService;
import com.digitalchina.xa.it.service.TConfigService;
import com.digitalchina.xa.it.util.TConfigUtils;

@Controller
@RequestMapping(value = "/ethNodes")
public class EthNodesController {
	@Autowired
	private TConfigService tconfigService;
	
	@ResponseBody
	@GetMapping("/statusCheck")
	public Map<String, Object> statusCheck(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<TConfigDomain> dataList = tconfigService.selectEthNodesInfo();
		modelMap.put("success", true);
		modelMap.put("data", JSONObject.toJSON(dataList));
		return modelMap;
	} 
	
	@ResponseBody
	@GetMapping("/contractBalance")
	public Map<String, Object> contractBalance(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<TConfigDomain> dataList = tconfigService.selectContractInfo();
		Web3j web3j =Web3j.build(new HttpService(TConfigUtils.selectIp()));
		
		for(int index = 0; index < dataList.size(); index++) {
			try {
				BigInteger balance = web3j.ethGetBalance(dataList.get(index).getCfgValue(),DefaultBlockParameterName.LATEST).send().getBalance().divide(BigInteger.valueOf(10000000000000000L));
				dataList.get(index).setCfgExtra(balance.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		modelMap.put("success", true);
		modelMap.put("data", JSONObject.toJSON(dataList));
		return modelMap;
	} 
}
