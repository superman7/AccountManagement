package com.digitalchina.xa.it.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;

import com.digitalchina.xa.it.dao.EthAccountDAO;
import com.digitalchina.xa.it.model.EthAccountDomain;
import com.digitalchina.xa.it.service.EthAccountService;
import com.digitalchina.xa.it.util.CreatAddressUtils;
import com.digitalchina.xa.it.util.GetPwdAndKeyStoreUtils;
import com.digitalchina.xa.it.util.TConfigUtils;

@Controller
@RequestMapping(value = "/eth")
public class UtilController {
	@Autowired
	private EthAccountService ethAccountService;
	@Autowired
    private EthAccountDAO ethAccountDAO;
	
	@ResponseBody
	@GetMapping("/getBalance")
	public String getBalance(@RequestParam(name = "itcode", required = true) String itcode){
		Web3j web3j = Web3j.build(new HttpService(TConfigUtils.selectIp()));
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
		try {
			BigInteger balance = web3j.ethGetBalance(ead.getAccount(),DefaultBlockParameterName.LATEST).send().getBalance();
			BigDecimal balance1 = new BigDecimal(balance.divide(new BigInteger("100000000000")).toString());
			BigDecimal nbalance = balance1.divide(new BigDecimal("100000"),2,BigDecimal.ROUND_DOWN);
			returnStr = ead.getAccount() + "_" + nbalance;
			System.out.println(returnStr);
		} catch (IOException e) {
			e.printStackTrace();
			returnStr = ead.getAccount() + "_" + ead.getBalance();
		}
		return returnStr;
	} 
	
	@ResponseBody
	@GetMapping("/updateItAmount")
	public String updateItAmount(
			@RequestParam(name = "itcode", required = true) String itcode,
			@RequestParam(name = "value", required = true) String value){
		//FIXME 添加IT抵扣个人额度接口
		Web3j web3j = Web3j.build(new HttpService(TConfigUtils.selectIp()));
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
		try {
			BigInteger balance = web3j.ethGetBalance(ead.getAccount(),DefaultBlockParameterName.LATEST).send().getBalance();
			BigDecimal balance1 = new BigDecimal(balance.divide(new BigInteger("100000000000")).toString());
			BigDecimal nbalance = balance1.divide(new BigDecimal("100000"),2,BigDecimal.ROUND_DOWN);
			returnStr = ead.getAccount() + "_" + nbalance;
			System.out.println(returnStr);
		} catch (IOException e) {
			e.printStackTrace();
			returnStr = ead.getAccount() + "_" + ead.getBalance();
		}
		return returnStr;
	}
	
	@ResponseBody
	@GetMapping("/updateBalance")
	public void updateBalance(){
		Web3j web3j = Web3j.build(new HttpService(TConfigUtils.selectIp()));
		List<EthAccountDomain> ethAccountDomains = ethAccountDAO.selectEthAccountNew();
		int count = 1;
		for(EthAccountDomain ead : ethAccountDomains){
			try {
				BigInteger balance = web3j.ethGetBalance(ead.getAccount(),DefaultBlockParameterName.LATEST).send().getBalance();
				BigDecimal balance1 = new BigDecimal(balance.divide(new BigInteger("100000000000")).toString());
				BigDecimal nbalance = balance1.divide(new BigDecimal("100000"),2,BigDecimal.ROUND_DOWN);
				
				ethAccountService.updateAccountBalance(ead.getAccount(), nbalance.doubleValue());
				System.out.println("更新" + ead.getItcode() + "余额。_" + nbalance + "___" + count);
				count++;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	} 
}
