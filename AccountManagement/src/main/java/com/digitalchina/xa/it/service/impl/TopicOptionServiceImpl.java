package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalchina.xa.it.dao.TopicOptionDAO;
import com.digitalchina.xa.it.model.TopicOptionDomain;
import com.digitalchina.xa.it.service.TopicOptionService;

@Service(value = "topicOptionService")
public class TopicOptionServiceImpl implements TopicOptionService {

    @Autowired
    private TopicOptionDAO topicOptionDAO;

	@Override
	@Transactional
	public int addTopicOption(TopicOptionDomain topicOption) {
        return topicOptionDAO.insert(topicOption);
    }

	@Override
	public List<TopicOptionDomain> findTopicOptionByID(int topicid) {
		return topicOptionDAO.selectTopicOptionByTopicID(topicid);
	}

	@Override
    @Transactional
	public void updatePopularity(int id) {
		topicOptionDAO.updatePopularity(id);
	}
}
