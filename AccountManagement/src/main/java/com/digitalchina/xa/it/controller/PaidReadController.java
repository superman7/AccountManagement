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
import org.springframework.web.bind.annotation.PostMapping;
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
	@PostMapping("/insertArticle")
	public Object insertArticle(
	        @RequestParam(name = "param", required = true) String jsonValue){
		Map<String, Object> modelMap = new HashMap<String, Object>();
    	String data = null;
		try {
			data = URLDecoder.decode(jsonValue, "utf-8");
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

    	Integer articleAuthor = paidReadAuthorService.selectAuthorIfSaved(itcode);
    	Integer articleFreePart = Integer.valueOf(jsonObj.getString("articleFreePart"));
    	Integer articlePrice = Integer.valueOf(jsonObj.getString("articlePrice"));
    	Integer articleBalance = Integer.valueOf(jsonObj.getString("articleBalance"));
    	
    	PaidReadArticleDomain paidReadArticleDomain = new PaidReadArticleDomain(articleName, articleAuthor, articleContent, 1, articleBalance, articleFreePart, articlePrice);
    	
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
	
	@ResponseBody
	@GetMapping("/selectArticleByHot")
	public List<PaidReadArticleDomain> selectArticleByHot(@RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize){
		List<PaidReadArticleDomain> result = paidReadArticleService.selectPaidReadArticleByHot(pageNum, pageSize);
				return result;
	}
	
	@ResponseBody
	@GetMapping("/queryMine")
	public Map<String, Object> queryMine(@RequestParam(name = "itcode", required = false) String itcode){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int authorId = paidReadAuthorService.selectAuthorIfSaved(itcode);
		List<PaidReadArticleDomain> dataList = paidReadArticleService.selectMyArticles(String.valueOf(authorId));
		modelMap.put("success", true);
		modelMap.put("data", JSONObject.toJSON(dataList));
		return modelMap;
	}
	
	@ResponseBody
	@GetMapping("/selectContent")
	public Map<String, Object> selectContent(@RequestParam(name = "id", required = false) String id){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String content = paidReadArticleService.selectArticleContent(Integer.parseInt(id));
		paidReadArticleService.updateReadingCapacity(Integer.parseInt(id));
		if(content != null) {
			modelMap.put("success", true);
			modelMap.put("content", content);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "noArticle");
		}
		return modelMap;
	}
	
	@ResponseBody
	@GetMapping("/withdraw")
	public Map<String, Object> withdrawFromArticle(@RequestParam(name = "id", required = false) String id,
			@RequestParam(name = "itcode", required = false) String itcode){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		System.out.println(id + "---" + itcode);
		//TODO 提现
		return modelMap;
	}
}
