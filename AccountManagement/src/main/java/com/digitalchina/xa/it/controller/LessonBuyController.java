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
import com.digitalchina.xa.it.util.DecryptAndDecodeUtils;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;
import com.digitalchina.xa.it.util.HttpRequest;
import com.digitalchina.xa.it.util.TConfigUtils;

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
		Map<String, Object> modelMap = DecryptAndDecodeUtils.decryptAndDecode(jsonValue);
		if(!(boolean) modelMap.get("success")){
			return modelMap;
		}
		JSONObject jsonObj = JSONObject.parseObject((String) modelMap.get("data"));
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
		
		//余额判断
		
		//向kafka集群发送扣费信息
		String url = TConfigUtils.selectValueByKey("kafka_address") + "/lessonBuy/processDeduction";
		String postParam = "itcode=" + itcode + "&transactionDetailId=" + transactionDetailId + "&turnBalance=" + turnBalance;
		HttpRequest.sendPost(url, postParam);
		
		return modelMap;
	}
}
