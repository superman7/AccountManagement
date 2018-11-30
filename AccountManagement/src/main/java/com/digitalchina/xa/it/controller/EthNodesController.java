package com.digitalchina.xa.it.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.TConfigDomain;
import com.digitalchina.xa.it.service.TConfigService;

@Controller
@RequestMapping(value = "/ethNodes")
public class EthNodesController {
	@Autowired
	private TConfigService tconfigService;
	
	@ResponseBody
	@GetMapping("/statusCheck")
	public Map<String, Object> queryMine(@RequestParam(name = "itcode", required = false) String itcode){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<TConfigDomain> dataList = tconfigService.selectEthNodesInfo();
		System.out.println(JSONObject.toJSON(dataList));
		modelMap.put("success", true);
		modelMap.put("data", JSONObject.toJSON(dataList));
		return modelMap;
	} 
}
