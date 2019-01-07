package com.digitalchina.xa.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.xa.it.model.EthAccountDomain;
import com.digitalchina.xa.it.service.EthAccountService;
import com.digitalchina.xa.it.util.CreatAddressUtils;
import com.digitalchina.xa.it.util.GetPwdAndKeyStoreUtils;

@Controller
@RequestMapping(value = "/eth")
public class UtilController {
	@Autowired
	private EthAccountService ethAccountService;
	
	@ResponseBody
	@GetMapping("/getBalance")
	public String getBalance(@RequestParam(name = "itcode", required = true) String itcode){
		EthAccountDomain ead = ethAccountService.selectDefaultEthAccount(itcode);
		String returnStr = "";
		if(ead == null) {
			String account = CreatAddressUtils.creatAddressUtils(itcode);
			EthAccountDomain ethAccountDomain = new EthAccountDomain();
			ethAccountDomain.setItcode(itcode);
			ethAccountDomain.setAccount(account);
			ethAccountService.insertItcodeAndAccount(ethAccountDomain);
			String keystore = GetPwdAndKeyStoreUtils.getDefaultPwdAndKeyStoreUtils(itcode).get("keystore");
			ethAccountService.updateKeystoreAndAlias(keystore, "默认账户", account, 4);
			return account + "_" + 10;
		}
		returnStr = ead.getAccount() + "_" + ead.getBalance();
		System.out.println(returnStr);
		return returnStr;
	} 
}
