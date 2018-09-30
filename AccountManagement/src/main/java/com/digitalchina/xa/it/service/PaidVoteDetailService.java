package com.digitalchina.xa.it.service;

import java.util.List;
import java.util.Map;

public interface PaidVoteDetailService {
	String voteToSomebody(String toaccount, String fromaccount, String toitcode, String fromitcode, String turncount,
			String remark, Integer topicId);
	
	List<Map<String, Object>> selectTop10(Integer topicId);
}
