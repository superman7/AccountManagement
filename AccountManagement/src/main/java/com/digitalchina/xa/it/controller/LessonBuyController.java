package com.digitalchina.xa.it.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.LessonBuyDomain;
import com.digitalchina.xa.it.model.LessonDetailDomain;
import com.digitalchina.xa.it.service.EthAccountService;
import com.digitalchina.xa.it.service.LessonBuyService;
import com.digitalchina.xa.it.service.LessonContractService;
import com.digitalchina.xa.it.service.LessonDetailService;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;
import com.digitalchina.xa.it.util.HttpRequest;

@Controller
@RequestMapping(value = "/lessonBuy")
public class LessonBuyController {
	@Autowired
	private LessonBuyService lessonBuyService;
	@Autowired
	private LessonContractService lessonContractService;
	@Autowired
	private EthAccountService ethAccountService;
	
	@ResponseBody
	@GetMapping("/insertBuyInfo")
	public Map<String, Object> updateChapter(
	        @RequestParam(name = "param", required = true) String jsonValue){
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
    	System.err.println("解密的JSON为:" + data);
    	JSONObject jsonObj = JSONObject.parseObject(data);
		String itcode = jsonObj.getString("itcode");
		String chapterNum = jsonObj.getString("chapterNum");
		String lessonId = jsonObj.getString("lessonId");
		
		LessonBuyDomain lbd = lessonBuyService.selectCostAndDiscount(chapterNum, lessonId);
		Double cost = lbd.getCost();
		Double discount = lbd.getDiscount();
		
		LessonBuyDomain lb = new LessonBuyDomain();
		lb.setLessonId(Integer.valueOf(lessonId));
		lb.setChapterNum(Integer.valueOf(chapterNum));
		lb.setItcode(itcode);
		lb.setCost(cost);
		lb.setDiscount(discount);
		lb.setBuyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		lb.setType(1);
		Integer transactionDetailId = lessonBuyService.insertBuyInfo(lb);
		BigInteger turnBalance = BigInteger.valueOf((long) (cost*discount/10)*10000000000000000L);
		System.out.println("*******记录购买信息********");
		
		//向kafka集群发送扣费信息
		String url = "http://10.7.10.124:8083/lessonBuy/processDeduction";
		String postParam = "itcode=" + itcode + "&transactionDetailId=" + transactionDetailId + "&turnBalance=" + turnBalance;
		HttpRequest.sendPost(url, postParam);
		
		modelMap.put("success", true);		
		return modelMap;
	}
}
