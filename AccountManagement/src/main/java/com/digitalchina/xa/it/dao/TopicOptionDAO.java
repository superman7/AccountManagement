package com.digitalchina.xa.it.dao;

import java.util.List;

import com.digitalchina.xa.it.model.TopicOptionDomain;

public interface TopicOptionDAO {
	int insert(TopicOptionDomain record);

    List<TopicOptionDomain> selectTopicOptionByTopicID(int topicid);
    
    void updatePopularity(int id);
}
