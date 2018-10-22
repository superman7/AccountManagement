package com.digitalchina.xa.it.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TopicDomain;
import com.github.pagehelper.PageInfo;

public interface TopicService {

	int addTopic(TopicDomain topic);

    PageInfo<TopicDomain> findAllTopic(int pageNum, int pageSize);
	
    void updatePopularity(int id);
    
    List<TopicDomain> findTopicByID(int id);
    
    List<TopicDomain> selectTopicToday();

	void updateTopicStatus(@Param(value="name") String topicName);
}
