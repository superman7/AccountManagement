package com.digitalchina.xa.it.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;
import com.digitalchina.xa.it.model.TPaidlotteryInfoDomain;
import com.digitalchina.xa.it.service.EthAccountService;
import com.digitalchina.xa.it.service.TPaidlotteryService;
import com.digitalchina.xa.it.util.DecryptAndDecodeUtils;
import com.digitalchina.xa.it.util.HttpRequest;
import com.digitalchina.xa.it.util.TConfigUtils;

@Controller
@RequestMapping(value = "/paidLottery")
public class PaidLotteryController {
	
	@Autowired
	private TPaidlotteryService tPaidlotteryService;
	@Autowired
	private EthAccountService ethAccountService;
	
	@ResponseBody
	@PostMapping("/insertLotteryInfo")
	public Map<String, Object> insertLotteryInfo(
	        @RequestParam(name = "param", required = true) String jsonValue){
		return null;
	}
	@Transactional
	@ResponseBody
	@GetMapping("/selectLotteryInfo")
	public Map<String, Object> selectLotteryInfo(
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
		TPaidlotteryInfoDomain tpid = tPaidlotteryService.selectLotteryInfoById(lotteryId);
		if(tpid.getNowSumAmount() >= tpid.getWinSumAmount()) {
			modelMap.put("data", "LotteryOver");
			return modelMap;
		}
		modelMap.put("data", "success");
		return modelMap;
	}
	
	@Transactional
	@ResponseBody
	@GetMapping("/insertLotteryDetails")
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
		BigInteger turnBalance = BigInteger.valueOf( Long.valueOf(jsonObj.getString("unitPrice")) * 10000000000000000L);
		
		//TODO 余额判断
		try {
			Web3j web3j =Web3j.build(new HttpService(TConfigUtils.selectIp()));
			BigInteger balance = web3j.ethGetBalance(ethAccountService.selectDefaultEthAccount(itcode).getAccount(),DefaultBlockParameterName.LATEST).send().getBalance().divide(BigInteger.valueOf(10000000000000000L));
			if(Double.valueOf(jsonObj.getString("unitPrice")) > Double.valueOf(balance.toString())-0.25) {
				modelMap.put("data", "balanceNotEnough");
				return modelMap;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("查询余额失败");
		}
		
		//判断是否达到所需金额
		synchronized(this){
			TPaidlotteryInfoDomain tpid = tPaidlotteryService.selectLotteryInfoById(lotteryId);
			if(tpid.getNowSumAmount() >= tpid.getWinSumAmount()) {
				modelMap.put("data", "LotteryOver");
				return modelMap;
			}
			//直接更新Info表nowSumAmount、backup4（待确认交易笔数）
			tPaidlotteryService.updateNowSumAmountAndBackup4(lotteryId);
		}
		
		//向t_paidlottery_details表中插入信息， 参数为lotteryId, itcode, result(0), buyTime
		TPaidlotteryDetailsDomain tpdd = new TPaidlotteryDetailsDomain(lotteryId, itcode, "", "", "", 0, "", "", new Timestamp(new Date().getTime()));
		int transactionId = tPaidlotteryService.insertLotteryBaseInfo(tpdd);
		System.out.println("transactionId" + transactionId);
		
		//向kafka发送请求，参数为itcode, transactionId,  金额？， lotteryId？; 产生hashcode，更新account字段，并返回hashcode与transactionId。
		String url = TConfigUtils.selectValueByKey("kafka_address_test") + "/lottery/buyTicket";
//		String url = TConfigUtils.selectValueByKey("kafka_address") + "/lottery/buyTicket";
		System.err.println(url);
		String postParam = "itcode=" + itcode + "&turnBalance=" + turnBalance.toString() + "&transactionDetailId=" + transactionId;
		HttpRequest.sendPost(url, postParam);
		//kafka那边更新account和hashcode
		//定时任务，查询到
		
		modelMap.put("data", "success");
		return modelMap;
	}
	
	@ResponseBody
	@PostMapping("/getResult")
	public Map<String, Object> kafkaUpdateDetails(
			@RequestParam(name = "param", required = true) String jsonValue){
		return null;
	}
	
	@Transactional
	@ResponseBody
	@GetMapping("/lotteryInfo/getOne")
	public Map<String, Object> selectLotteryInfoById(
			@RequestParam(name = "param", required = true) String jsonValue){
		Map<String, Object> modelMap = DecryptAndDecodeUtils.decryptAndDecode(jsonValue);
		if(!(boolean) modelMap.get("success")){
			return modelMap;
		}
		JSONObject jsonObj = JSONObject.parseObject((String) modelMap.get("data"));
		String itcode = jsonObj.getString("itcode");
		int id = Integer.valueOf(jsonObj.getString("id"));
		
		TPaidlotteryInfoDomain tpid = tPaidlotteryService.selectLotteryInfoById(id);
		List<TPaidlotteryDetailsDomain> tpddList = tPaidlotteryService.selectLotteryDetailsByItcodeAndLotteryId(itcode, id);
		
		for(TPaidlotteryDetailsDomain tpldd : tpddList){
	        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			tpldd.setBackup2(sdf.format(tpldd.getBuyTime()));
		}
		modelMap.put("infoData", JSONObject.toJSON(tpid));
		modelMap.put("detailData", JSONObject.toJSON(tpddList));
		return modelMap;
	}
	
	@ResponseBody
	@GetMapping("/lotteryInfo/getData")
	public Map<String, Object> getData(
			@RequestParam(name = "param", required = true) String jsonValue){
		Map<String, Object> modelMap = DecryptAndDecodeUtils.decryptAndDecode(jsonValue);
		if(!(boolean) modelMap.get("success")){
			return modelMap;
		}
		JSONObject jsonObj = JSONObject.parseObject((String) modelMap.get("data"));
//		String itcode = jsonObj.getString("itcode");
//		int id = Integer.valueOf(jsonObj.getString("id"));
		TPaidlotteryInfoDomain smbTpid = tPaidlotteryService.selectOneSmbTpid();			
		List<TPaidlotteryInfoDomain> hbTpidList = tPaidlotteryService.selectHbTpids();	
		List<TPaidlotteryInfoDomain> otherTpidList = tPaidlotteryService.selectOtherTpids();
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		smbTpid.setBackup1(sdf.format(smbTpid.getLotteryTime()));
		for(TPaidlotteryInfoDomain tpid : hbTpidList){
	        tpid.setBackup1(sdf.format(tpid.getLotteryTime()));
		}
		for(TPaidlotteryInfoDomain tpid : otherTpidList){
	        tpid.setBackup1(sdf.format(tpid.getLotteryTime()));
		}
		
		modelMap.put("smb", JSONObject.toJSON(smbTpid));
		modelMap.put("hongbao", JSONObject.toJSON(hbTpidList));
		modelMap.put("other", JSONObject.toJSON(otherTpidList));
		
		return modelMap;
	}
	
	@ResponseBody
	@GetMapping("/lotteryInfo/unfinished")
	public Map<String, Object> selectLotteryInfoUnfinished(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<TPaidlotteryInfoDomain> tpidList = tPaidlotteryService.selectLotteryInfoByFlag(0);
		modelMap.put("success", true);
		modelMap.put("data", JSONObject.toJSON(tpidList));
		return modelMap;
	}
	
	@ResponseBody
	@GetMapping("/lotteryInfo/finished")
	public Map<String, Object> selectLotteryInfofinished(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<TPaidlotteryInfoDomain> tpidList = tPaidlotteryService.selectLotteryInfoByFlag(1);
		modelMap.put("success", true);
		modelMap.put("data", JSONObject.toJSON(tpidList));
		return modelMap;
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
		//tPaidlotteryService.selectLotteryDetailsByItcode(itcode);
	}
	
	@ResponseBody
	@GetMapping("/lotteryDetail/{lotteryInfoId}")
	public void selectlotteryDetailByLotteryInfoId(
			@RequestParam(name = "param", required = true) String jsonValue){
		/*
		 * 某抽奖参与详情。
		 */
		//tPaidlotteryService.selectLotteryDetailsByLotteryId(lotteryId);
	}
}
