package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.VoteDomain;

public interface VoteService {

	int addVote(VoteDomain vote);
	
    List<VoteDomain> findVoteInfoByAccountToday(VoteDomain vote);
}
