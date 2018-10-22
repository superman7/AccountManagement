package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.TopicOptionDomain;

public interface TopicOptionService {

	int addTopicOption(TopicOptionDomain topicOption);
	
    void updatePopularity(int id);
    
    List<TopicOptionDomain> findTopicOptionByID(int id);
}
