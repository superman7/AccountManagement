package com.digitalchina.xa.it.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.contract.Qiandao;
import com.digitalchina.xa.it.dao.SigninRewardDAO;
import com.digitalchina.xa.it.fanwei.service.QiandaoContractService;
import com.digitalchina.xa.it.model.LuckycashDomain;
import com.digitalchina.xa.it.model.SigninRewardDomain;
import com.digitalchina.xa.it.model.UserDomain;
import com.digitalchina.xa.it.service.LuckycashService;
import com.digitalchina.xa.it.service.SigninRewardService;
import com.digitalchina.xa.it.service.UserService;
import com.digitalchina.xa.it.util.DecryptAndDecodeUtils;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;
import com.digitalchina.xa.it.util.TConfigUtils;

@Service(value = "signinRewardService")
public class SigninRewardServiceImpl implements SigninRewardService{
	@Autowired
	private UserService userService;
	
	@Autowired
	private LuckycashService luckycashService;
	
    @Autowired
    private SigninRewardDAO srDao;

	@Override
	public Map<String, Object> addLuckyNumber(String param) {
		Map<String, Object> modelMap = DecryptAndDecodeUtils.decryptAndDecode(param);
		
		if((boolean) modelMap.get("success")){
			//获取前端发送的密语，密语密码，地址名和交易密码
			JSONObject allInfoSentenceJson = JSONObject.parseObject((String) modelMap.get("data"));
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
			
			modelMap.remove("data");
		}
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

	@Override
	public String chargeToContract(String value) {
		Web3j web3j = Web3j.build(new HttpService(TConfigUtils.selectIp()));
		long chargeValueLong;
		try {
			chargeValueLong = Long.parseLong(value);
		} catch (NumberFormatException e) {
			chargeValueLong = 1;
		}
		String walletfile = rootPath + "UTC--2018-02-05T15-28-30.373580131Z--8c735de7b8c388347b7443b492740a9c80df20a6";
		
		Credentials credentials;
		try {
			credentials = WalletUtils.loadCredentials("mini0823", walletfile);
			
			Qiandao contract = Qiandao.load(address, web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
			
			BigInteger charge = BigInteger.valueOf(10000000000000000L).multiply(BigInteger.valueOf(chargeValueLong));
			
			TransactionReceipt transactionReceipt = contract.chargeToContract(charge).send();
			
			String result = transactionReceipt.getTransactionHash();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String signinReward(String itcode, int reward) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String voteReward(String itcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void attendanceReward(String employeeNumber) {
		// TODO Auto-generated method stub
		
	}
}
