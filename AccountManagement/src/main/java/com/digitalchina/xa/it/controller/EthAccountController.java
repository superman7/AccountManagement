package com.digitalchina.xa.it.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
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
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.contract.Transfer;
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
	private static String ip = "http://10.7.10.124:8545";
	private static String address = "0x024a3c0d945739237eedf78c80c6ae5daf22c010";
	private static String tempFilePath = "C://temp/";
	private static String keystoreName = "keystore.json";
	
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
	
//	确认充值请求，提交账户地址（FROM），密码，金额，钱包地址（TO）
	@ResponseBody
	@GetMapping("/chargeConfirm")
	public Map<String, Object> chargeConfirm(
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
    	JSONObject chargeJson = JSONObject.parseObject(data);
		String account = chargeJson.getString("account");
		String password = chargeJson.getString("password");
		String money = chargeJson.getString("money");
		String defaultAcc = chargeJson.getString("defaultAcc");
		String keystore = ethAccountService.getKeystore(account);
		//充值
		try {
			Web3j web3j =Web3j.build(new HttpService(ip));
			Credentials credentials = WalletUtils.loadCredentials(password, keystoreToFile(keystore));
			System.out.println("解锁成功。。。");
			Transfer contract = Transfer.load(address, web3j, credentials, BigInteger.valueOf(2200000000L), BigInteger.valueOf(4300000L));
			contract.transferAToB(new Address(defaultAcc), BigInteger.valueOf(Long.parseLong(money))).observable().subscribe(x -> {
				System.out.println(x.getBlockHash());
				System.out.println(x.getBlockNumber());
				System.out.println(x.getCumulativeGasUsed());
				System.out.println(x.getGasUsed());
				System.out.println(x.getStatus());
				System.out.println(x.getTransactionHash());
				
				modelMap.put("success", true);
			});
		} catch (Exception e) {
			if(e.getMessage().contains("Invalid")) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "invalidPassword");
				return modelMap;
			}
		}
    	
		return modelMap;
	}
	
	
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
	
	@ResponseBody
	@GetMapping("/accountList")
	@Transactional
	public Map<String, Object> accountList(
            @RequestParam(name = "param", required = true) String jsonValue) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//FIXME 封装为统一的静态方法调用		
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
    	
		JSONObject mnemonicJson = JSONObject.parseObject(data);
		String itcode = mnemonicJson.getString("itcode");
		ethAccountService.refreshBalance(itcode);
		modelMap.put("success", true);
		List<EthAccountDomain> accountList = ethAccountService.selectEthAccountByItcode(itcode);
		modelMap.put("accountList", accountList);
		
		return modelMap;
	}
	
//	创建地址请求
	@ResponseBody
	@GetMapping("/newAddress")
	public Map<String, Object> newAddress(
            @RequestParam(name = "param", required = true) String jsonValue) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//FIXME 封装为统一的静态方法调用		
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
		String address = "0x" + Keys.getAddress(ecKeyPair);
		
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
			ethAccountService.insertItcodeAndAccount(ethAccountDomain);
			
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
	public Map<String, Object> newAccount(@RequestParam(name = "param", required = true) String jsonValue) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//FIXME 封装为统一的静态方法调用
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
    	
		//获取前端发送的密语，密语密码，地址名和交易密码
		JSONObject allInfoSentenceJson = JSONObject.parseObject(data);
		String mnemonic = allInfoSentenceJson.getString("mnemonic");
		String mnePassword = allInfoSentenceJson.getString("mnePassword");
		String alias = allInfoSentenceJson.getString("alias");
		String traPassword = allInfoSentenceJson.getString("traPassword");
		ECKeyPair ecKeyPair = getECKeyPair(mnemonic, mnePassword);
		String address = "0x" + Keys.getAddress(ecKeyPair);
		
		//生成WalletFile(keystore)，更新数据库，根据address存入keystore和alias
		try {
			WalletFile walletFile = Wallet.createLight(traPassword, ecKeyPair);
			String keystore = ((JSONObject) JSONObject.toJSON(walletFile)).toJSONString();
			System.out.println(keystore);
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
	public Map<String, Object> checkUp(@RequestParam(name = "param", required = true) String jsonValue) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//FIXME 封装为统一的静态方法调用		
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
    	
		JSONObject aliasInfoJson = JSONObject.parseObject(data);
		String itcode = aliasInfoJson.getString("itcode");
		String alias = aliasInfoJson.getString("alias");
		
		List<EthAccountDomain> accountList = ethAccountService.selectEthAccountByItcode(itcode);
		for(int index = 0; index < accountList.size(); index++) {
			if(accountList.get(index).getAlias().equals(alias)) {
				modelMap.put("valid", false);
				return modelMap;
			}
		}
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
	
	private File keystoreToFile(String keystore) throws IOException {
		File file = new File(tempFilePath + keystoreName);
        if(!file.exists()){
         file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(keystore);
        bw.close();
        System.out.println("创建keystore。。。");
        
        return file;
	}
}
