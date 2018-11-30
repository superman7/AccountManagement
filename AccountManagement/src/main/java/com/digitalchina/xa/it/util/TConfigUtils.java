package com.digitalchina.xa.it.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.digitalchina.xa.it.service.TConfigService;

import scala.util.Random;

public class TConfigUtils {
	@Autowired
	public static TConfigService tconfigService;
	
	public static String selectIp() {
		String[] ipArr = (String[]) tconfigService.selectIpArr().toArray();
		return ipArr[new Random().nextInt(5)];
	}
	
	public static String[] selectIpArr() {
		return (String[]) tconfigService.selectIpArr().toArray();
	}
	
	public static String selectValueByKey(String cfgKey) {
		return tconfigService.selectValueByKey(cfgKey);
	}
	
	public static Boolean selectIpArr(String cfgValue, Integer cfgStatus) {
		return tconfigService.UpdateEthNodesStatus(cfgValue, cfgStatus);
	}
}

