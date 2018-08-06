package com.digitalchina.xa.it.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TimedTask {

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
}
