package com.digitalchina.xa.it.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.LessonDetailDomain;
import com.digitalchina.xa.it.service.LessonBuyService;
import com.digitalchina.xa.it.service.LessonDetailService;
import com.digitalchina.xa.it.service.LessonsService;
import com.digitalchina.xa.it.util.DecryptAndDecodeUtils;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;

@Controller
@RequestMapping(value = "/lessons")
public class LessonsController {
	@Autowired
	private LessonDetailService lessonDetailService;
	@Autowired
	private LessonBuyService lessonBuyService;
	@Autowired
	private LessonsService lessonsService;
	
	//更新用户最新阅读的章节及最近阅读时间
	@ResponseBody
	@GetMapping("/updateChapter")
	public Object updateChapter(
	        @RequestParam(name = "param", required = true) String jsonValue){
		Map<String, Object> modelMap = DecryptAndDecodeUtils.decryptAndDecode(jsonValue);
		if(!(boolean) modelMap.get("success")){
			return modelMap;
		}
		JSONObject jsonObj = JSONObject.parseObject((String) modelMap.get("data"));
		String itcode = jsonObj.getString("itcode");
		String chapter = jsonObj.getString("chapter");
		String lessonId = jsonObj.getString("lessonId");
		String backup1 = chapter.split("_")[0];
		
		String backup1_ = lessonDetailService.selectBackup1(itcode, Integer.parseInt(lessonId));
		int res = Integer.parseInt(backup1) - Integer.parseInt(backup1_);
		if( res <= 5 && res >=0) {
			LessonDetailDomain ld = new LessonDetailDomain();
			ld.setItcode(itcode);
			ld.setChapter(chapter);
			ld.setLessonId(Integer.parseInt(lessonId));
			ld.setRecentTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			ld.setBackup1(backup1);
			lessonDetailService.updateChapterAndRecentTime(ld);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "skippingReading");
		}
		
		return modelMap;
	}
	
	//插入用户首次阅读的课程编号及itcode
	@ResponseBody
	@GetMapping("/insertItcode")
	public Object insertItcode(
	        @RequestParam(name = "param", required = true) String jsonValue){
		Map<String, Object> modelMap = DecryptAndDecodeUtils.decryptAndDecode(jsonValue);
		if(!(boolean) modelMap.get("success")){
			return modelMap;
		}
		JSONObject jsonObj = JSONObject.parseObject((String) modelMap.get("data"));
		String itcode = jsonObj.getString("itcode");
		String lesson = jsonObj.getString("lesson");
		
		//FIXME 暂时修改课程学习重复记录itcode的bug 
		//此处暂时添加第三个参数，lessonId
		String lessonId = jsonObj.getString("lessonid");
		//LessonDetailDomain ld = lessonDetailService.selectOneRecord(itcode, lesson);
		//System.out.println(ld);
		Integer counter = lessonDetailService.selectLessonAndItcodeRecord(itcode, Integer.valueOf(lessonId));
		System.err.println("counter : " + counter);
		if(counter < 1){
			lessonDetailService.insertItcode(itcode, lesson);
		}
		
		List<String> userPurchased = lessonBuyService.selectUserPurchased(itcode, lessonId);
		List<String> freeChapter = lessonBuyService.selectFreeChapter(lessonId	);
		userPurchased.addAll(freeChapter);
		modelMap.put("data", userPurchased);
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	//查询每一个科目的阅读人数
	@ResponseBody
	@GetMapping("/getCount")
	public Object getCount(
	        @RequestParam(name = "param", required = true) String jsonValue){
		Map<String, Object> modelMap = DecryptAndDecodeUtils.decryptAndDecode(jsonValue);
		if(!(boolean) modelMap.get("success")){
			return modelMap;
		}
		JSONObject jsonObj = JSONObject.parseObject((String) modelMap.get("data"));
    	for(String key : jsonObj.keySet()) {
    		Integer count = lessonDetailService.selectOrderCount(jsonObj.getString(key));
    		modelMap.put(key, count);
    	}
		modelMap.put("success", true);
		
		return modelMap;
	}
	
	@ResponseBody
	@GetMapping("/top10")
	public String getTopTen() {
		List<Map<String, Object>> dataList = lessonsService.selectTop10();
		List<Map<String, Object>> returnList = new ArrayList<>();
		for(int i = 0; i < dataList.size(); i++) {
			Map<String, Object> map = new HashMap<>();
			Double value = new BigDecimal((Double)dataList.get(i).get("balance")).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			map.put("key", dataList.get(i).get("itcode"));
			map.put("value", value);
			returnList.add(map);
		}
		String data = JSON.toJSONString(returnList);
		return data;
	}
}
