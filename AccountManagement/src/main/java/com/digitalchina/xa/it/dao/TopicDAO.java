package com.digitalchina.xa.it.dao;

import java.util.List;

import com.digitalchina.xa.it.model.TopicDomain;

public interface TopicDAO {
	int insert(TopicDomain record);

    List<TopicDomain> selectTopic();

	List<TopicDomain> selectTopicByID(int id);
    
    void updatePopularity(int id);
    
    List<TopicDomain> selectTopicToday();
    
    void updateAvailable(int id);
    
    void updateAvailableBefore(int id);
    
    int selectNextTopic();

	void updateTopicStatus(String topicName);
}
