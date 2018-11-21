package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalchina.xa.it.dao.TopicDAO;
import com.digitalchina.xa.it.model.TopicDomain;
import com.digitalchina.xa.it.service.TopicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service(value = "topicService")
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicDAO topicDAO;

	@Override
	@Transactional
	public int addTopic(TopicDomain topic) {
        return topicDAO.insert(topic);
    }

	@Override
	public PageInfo<TopicDomain> findAllTopic(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<TopicDomain> topicDomains = topicDAO.selectTopic();
        PageInfo<TopicDomain> result = new PageInfo<TopicDomain>(topicDomains);
        return result;
    }

	@Override
    @Transactional
	public void updatePopularity(int id) {
		topicDAO.updatePopularity(id);
	}

	@Override
	public List<TopicDomain> findTopicByID(int id) {
		List<TopicDomain> result = topicDAO.selectTopicByID(id);
		return result;
	}

	@Override
	public List<TopicDomain> selectTopicToday() {
		return topicDAO.selectTopicToday();
	}

	@Override
	public void updateTopicStatus(String topicName) {
		topicDAO.updateTopicStatus(topicName);
	}

	@Override
    @Transactional
	public void updateReader(int id) {
		topicDAO.updateReader(id);
	}

	@Override
    @Transactional
	public void updatePriority(int id, int priority) {
		topicDAO.updatePriority(id, priority);
	}

	@Override
	public PageInfo<TopicDomain> selectHotTopicByPopularityDesc(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<TopicDomain> topicDomains = topicDAO.selectHotTopicByPopularityDesc();
        PageInfo<TopicDomain> result = new PageInfo<TopicDomain>(topicDomains);
        return result;
    }
	
	@Override
	public PageInfo<TopicDomain> selectNewTopicByPopularityDesc(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<TopicDomain> topicDomains = topicDAO.selectNewTopicByPopularityDesc();
        PageInfo<TopicDomain> result = new PageInfo<TopicDomain>(topicDomains);
        return result;
    }

	@Override
	public PageInfo<TopicDomain> selectPriorityTopicByPopularityDesc(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<TopicDomain> topicDomains = topicDAO.selectPriorityTopicByPopularityDesc();
        PageInfo<TopicDomain> result = new PageInfo<TopicDomain>(topicDomains);
        return result;
    }

	@Override
	public List<TopicDomain> selectPersonalityAsc() {
		return topicDAO.selectPersonalityAsc();
	}

	@Override
	public List<TopicDomain> selectNewsAsc() {
		return topicDAO.selectNewsAsc();
	}
}
