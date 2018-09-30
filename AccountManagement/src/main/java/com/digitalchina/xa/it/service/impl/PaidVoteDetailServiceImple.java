package com.digitalchina.xa.it.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import com.digitalchina.xa.it.dao.PaidVoteDetailDAO;
import com.digitalchina.xa.it.model.PaidVoteDetailDomain;
import com.digitalchina.xa.it.service.PaidVoteDetailService;

@Service(value = "paidVoteDetailService")
public class PaidVoteDetailServiceImple implements PaidVoteDetailService{
	private volatile static Web3j web3j;
    private static String address = "0x941b8f05561e57f5b9d366c168b85baf900b037c";
    private static String[] ipArr = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};

    @Autowired
    private PaidVoteDetailDAO paidVoteDetailDAO;

	@Override
	public String voteToSomebody(String toaccount, String fromaccount, String toitcode, String fromitcode,
			String turncount, String remark, Integer topicId) {
		PaidVoteDetailDomain paidVoteDetailDomain = new PaidVoteDetailDomain();
		//投票主题
		paidVoteDetailDomain.setTopicId(topicId);
		//被投票人的相关信息
		paidVoteDetailDomain.setBeVotedAddress(toaccount);
		paidVoteDetailDomain.setBeVotedItcode(toitcode);
		//投票张数
		paidVoteDetailDomain.setNumberOfVotes(Integer.valueOf(turncount));
		//投票人
		paidVoteDetailDomain.setVoteAddress(fromaccount);
		
		paidVoteDetailDAO.insertPaidVoteDetail(paidVoteDetailDomain);
		
		System.err.println(paidVoteDetailDomain.getId());
		
		//FIXME 这里添加扣费逻辑
		
		return null;
	}

	@Override
	public List<Map<String, Object>> selectTop10(Integer topicId) {
		return paidVoteDetailDAO.selectTop5(topicId);
	}
}
