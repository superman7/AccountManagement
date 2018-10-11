package com.digitalchina.xa.it.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.PaidVoteDetailDomain;

public interface PaidVoteDetailService {
	String voteToSomebody(String toaccount, String fromaccount, String toitcode, String fromitcode, String turncount,
			String remark, Integer topicId);
	
	List<Map<String, Object>> selectTop10(Integer topicId);
	
	List<PaidVoteDetailDomain> selectPaidVoteDetailByBeVotedItcode(String beVotedItcode, Integer topicId);
}
