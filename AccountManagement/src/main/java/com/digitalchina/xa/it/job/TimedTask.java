package com.digitalchina.xa.it.job;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.digitalchina.xa.it.dao.WalletAccountDAO;

@Component
public class TimedTask {
	@Autowired
    private WalletAccountDAO walletAccountDAO;
	
	@Transactional
	@Scheduled(cron="55 59 23 * * ?")
	public void updateVoteTopic(){
//		System.out.println("执行定时任务");
//		List<TopicDomain> topicList = topicDAO.selectTopicToday();
//		if(topicList.size() == 0){
//			return;
//		}
//		TopicDomain topic = topicList.get(0);
//		System.out.println("topicid为：" + topic.getId());
//		topicDAO.updateAvailableBefore(topic.getId());
//		int nextTopicId = topicDAO.selectNextTopic();
//		topicDAO.updateAvailable(nextTopicId);
	}
	
	//每天8点30分30秒执行前一天考勤奖励
	@Transactional
	@Scheduled(cron="30 30 08 * * ?")
//	@Scheduled(cron="50 38 17 * * ?")
	public void sendAttendanceRewards(){
		System.err.println("开始考勤奖励员工编号获取...");
		String result = "";
		List<String> resultList = walletAccountDAO.selectUserNoAfter21();
		resultList.addAll(walletAccountDAO.selectUserNoBefore8());
		
		for (String string : resultList) {
			result += (string + ",");
		}
		result = result.substring(0, result.length() - 1 );
		//FIXME 发送get请求，http://http//10.0.5.217:8080/eth/attendanceReward/result
	}
}
