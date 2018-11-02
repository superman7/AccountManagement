package com.digitalchina.xa.it.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.PaidReadArticleDomain;
import com.digitalchina.xa.it.model.PaidVoteDetailDomain;
import com.digitalchina.xa.it.service.EthAccountService;
import com.digitalchina.xa.it.service.PaidReadArticleService;
import com.digitalchina.xa.it.service.PaidReadAuthorService;
import com.digitalchina.xa.it.service.PaidReadDetailService;
import com.digitalchina.xa.it.service.PaidVoteDetailService;
import com.digitalchina.xa.it.service.PaidVoteTop10Service;
import com.digitalchina.xa.it.service.PaidVoteTopicService;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;

@Controller
@RequestMapping(value = "/paidRead")
public class PaidReadController {
	@Autowired
	private PaidReadDetailService paidReadDetailService;
	@Autowired
	private PaidReadArticleService paidReadArticleService;
	@Autowired
	private PaidReadAuthorService paidReadAuthorService;
	@Autowired
	private EthAccountService ethAccountService;
	
	@ResponseBody
	@GetMapping("/insertArticle")
	public Object insertArticle(
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
    	System.err.println("解密后参数为：" + data);
    	JSONObject jsonObj = JSONObject.parseObject(data);
    	String articleName = jsonObj.getString("articleName");
    	String itcode = jsonObj.getString("itcode");
    	String articleContent = jsonObj.getString("articleContent");
    	
    	Integer articleFreePart = Integer.valueOf(jsonObj.getString("articleFreePart"));
    	Integer articlePrice = Integer.valueOf(jsonObj.getString("articlePrice"));
    	Integer authorNum = 1;
    	
    	
    	PaidReadArticleDomain paidReadArticleDomain = new PaidReadArticleDomain(articleName, authorNum, articleContent, 1, 30, articleFreePart, articlePrice);
		int result = paidReadArticleService.insertPaidReadArticle(paidReadArticleDomain);
		if(result > 0) {
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "insert failed");
		}
		
		return modelMap;
	}
	
	@ResponseBody
	@GetMapping("/selectArticle")
	public List<PaidReadArticleDomain> selectArticle(@RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
		List<PaidReadArticleDomain> result = paidReadArticleService.selectPaidReadArticleByTime(pageNum, pageSize);
				return result;
	}
}
