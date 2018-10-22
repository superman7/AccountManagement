package com.digitalchina.xa.it.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.dao.SigninRewardDAO;
import com.digitalchina.xa.it.model.LuckycashDomain;
import com.digitalchina.xa.it.model.SigninRewardDomain;
import com.digitalchina.xa.it.model.UserDomain;
import com.digitalchina.xa.it.service.LuckycashService;
import com.digitalchina.xa.it.service.SigninRewardService;
import com.digitalchina.xa.it.service.UserService;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;

@Service(value = "signinRewardService")
public class SigninRewardServiceImpl implements SigninRewardService{
	@Autowired
	private UserService userService;
	
	@Autowired
	private LuckycashService luckycashService;
	
//    private volatile static Web3j web3j;
//    private static String ip = "";
//    private static final String rootPath = "/eth/datadir/keystore/";
//    private static final String address = "0x16a9de544cbf62d8b55852a66fb7e7740803d78a";
//    private static String[] ipArr = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};
    
    @Autowired
    private SigninRewardDAO srDao;

	@Override
	public Map<String, Object> addLuckyNumber(String param) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//FIXME 封装为统一的静态方法调用
		System.out.println(param);
		Encrypt encrypt = new EncryptImpl();
		String decrypt = null;
		try {
			decrypt = encrypt.decrypt(param);
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
		System.err.println("红包详情的JSON为:" + data);
		
		//获取前端发送的密语，密语密码，地址名和交易密码
		JSONObject allInfoSentenceJson = JSONObject.parseObject(data);
		String luckyNum = allInfoSentenceJson.getString("luckyNum");
		String luckyGuys = allInfoSentenceJson.getString("luckyGuys");
		String luckyGuysCount = allInfoSentenceJson.getString("luckyGuysCount");
		
		LuckycashDomain luckycashDomain = new LuckycashDomain();
		luckycashDomain.setAvailable(0);
		luckycashDomain.setLuckyNum(luckyNum);
		luckycashDomain.setLuckyGuys(luckyGuys);
		luckycashDomain.setLuckyGuysCount(Integer.valueOf(luckyGuysCount));
		Timestamp nousedate = new Timestamp(System.currentTimeMillis());
		luckycashDomain.setAvailableTime(nousedate);
		luckycashService.insert(luckycashDomain);
		
		modelMap.put("success", true);
		
		return modelMap;
	}

	@Override
	public int addSigninReward(SigninRewardDomain sr) {
		int result = srDao.insert(sr);
		return result;
	}

	@Override
	public List<SigninRewardDomain> selectSigninStatus(String itcode) {
		List<SigninRewardDomain> result = srDao.selectSigninStatus(itcode);
		return result;
	}

	@Override
	public List<SigninRewardDomain> selectTodaySigninStatus(String itcode) {
		List<SigninRewardDomain> result = srDao.selectTodaySigninStatus(itcode);
		return result;
	}
	@Override
	public List<SigninRewardDomain> selectTodaySigninCounter() {
		List<SigninRewardDomain> result = srDao.selectTodaySigninCounter();
		return result;
	}

	@Override
	public String saveSigninInfo(String itcode) {
		
		List<SigninRewardDomain> result = this.selectSigninStatus(itcode);
		int counter = this.selectTodaySigninCounter().size();
		String luckyNum = "none";
		
		List<LuckycashDomain> luckycashDomainList = luckycashService.selectAvailableLuckyNum();
		if(luckycashDomainList.size() > 0){
			LuckycashDomain luckycashDomain = luckycashDomainList.get(0);
			//得到下一个中奖号码
			int luckyGuysIndex = luckycashDomain.getLuckyGuysCount();
			String[] luckyNumberArray = luckycashDomain.getLuckyGuys().split(",");
			
			String luckyNumber = luckyNumberArray[luckyNumberArray.length - luckyGuysIndex];
			//计算
			if(String.valueOf(counter + 1).equals(luckyNumber)){
				luckycashService.updateLuckyGuysCount(luckycashDomain.getId());
				//如果中奖名额已用完，将红包码变为不可用
				if(luckyGuysIndex == 1){
					luckycashService.updateAvailable(luckycashDomain.getId());
				}
				//一人一包制
				luckyNum = luckycashDomain.getLuckyNum().split(",")[luckyNumberArray.length - luckyGuysIndex];
//				luckyNum = luckycashDomain.getLuckyNum();
			}
			//添加fannl和mojja作为测试，每次可以看到红包码
			if(itcode.trim().equals("fannl") || itcode.trim().equals("mojja")){
				luckyNum = luckycashDomain.getLuckyNum();
			}
		}
		
		int rewards = 5;
		if(result.size() > 0){
			//第1-6天分别为5-10个币，第7天开始增加一个1-10的随机宝箱，14天-21天宝箱大小为6-15,第21天以上宝箱大小为11-20
			int count = result.size();
			int countQuotient = count / 7;
			int countModel = count % 7;
			
			if(countQuotient == 0){
				rewards = countModel + 4;
			}else if(countQuotient == 1){
				rewards = 10 + (int)(Math.random() * 10);
			}else if(countQuotient == 2){
				rewards = 10 + (int)(Math.random() * 16);
			}else if(countQuotient >= 3){
				rewards = 10 + (int)(Math.random() * 21);
			}
		}
		
		//查到此itcode才能进行奖励
		List<UserDomain> userList = userService.findUserByItcode(itcode);
		if(userList.size() > 0){
			SigninRewardDomain sr = new SigninRewardDomain();
			sr.setItcode(itcode);
			sr.setAccountkey(userList.get(0).getAccount());
			sr.setType(0);
			Timestamp nousedate = new Timestamp(System.currentTimeMillis());
			
			sr.setSigntime(nousedate);
			sr.setRewards(Integer.valueOf(rewards));
			this.addSigninReward(sr);
			return "{\"status\":1,\"value\":" + rewards + ",\"lucky\":\"" + luckyNum + "\"}";
		}
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SigninRewardDomain sr = new SigninRewardDomain();
//		sr.setItcode("dictionary");
//		sr.setAccountkey("dictionaryKey");
//		sr.setType(1);
//      //为签到表插入字典数据，目前插入至2021-02-23
//		for (int n = 1; n < 1000; n ++){
//			Date date;
//			try {
//				date = dateFormat.parse("2018-05-31 00:00:01");
//				Calendar c = Calendar.getInstance();
//				c.setTime(date);
//				c.add(Calendar.DAY_OF_MONTH, n);
//				Timestamp nousedate = new Timestamp(c.getTimeInMillis());
//				
//				System.out.println(nousedate + "   " + n);
//				sr.setSigntime(nousedate);
//				srService.addSigninReward(sr);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
	    return "{\"status\":0,\"value\":0,\"lucky\":\"none\"}";
	}

	@Override
	public String checkSigninStatus(String itcode) {
		List<SigninRewardDomain> resultToday = this.selectTodaySigninStatus(itcode);
		boolean todayStatus = (resultToday.size() == 0);
		List<SigninRewardDomain> result = this.selectSigninStatus(itcode);
		int count = result.size();
		int counter = this.selectTodaySigninCounter().size();
		if(todayStatus){
			//今日未签到
			return "{\"status\":1,\"value\":" + count + ",\"value1\":" + counter +"}";
		}else{
			//今日已签到
			return "{\"status\":0,\"value\":" + count + ",\"value1\":" + counter +"}";
		}
	}

	@Override
	public String saveSigninInfoConstant(String itcode) {
		int rewards = 5 + (int)(Math.random() * 11);
		List<UserDomain> userList = userService.findUserByItcode(itcode);
		if(userList.size() > 0){
			SigninRewardDomain sr = new SigninRewardDomain();
			sr.setItcode(itcode);
			sr.setAccountkey(userList.get(0).getAccount());
			sr.setType(0);
			Timestamp nousedate = new Timestamp(System.currentTimeMillis());
			
			sr.setSigntime(nousedate);
			sr.setRewards(Integer.valueOf(rewards));
			this.addSigninReward(sr);
			return "{\"status\":1,\"value\":" + rewards + "}";
		}
	    return "{\"status\":0,\"value\":0}";
	}
}
