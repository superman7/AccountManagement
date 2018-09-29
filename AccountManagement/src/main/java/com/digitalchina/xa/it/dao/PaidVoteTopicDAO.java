package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.PaidVoteTopicDomain;

public interface PaidVoteTopicDAO {
	int insertPaidVoteTopic(@Param("topicName")String topicName, @Param("available")Integer available);
	
	List<PaidVoteTopicDomain> selectPaidVoteTopic();
	
	PaidVoteTopicDomain selectPaidVoteTopicById(@Param("id")Integer id);
	
	void updateAvailable(@Param("id")Integer id);
}
