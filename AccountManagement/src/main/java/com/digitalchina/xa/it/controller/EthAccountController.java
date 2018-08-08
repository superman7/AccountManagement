package com.digitalchina.xa.it.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
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
import org.web3j.crypto.CipherException;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.EthAccountDomain;
import com.digitalchina.xa.it.service.EthAccountService;
import com.digitalchina.xa.it.service.MnemonicService;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;


@Controller
@RequestMapping(value = "/ethAccount")
public class EthAccountController {
	@Autowired
	private EthAccountService ethAccountService;
	@Autowired
	private MnemonicService mnemonicService;
	
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
	
//	重选密语请求，返回新生成的密语
	@ResponseBody
	@GetMapping("/refreshMnemonic")
	public Map<String, Object> refreshMnemonic() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String mnemonicSentence = mnemonicService.chooseMnemonic();
		modelMap.put("success", true);
		modelMap.put("mnemonic", mnemonicSentence);
		
		return modelMap;
	}
	
//	创建地址请求
	@ResponseBody
	@GetMapping("/newAddress")
	public Map<String, Object> newAddress(
            @RequestParam(name = "param", required = true) String jsonValue) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		System.out.println(jsonValue);
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
    	System.err.println("解密的助记词，密码及itcode的JSON为:" + data);
		//获取前端发送的数据，包括密语，密语密码和itcode
		JSONObject mnemonicJson = JSONObject.parseObject(data);
		String mnemonic = mnemonicJson.getString("mnemonic");
		String mnePassword = mnemonicJson.getString("mnePassword");
		String itcode = mnemonicJson.getString("itcode");
		//生成ECKeyPair，再得到账户地址
		ECKeyPair ecKeyPair= getECKeyPair(mnemonic, mnePassword);
		String address = Keys.getAddress(ecKeyPair);
		
		//查询数据库中该用户拥有的账户个数，如果超过10个，返回false
		List<EthAccountDomain> accountList = ethAccountService.selectEthAccountByItcode(itcode);
		if(accountList.size() >= 10) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "overnumber");
		} else {
			//小于10个，将新生成的账户地址和itcode插入数据库，并向前端返回地址，密语和密语密码
			EthAccountDomain ethAccountDomain = new EthAccountDomain();
			ethAccountDomain.setItcode(itcode);
			ethAccountDomain.setAccount(address);
			ethAccountService.insertEthAccount(ethAccountDomain);
			
			modelMap.put("success", true);
			modelMap.put("address", address);
			modelMap.put("mnemonic", mnemonic);
			modelMap.put("mnePassword", mnePassword);
		}
		return modelMap;
	}
	
//	创建账户请求
	@ResponseBody
	@GetMapping("/newAccount")
	public Map<String, Object> newAccount(String allInfoSentence) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//获取前端发送的密语，密语密码，地址名和交易密码
		JSONObject allInfoSentenceJson = JSONObject.parseObject(allInfoSentence);
		String mnemonic = allInfoSentenceJson.getString("mnemonic");
		String mnePassword = allInfoSentenceJson.getString("mnePassword");
		String alias = allInfoSentenceJson.getString("alias");
		String traPassword = allInfoSentenceJson.getString("traPassword");
		ECKeyPair ecKeyPair = getECKeyPair(mnemonic, mnePassword);
		String address = Keys.getAddress(ecKeyPair);
		
		//生成WalletFile(keystore)，更新数据库，根据address存入keystore和alias
		try {
			WalletFile walletFile = Wallet.createLight(traPassword, ecKeyPair);
			String keystore = ((JSONObject) JSONObject.toJSON(walletFile)).toJSONString();
			ethAccountService.updateKeystoreAndAlias(keystore, alias, address);
		} catch (CipherException e) {
			e.printStackTrace();
		}
		
		modelMap.put("success", true);
		
		return modelMap;
	}
	
//	检测地址名是否重复
	@ResponseBody
	@GetMapping("/checkUp")
	public Map<String, Object> checkUp(String aliasInfo) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		JSONObject aliasInfoJson = JSONObject.parseObject(aliasInfo);
		String itcode = aliasInfoJson.getString("itcode");
		String alias = aliasInfoJson.getString("addressName");
		
		List<EthAccountDomain> accountList = ethAccountService.selectEthAccountByItcode(itcode);
		for(int index = 0; index < accountList.size(); index++) {
			if(accountList.get(index).getAlias().equals(alias)) {
				modelMap.put("success", true);
				modelMap.put("valid", false);
				return modelMap;
			}
		}
		modelMap.put("success", true);
		modelMap.put("valid", true);
		
		return modelMap;
	}
	
	@ResponseBody
	@GetMapping("/all")
	public Object findAllUser(
	        @RequestParam(name = "pageNum", required = false, defaultValue = "1")
	                int pageNum,
	        @RequestParam(name = "pageSize", required = false, defaultValue = "10")
	                int pageSize){
	    return ethAccountService.findAllEthAccount(pageNum, pageSize);
	}
	
	private byte[] getSHA2HexValue(String str) {
        byte[] cipher_byte;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            cipher_byte = md.digest();
            return cipher_byte;
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return null;
  }
	
	private ECKeyPair getECKeyPair(String mnemonic, String mnePassword) {
		List<String> mnemonicList = mnemonicService.lockMnemonicByPwd(mnemonic, mnePassword);
		String ecKeyPairStr = mnemonicService.merkleTreeRoot(mnemonicList);
		ECKeyPair ecKeyPair= ECKeyPair.create(getSHA2HexValue(ecKeyPairStr));
		
		return ecKeyPair;
	}
}
