package com.digitalchina.xa.it.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TopicDomain;
import com.github.pagehelper.PageInfo;

public interface TopicService {

	int addTopic(TopicDomain topic);

    PageInfo<TopicDomain> findAllTopic(int pageNum, int pageSize);
	
    void updatePopularity(int id);
    
    void updateReader(int id);
    
    void updatePriority(int id, int priority);
    
    List<TopicDomain> findTopicByID(int id);
    
    List<TopicDomain> selectTopicToday();

	void updateTopicStatus(@Param(value="name") String topicName);
	//根据投票数排序查询话题榜
	PageInfo<TopicDomain> selectHotTopicByPopularityDesc(int pageNum, int pageSize);
	//根据时间排序查询话题榜
	PageInfo<TopicDomain> selectNewTopicByPopularityDesc(int pageNum, int pageSize);
	//根据优先级数排序查询话题榜
	PageInfo<TopicDomain> selectPriorityTopicByPopularityDesc(int pageNum, int pageSize);
}
