package com.dc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.abi.datatypes.generated.Uint256;

import com.alibaba.fastjson.JSONObject;
import com.dc.service.ContractService;

@Controller
@RequestMapping(value = "/contractTest")
public class ContractController {
	@Autowired
	private ContractService contractService;
	
	@ResponseBody
	@GetMapping("/transfer")
	public Map<String, Object> transfer(
			@RequestParam(name = "jsonStr", required = true) String jsonValue) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.parseObject(jsonValue);
		String value = jsonObject.getString("money");
		Uint256 valueUint = new Uint256(Long.parseLong(value));
		contractService.setTransferValue(valueUint);
		contractService.transferToFixedAccount();
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	@ResponseBody
	@GetMapping("/charge")
	public Map<String, Object> charge(
			@RequestParam(name = "jsonStr", required = true) String jsonValue) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.parseObject(jsonValue);
		String value = jsonObject.getString("money");
		contractService.chargeToContract(Integer.valueOf(value));
		modelMap.put("success", true);

		return modelMap;
	}
}
