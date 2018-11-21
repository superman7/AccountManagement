package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.TopicDomain;

public interface TopicDAO {
	int insert(TopicDomain record);

    List<TopicDomain> selectTopic();

	List<TopicDomain> selectTopicByID(int id);
    
    void updatePopularity(int id);
    
    void updateReader(int id);
    
    void updatePriority(@Param("id")int id, @Param("priority")int priority);
    
    List<TopicDomain> selectTopicToday();
    
    void updateAvailable(int id);
    
    void updateAvailableBefore(int id);
    
    int selectNextTopic();

	int selectNextTopicStock();

	void updateTopicStatus(String topicName);
	
	//根据投票数排序查询话题榜
	List<TopicDomain> selectHotTopicByPopularityDesc();
	
	//根据话题提交时间排序查询话题榜
	List<TopicDomain> selectNewTopicByPopularityDesc();
	
	//根据优先级排序查询话题榜
	List<TopicDomain> selectPriorityTopicByPopularityDesc();
	
	//查询性格测试话题
	List<TopicDomain> selectPersonalityAsc();
	//查询热门话题
	List<TopicDomain> selectNewsAsc();
}
