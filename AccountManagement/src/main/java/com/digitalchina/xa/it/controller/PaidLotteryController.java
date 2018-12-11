package com.digitalchina.xa.it.controller;

import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;
import com.digitalchina.xa.it.service.TPaidlotteryDetailsService;
import com.digitalchina.xa.it.util.DecryptAndDecodeUtils;
import com.digitalchina.xa.it.util.HttpRequest;
import com.digitalchina.xa.it.util.TConfigUtils;

@Controller
@RequestMapping(value = "/paidLottery")
public class PaidLotteryController {
	
	@Autowired
	private TPaidlotteryDetailsService tPaidlotteryDetailsService;
	
	@ResponseBody
	@PostMapping("/insertLotteryInfo")
	public Map<String, Object> insertLotteryInfo(
	        @RequestParam(name = "param", required = true) String jsonValue){
		Map<String, Object> modelMap = DecryptAndDecodeUtils.decryptAndDecode(jsonValue);
		if(!(boolean) modelMap.get("success")){
			return modelMap;
		}
		JSONObject jsonObj = JSONObject.parseObject((String) modelMap.get("data"));
		
		
		return null;
	}
	
	@ResponseBody
	@PostMapping("/insertLotteryDetails")
	public Map<String, Object> insertLotterydetails(
			@RequestParam(name = "param", required = true) String jsonValue){
		/*
		 * 1.插入detail信息
		 * 2.调用kafka进行合约交易
		 */
		Map<String, Object> modelMap = DecryptAndDecodeUtils.decryptAndDecode(jsonValue);
		if(!(boolean) modelMap.get("success")){
			return modelMap;
		}
		JSONObject jsonObj = JSONObject.parseObject((String) modelMap.get("data"));
		Integer lotteryId = Integer.valueOf(jsonObj.getString("lotteryId"));
		String itcode = jsonObj.getString("itcode");
		
		//向t_paidlottery_details表中插入信息， 参数为lotteryId, itcode, result(0), buyTime
		TPaidlotteryDetailsDomain tpdd = new TPaidlotteryDetailsDomain(lotteryId, itcode, "", "", "", 0, "", "", new DateTime(new Date().getTime()));
		int transactionId = tPaidlotteryDetailsService.insertLotteryBaseInfo(tpdd);
		
		//向kafka发送请求，参数为itcode, transactionId,  金额？， lotteryId？; 产生hashcode，更新account字段，并返回hashcode与transactionId。
		String url = TConfigUtils.selectValueByKey("kafka_address") + "";
		String postParam = "itcode=" + itcode + "&turnBalance=" + null + "&transactionId=" + transactionId;
		HttpRequest.sendPost(url, postParam);
		
		modelMap.put("", "");
		return modelMap;
	}
	@ResponseBody
	@PostMapping("/kafkaUpdateDetails")
	public Map<String, Object> kafkaUpdateDetails(
			@RequestParam(name = "param", required = true) String jsonValue){
		/*
		 * 1.kafka回传hash信息
		 * 2.更新奖票字段
		 * 3.判断开奖及更新info
		 */
		
		//接收hashcode与transactionId，lotteryId
		
		//更新hashcode，service层计算ticket，判断开奖条件，若不开，则更新id=transactionId的ticket字段；若开，则比对lotteryId，更新此次参与者的result，winTicket，winReword字段。
		return null;
	}
	@ResponseBody
	@GetMapping("/lotteryInfo/{lotteryInfoId}")
	public void selectLotteryInfoById(
			@RequestParam(name = "param", required = true) String jsonValue){
		
	}
	@ResponseBody
	@GetMapping("/lotteryInfo/unfinished")
	public void selectLotteryInfoUnfinished(){
		
	}
	@ResponseBody
	@GetMapping("/lotteryInfo/finished")
	public void selectLotteryInfofinished(){
		
	}
	@ResponseBody
	@GetMapping("/lotteryInfo/all")
	public void selectLotteryInfoAll(){
		/*
		 * 排序逻辑
		 * 1.按时间，当前时间到开奖时间（XX%）
		 * 2.按奖池，当前奖池到开奖金额（XX%）
		 * 3.按参与人数，当前人数到开奖人数（XX%）
		 */
	}
	@ResponseBody
	@GetMapping("/lotteryDetail/{lotteryDetailId}")
	public void selectlotteryDetailById(
			@RequestParam(name = "param", required = true) String jsonValue){
		
	}
	@ResponseBody
	@GetMapping("/lotteryDetail/{itcode}")
	public void selectlotteryDetailByItcode(
			@RequestParam(name = "param", required = true) String jsonValue){
		
	}
	@ResponseBody
	@GetMapping("/lotteryDetail/{lotteryInfoId}")
	public void selectlotteryDetailByLotteryInfoId(
			@RequestParam(name = "param", required = true) String jsonValue){
		/*
		 * 某抽奖参与详情。
		 */
	}
}
