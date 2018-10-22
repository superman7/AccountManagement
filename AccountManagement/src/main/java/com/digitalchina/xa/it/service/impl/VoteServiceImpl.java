package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalchina.xa.it.dao.VoteDAO;
import com.digitalchina.xa.it.model.VoteDomain;
import com.digitalchina.xa.it.service.VoteService;

@Service(value = "voteService")
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteDAO voteDao;

	@Override
	@Transactional
	public int addVote(VoteDomain vote) {
		return voteDao.insert(vote);
	}

	@Override
	public List<VoteDomain> findVoteInfoByAccountToday(VoteDomain record) {
		return voteDao.selectVoteInfoByAccountToday(record);
	}
}
