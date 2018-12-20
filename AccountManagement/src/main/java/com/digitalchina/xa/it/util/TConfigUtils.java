package com.digitalchina.xa.it.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.digitalchina.xa.it.service.TConfigService;

import scala.util.Random;

@Configuration
public class TConfigUtils {
	@Autowired
	private TConfigService tconfigService;
	
	private static TConfigService newtconfigService;
	
	@PostConstruct
	public void init() {
		newtconfigService = tconfigService;
	}
	
	public static String selectIp() {
		List<String> ipArr =  newtconfigService.selectIpArr();
		return ipArr.get(new Random().nextInt(5));
	}

	public static List<String> selectIpArr() {
		return newtconfigService.selectIpArr();
	}
	
	public static String selectValueByKey(String cfgKey) {
		return newtconfigService.selectValueByKey(cfgKey);
	}
}

