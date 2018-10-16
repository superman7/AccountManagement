package com.digitalchina.xa.it.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.LessonBuyDomain;
import com.digitalchina.xa.it.model.LessonDetailDomain;
import com.digitalchina.xa.it.service.EthAccountService;
import com.digitalchina.xa.it.service.LessonBuyService;
import com.digitalchina.xa.it.service.LessonContractService;
import com.digitalchina.xa.it.service.LessonDetailService;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;

@Controller
@RequestMapping(value = "/lessonBuy")
public class LessonBuyController {
	@Autowired
	private LessonBuyService lessonBuyService;
	@Autowired
	private LessonContractService lessonContractService;
	@Autowired
	private EthAccountService ethAccountService;
	
	@ResponseBody
	@GetMapping("/insertBuyInfo")
	public Map<String, Object> updateChapter(
	        @RequestParam(name = "param", required = true) String jsonValue){
		Map<String, Object> modelMap = new HashMap<String, Object>();
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
    	JSONObject jsonObj = JSONObject.parseObject(data);
		String itcode = jsonObj.getString("itcode");
		String chapterNum = jsonObj.getString("chapterNum");
		String lessonId = jsonObj.getString("lessonId");
		
		LessonBuyDomain lbd = lessonBuyService.selectCostAndDiscount(chapterNum, lessonId);
		Double cost = lbd.getCost();
		Double discount = lbd.getDiscount();
		
		Web3j web3j = lessonContractService.build();
		/*String keystore = "{\"address\":\"8a950e851344715a51036567ca1b44aab3f15110\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"9e3577ad90bb39f8ba61d8d46b4ce00ad27c386c3a23106d71c37834ebba8417\",\"cipherparams\":{\"iv\":\"9ec6b6059588cbbac7be3b150644159b\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"43252a8cfcc59d0d63089de5b21a53e3681dc366e22d6fc6f85ae6040e03c971\"},\"mac\":\"2c9b998de00e7b10121a5715110659f92bb4676633676655a2ab6603ec2d3796\"},\"id\":\"67fa3f2a-dec5-4fef-ac43-d722c435b039\",\"version\":3}";
		*/
		String keystore = ethAccountService.selectKeystoreByItcode(itcode);
		try {
			Credentials credentials = lessonContractService.loadCredentials(keystore, "/eth/temp/" + itcode + ".json", "mini0823");
			System.out.println("*********生成凭证*********");
			lessonContractService.buyChapter(credentials, lessonId, BigInteger.valueOf((long) (cost*discount/10)*10000000000000000L));
			System.out.println("*********调用购买合约**********");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CipherException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LessonBuyDomain lb = new LessonBuyDomain();
		lb.setLessonId(Integer.valueOf(lessonId));
		lb.setChapterNum(Integer.valueOf(chapterNum));
		lb.setItcode(itcode);
		lb.setCost(cost);
		lb.setDiscount(discount);
		lb.setBuyTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		lb.setType(1);
		lessonBuyService.insertBuyInfo(lb);
		System.out.println("*******记录购买信息********");
		modelMap.put("success", true);		
		return modelMap;
	}
}
