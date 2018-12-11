package com.digitalchina.xa.it.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.LessonBuyDomain;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;
import com.digitalchina.xa.it.util.HttpRequest;

@Controller
@RequestMapping(value = "/paidLottery")
public class PaidLotteryController {
	
	@ResponseBody
	@PostMapping("/insertLotteryInfo")
	public Map<String, Object> insertLotteryInfo(
	        @RequestParam(name = "param", required = true) String jsonValue){
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
		return null;
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
