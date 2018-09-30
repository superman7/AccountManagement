package com.digitalchina.xa.it.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.PaidVoteDetailDomain;

public interface PaidVoteDetailDAO {
	int insertPaidVoteDetail(PaidVoteDetailDomain paidVoteDetailDomain);
	
	List<PaidVoteDetailDomain> selectPaidVoteDetailByTopicId(@Param("topicId")Integer topicId);
	
	List<PaidVoteDetailDomain> selectPaidVoteDetailByVoteAddress(@Param("voteAddree")String voteAddree);
	
	List<PaidVoteDetailDomain> selectPaidVoteDetailByBeVotedAddress(@Param("beVotedAddress")String beVotedAddress);
	
	int selectTotalValueByBeVotedAddress(@Param("beVotedAddress")String beVotedAddress);
	
	void updateTransactionHash(@Param("id")Integer id, @Param("transactionHash")String transactionHash);
	
	List<String> selectUnfinishedTransaction();

	List<Map<String, Object>> selectTop5(Integer topicId);
	
	void updateTransactionStatus(@Param("transactionHash")String transactionHash);
}
