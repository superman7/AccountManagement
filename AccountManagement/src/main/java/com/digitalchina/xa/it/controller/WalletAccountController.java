package com.digitalchina.xa.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.xa.it.service.WalletAccountService;

@Controller
@RequestMapping(value = "/walletAccount")
public class WalletAccountController {
	@Autowired
	private WalletAccountService walletAccountService;
	
//	@ResponseBody
//	@GetMapping("/refreshAllUsersBalance")
//	public void refreshBalance(){
//	    userService.refreshBalance();
//	}
	
//	@ResponseBody
//	@PostMapping("/updateBalance")
//	public void updateBalance(
//	        @RequestParam(name = "itcode", required = true)
//            	String itcode,
//            @RequestParam(name = "balance", required = true)
//	        	String balance){
//	    userService.updateBalance(itcode, Double.valueOf(balance));
//	}
//	
	@ResponseBody
	@GetMapping("/all")
	public Object findAllUser(
	        @RequestParam(name = "pageNum", required = false, defaultValue = "1")
	                int pageNum,
	        @RequestParam(name = "pageSize", required = false, defaultValue = "10")
	                int pageSize){
	    return walletAccountService.findAllWalletAccount(pageNum, pageSize);
	}
}
