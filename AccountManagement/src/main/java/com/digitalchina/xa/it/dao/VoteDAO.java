package com.digitalchina.xa.it.dao;

import java.util.List;

import com.digitalchina.xa.it.model.VoteDomain;

public interface VoteDAO {
	int insert(VoteDomain record);

    List<VoteDomain> selectVoteInfoByAccountToday(VoteDomain record);
}
