package com.digitalchina.xa.it.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.xa.it.service.SigninRewardService;

@Controller
@RequestMapping(value = "/signinReward")
public class SigninRewardController {
	@Autowired
	private SigninRewardService srService;
	
	@ResponseBody
	@GetMapping("/saveRandom")
	public Object saveSigninStatusRandom(
			@RequestParam(name = "itcode", required = true) String itcode){
		return srService.saveSigninInfo(itcode);
	}
	@ResponseBody
	@GetMapping("/saveConstant")
	public Object saveSigninStatusConstant(
			@RequestParam(name = "itcode", required = true) String itcode){
		return srService.saveSigninInfoConstant(itcode);
	}
	@ResponseBody
	@GetMapping("/signinStatus")
	public Object signinStatus(
			@RequestParam(name = "itcode", required = true) String itcode){
		return srService.checkSigninStatus(itcode);
	}
	
	@ResponseBody
	@GetMapping("/addLuckyNumber")
	public Map<String, Object> addLuckyNumber(
			@RequestParam(name = "param", required = true) String param){
		return srService.addLuckyNumber(param);
	}
	
//	@ResponseBody
//	@GetMapping("/all")
//	public Object findAllVms(
//	        @RequestParam(name = "pageNum", required = false, defaultValue = "1")
//	                int pageNum,
//	        @RequestParam(name = "pageSize", required = false, defaultValue = "10")
//	                int pageSize){
//	    return virtualMachineService.findAllVms(pageNum,pageSize);
//	}
//	
//	@ResponseBody
//	@GetMapping("/myVms")
//	public Object findMyVms(
//			@RequestParam(name = "itcode", required = true)
//				String itcode){
//	    return virtualMachineService.findMyVms(itcode);
//	}
//	
//	@ResponseBody
//	@GetMapping("/apply")
//	public Object applyVm(
//	        @RequestParam(name = "machineid", required = true)
//	            Integer id,
//	        @RequestParam(name = "itcode", required = true)
//	        	String itcode){
//		System.out.println(itcode + "申请虚拟机" + itcode);
//	    return virtualMachineService.applyVm(id, itcode);
//	}
}
