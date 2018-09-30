package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.PaidVoteTopicDAO;
import com.digitalchina.xa.it.model.PaidVoteTopicDomain;
import com.digitalchina.xa.it.service.PaidVoteTopicService;

@Service(value = "paidVoteTopicService")
public class PaidVoteTopicServiceImpl implements PaidVoteTopicService{
    @Autowired
    private PaidVoteTopicDAO paidVoteTopicDAO;

	@Override
	public int insertPaidVoteTopic(String topicName, Integer available) {
		return paidVoteTopicDAO.insertPaidVoteTopic(topicName, available);
	}

	@Override
	public List<PaidVoteTopicDomain> selectPaidVoteTopic() {
		return paidVoteTopicDAO.selectPaidVoteTopic();
	}

	@Override
	public void updateAvailable(Integer id) {
		paidVoteTopicDAO.updateAvailable(id);
	}
}
