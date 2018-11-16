package com.digitalchina.xa.it.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.utils.Numeric;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.contract.Transfer;
import com.digitalchina.xa.it.dao.KeywordToAccountDAO;
import com.digitalchina.xa.it.model.EthAccountDomain;
import com.digitalchina.xa.it.model.KeywordToAccountDomain;
import com.digitalchina.xa.it.model.WalletTransactionDomain;
import com.digitalchina.xa.it.service.EthAccountService;
import com.digitalchina.xa.it.service.MnemonicService;
import com.digitalchina.xa.it.service.WalletTransactionService;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;
import com.digitalchina.xa.it.util.CreatAddressUtils;

import scala.util.Random;


@Controller
@RequestMapping(value = "/ethAccount")
public class EthAccountController {
	@Autowired
	private EthAccountService ethAccountService;
	@Autowired
	private MnemonicService mnemonicService;
	@Autowired
	private WalletTransactionService walletTransactionService;
	
	@Autowired
	private KeywordToAccountDAO keywordToAccountDAO;
	
	private static String[] ip = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};

	private static String keystoreName = "keystore.json";
	private static final BigInteger tax = BigInteger.valueOf(5000000000000000L);
	private static String address = "0x024a3c0d945739237eedf78c80c6ae5daf22c010";
//	private static String tempFilePath = "C://temp/";
	private static String tempFilePath = "/eth/javaServer/wallet/temp/";
	
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
	
	/** @api {get} /ethAccount/balanceQuery/:account 查询输入充值账户的余额
	* @apiVersion 0.1.0 
	* @apiGroup Wallet 
	* @apiParam {String} account 账户地址,格式为"yyyy-MM-dd". @apiParam {String} accountkey 账户,神州区块链账户地址.
	* @apiSuccess {String} do/did do,该账户今日未签到;did,该账户今日已签到.
	* @apiSuccessExample Success-Response: HTTP/1.1 200 OK
	* do */
	
//	查询输入充值账户的余额
	@ResponseBody
	@GetMapping("/balanceQuery")
	public Map<String, Object> balanceQuery(
			@RequestParam(name = "param", required = true) String jsonValue) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Web3j web3j = Web3j.build(new HttpService(ip[new Random().nextInt(5)]));
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
    	JSONObject accountJson = JSONObject.parseObject(data);
		String account = accountJson.getString("account");
		
		try {
			BigInteger balance = web3j.ethGetBalance(account,DefaultBlockParameterName.LATEST).send().getBalance();
			modelMap.put("success", true);
			modelMap.put("balance", Double.parseDouble(balance.toString()));
			web3j.shutdown();
		} catch (IOException e) {
			modelMap.put("success", false);
			System.out.println("查询余额失败");
		}
		
		return modelMap;
	}
	
//	输入账户充值请求，提交账户地址（FROM），密码，私钥，钱包地址（TO）
	@ResponseBody
	@GetMapping("/chargeFromInput")
	public Map<String, Object> chargeFromInput(
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
		String defaultAcc = chargeJson.getString("defaultAcc");
		String itcode = chargeJson.getString("itcode");
		String keystore = chargeJson.getString("keystore");
		
		try {
			List<Web3j> web3jList = new ArrayList<>();
			for(int i = 0; i < ip.length; i++) {
				web3jList.add(Web3j.build(new HttpService(ip[i])));
			}
			File keystoreFile = keystoreToFile(keystore, account + ".json");
			System.out.println("开始解锁。。。");
			Credentials credentials = WalletUtils.loadCredentials(password, keystoreFile);
			System.out.println("解锁成功。。。");
			keystoreFile.delete();
			System.out.println("删除临时keystore文件成功。。。");
			
			BigInteger accountBalance = web3jList.get(new Random().nextInt(5)).ethGetBalance(account,DefaultBlockParameterName.LATEST).send().getBalance();
			accountBalance = accountBalance.subtract(tax);
			Double money = Double.parseDouble(accountBalance.toString());
			if(money < 1000000000000000L) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "balanceNotEnough");
				return modelMap;
			}
			
			EthGetTransactionCount ethGetTransactionCount = web3jList.get(new Random().nextInt(5)).ethGetTransactionCount(account, DefaultBlockParameterName.LATEST).sendAsync().get();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			System.err.println("nonce:" + nonce);
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, BigInteger.valueOf(2200000000L), BigInteger.valueOf(2100000L), defaultAcc, accountBalance);
			//签名Transaction，这里要对交易做签名
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			System.err.println("hexValue:" + hexValue);
			//发送交易
			String transactionHash = "";
			String realTransactionHash = "";
			for(int i = 0; i < web3jList.size(); i++) {
				transactionHash = web3jList.get(i).ethSendRawTransaction(hexValue).sendAsync().get().getTransactionHash();
				if(transactionHash != null) {
					realTransactionHash = transactionHash;
					System.out.println(transactionHash);
				}
			}
			
			WalletTransactionDomain wtd = new WalletTransactionDomain();
			wtd.setItcode(itcode);
			wtd.setAccountFrom(account);
			wtd.setAccountTo(defaultAcc);
			wtd.setAliasFrom("输入账户");
			wtd.setAliasTo("默认账户");
			wtd.setBalance(money);
			wtd.setTransactionHash(realTransactionHash);
			wtd.setConfirmTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			walletTransactionService.insertBaseInfo(wtd);
			
			modelMap.put("success", true);
			modelMap.put("transactionHash", transactionHash);
		} catch (Exception e) {
			System.out.println("解锁失败。。。");
			if(e.getMessage().contains("Invalid password provided")) {
				System.out.println("密码错误");
				modelMap.put("success", false);
				modelMap.put("errMsg", "invalidPassword");
				return modelMap;
			}
		}
    	
		return modelMap;
	}
	
//	获取keystore
	@ResponseBody
	@GetMapping("/getKeystore")
	public Map<String, Object> getKeystore(
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
    	JSONObject accountJson = JSONObject.parseObject(data);
		String account = accountJson.getString("account");
		
		EthAccountDomain ethAccountDomain = new EthAccountDomain();
		ethAccountDomain.setAccount(account);
		String keystore = ethAccountService.selectKeystoreByAccount(ethAccountDomain);
		
		System.out.println(keystore);
		
		modelMap.put("success", true);
		modelMap.put("keystore", keystore);
		
		return modelMap;
	}
	
//	确认提现请求，提交账户地址（TO），金额，钱包地址（FROM）
	@ResponseBody
	@GetMapping("/withdrawConfirm")
	public Map<String, Object> withdrawConfirm(
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
    	
    	JSONObject withdrawJson = JSONObject.parseObject(data);
		String account = withdrawJson.getString("account");
		String defaultAcc = withdrawJson.getString("defaultAcc");
		String itcode = withdrawJson.getString("itcode");
		String alias = withdrawJson.getString("alias");
		Double money = (Double.parseDouble(withdrawJson.getString("money")))*10000000000000000L;
		
		BigDecimal moneyBigDecimal = new BigDecimal(money);// 转账金额
		EthAccountDomain ead = new EthAccountDomain();
		ead.setAccount(defaultAcc);
		String keystore = ethAccountService.selectKeystoreByAccount(ead);
		System.out.println(keystore);
		try {
			List<Web3j> web3jList = new ArrayList<>();
			for(int i = 0; i < ip.length; i++) {
				web3jList.add(Web3j.build(new HttpService(ip[i])));
			}
			File keystoreFile = keystoreToFile(keystore, defaultAcc + ".json");//
			Credentials credentials = WalletUtils.loadCredentials("mini0823", keystoreFile);
			System.out.println("解锁成功。。。");
			keystoreFile.delete();
			System.out.println("删除临时keystore文件成功。。。");
			
			EthGetTransactionCount ethGetTransactionCount = web3jList.get(new Random().nextInt(5)).ethGetTransactionCount(defaultAcc, DefaultBlockParameterName.LATEST).sendAsync().get();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			System.err.println("nonce:" + nonce);
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, BigInteger.valueOf(2200000000L), BigInteger.valueOf(2100000L), account, moneyBigDecimal.toBigInteger());
			//签名Transaction，这里要对交易做签名
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			System.err.println("hexValue:" + hexValue);
			//发送交易
			String transactionHash = "";
			String realTransactionHash = "";
			for(int i = 0; i < web3jList.size(); i++) {
				transactionHash = web3jList.get(i).ethSendRawTransaction(hexValue).sendAsync().get().getTransactionHash();
				if(transactionHash != null) {
					realTransactionHash = transactionHash;
				}
			}
			
			WalletTransactionDomain wtd = new WalletTransactionDomain();
			wtd.setItcode(itcode);
			wtd.setAccountFrom(defaultAcc);
			wtd.setAccountTo(account);
			wtd.setAliasFrom("默认账户");
			wtd.setAliasTo(alias);
			wtd.setBalance(money);
			wtd.setTransactionHash(realTransactionHash);
			wtd.setConfirmTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			walletTransactionService.insertBaseInfo(wtd);
			
			modelMap.put("success", true);
			modelMap.put("transactionHash", transactionHash);
		} catch (Exception e) {
			System.out.println("解锁失败。。。");
			e.printStackTrace();
			if(e.getMessage().contains("Invalid password provided")) {
				modelMap.put("success", false);
				modelMap.put("errMsg", "invalidPassword");
				return modelMap;
			}
		}
    	
		return modelMap;
	}
	
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
		String defaultAcc = chargeJson.getString("defaultAcc");
		String itcode = chargeJson.getString("itcode");
		String alias = chargeJson.getString("alias");
		
		Double money = (Double.parseDouble(chargeJson.getString("money")))*10000000000000000L;
		BigDecimal moneyBigDecimal = new BigDecimal(money);// 转账金额
		EthAccountDomain ethAccountDomain = new EthAccountDomain();
		ethAccountDomain.setAccount(account);
		String keystore = ethAccountService.selectKeystoreByAccount(ethAccountDomain);
		System.out.println(keystore);
		try {
			List<Web3j> web3jList = new ArrayList<>();
			for(int i = 0; i < ip.length; i++) {
				web3jList.add(Web3j.build(new HttpService(ip[i])));
			}
			File keystoreFile = keystoreToFile(keystore, account + ".json");
			System.out.println("开始解锁。。。");
			Credentials credentials = WalletUtils.loadCredentials(password, keystoreFile);
			System.out.println("解锁成功。。。");
			keystoreFile.delete();
			System.out.println("删除临时keystore文件成功。。。");
			
			EthGetTransactionCount ethGetTransactionCount = web3jList.get(new Random().nextInt(5)).ethGetTransactionCount(account, DefaultBlockParameterName.LATEST).sendAsync().get();
			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
			System.err.println("nonce:" + nonce);
			RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, BigInteger.valueOf(2200000000L), BigInteger.valueOf(2100000L), defaultAcc, moneyBigDecimal.toBigInteger());
			//签名Transaction，这里要对交易做签名
			byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
			String hexValue = Numeric.toHexString(signedMessage);
			System.err.println("hexValue:" + hexValue);
			//发送交易
			String transactionHash = "";
			String realTransactionHash = "";
			for(int i = 0; i < web3jList.size(); i++) {
				transactionHash = web3jList.get(i).ethSendRawTransaction(hexValue).sendAsync().get().getTransactionHash();
				if(transactionHash != null) {
					realTransactionHash = transactionHash;
				}
			}
			
			WalletTransactionDomain wtd = new WalletTransactionDomain();
			wtd.setItcode(itcode);
			wtd.setAccountFrom(account);
			wtd.setAccountTo(defaultAcc);
			wtd.setAliasFrom(alias);
			wtd.setAliasTo("默认账户");
			wtd.setBalance(money);
			wtd.setTransactionHash(realTransactionHash);
			wtd.setConfirmTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			walletTransactionService.insertBaseInfo(wtd);
			
			modelMap.put("success", true);
			modelMap.put("transactionHash", transactionHash);
		} catch (Exception e) {
			System.out.println("解锁失败。。。");
			e.printStackTrace();
			if(e.getMessage().contains("Invalid password provided")) {
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
	//FIXME
	@ResponseBody
	@GetMapping("/newNounAccount")
	public String newNounAccount(@RequestParam(name = "param", required = true) String param) {
		String[] mnemonicList = param.split(",");
		for(String temp : mnemonicList){
			String account = CreatAddressUtils.creatAddressUtils(temp);
			keywordToAccountDAO.insertKeyword(new KeywordToAccountDomain(temp, account));
		}
		return "success";
	}
	
//	检测地址名是否重复
	@ResponseBody
	@GetMapping("/checkUp")
	public Map<String, Object> checkUp(@RequestParam(name = "param", required = true) String jsonValue) {
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
	
	private File keystoreToFile(String keystore, String keystoreName) throws IOException {
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
