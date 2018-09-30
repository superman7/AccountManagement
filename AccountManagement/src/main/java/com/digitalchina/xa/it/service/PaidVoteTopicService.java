package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.PaidVoteTopicDomain;

public interface PaidVoteTopicService {
	public int insertPaidVoteTopic(String topicName, Integer available);
	
	public List<PaidVoteTopicDomain> selectPaidVoteTopic();
	
	public void updateAvailable(Integer id);
}
