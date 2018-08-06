package com.digitalchina.xa.it.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Test {

	//905E32565C95D7E38BD5A1539C2726E5CB3DAB51650FA2F381F7CD14932AC229A2C1C19C105A4C836FD1DC064AEA88034724759E3EFC22900AF321383BB8CC0E203E08B17B7FEDA0B22F50EB77E5F017B512CE737D656B85FB31CD081B87699E29088220054A8B91E3CCC79F48A6E6F89FA6D621D35EE8BF
	public static void main(String[] args) {
		String jsonValue = "670CB864C73A31D42B8DB141CA94CFCE516A0B21CEFB94F4C61F3BF38DB43257B8818F100E46A4060964C2CC88D19C46EEAC16128450C269E18C1D2DABFE3477DE328299BF1A1461503B284DA41A46BC81A665DE40892B3B4868F506335CADE9B0ECB683DC548BAF35E7DD231F25A21730D3F5F4FEF8CAFCCE4BFE9460919A96922C217A38B7A770";
		System.out.println(jsonValue);
		
		Encrypt encrypt = new EncryptImpl();
    	String decrypt = null;
		try {
			decrypt = encrypt.decrypt(jsonValue);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.err.println("解密转账信息时发生的异常为:"+e1.toString());
		}
    	String data = null;
		try {
			data = URLDecoder.decode(decrypt, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("url解码转账信息时发生的异常为:"+e.toString());
		}
    	System.err.println(data);
    	
		JSONObject obj = JSON.parseObject(data);
		String topicName = obj.getString("topic");
		System.out.println("topicName:" + topicName);
		JSONArray options = (JSONArray) obj.get("options");
		
//		TopicDomain topic = new TopicDomain();
//		topic.setName(name);
//		topic.setType("选择");
//		int insertCounter = topicService.addTopic(topic);
//		int topicId = 0;
//		if(insertCounter > 0){
//			topicId = topic.getId();
//		}
		for(int i = 0; i < options.size(); i++){
			System.out.println("option" + (i + 1) + ":" + options.get(i));
		}
	}

}
