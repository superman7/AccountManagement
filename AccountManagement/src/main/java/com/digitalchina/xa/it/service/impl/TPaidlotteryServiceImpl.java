package com.digitalchina.xa.it.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.TPaidlotteryDetailsDAO;
import com.digitalchina.xa.it.dao.TPaidlotteryInfoDAO;
import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;
import com.digitalchina.xa.it.model.TPaidlotteryInfoDomain;
import com.digitalchina.xa.it.service.TPaidlotteryService;
import com.digitalchina.xa.it.util.MerkleTrees;

@Service(value = "TPaidlotteryDetailsService")
public class TPaidlotteryServiceImpl implements TPaidlotteryService {
	@Autowired
	private TPaidlotteryDetailsDAO tPaidlotteryDetailsDAO;
	@Autowired
	private TPaidlotteryInfoDAO tPaidlotteryInfoDAO;

	@Override
	public int insertLotteryBaseInfo(TPaidlotteryDetailsDomain tPaidlotteryDetailsDomain) {
		return 0;
	}

	@Override
	public int updateHashcodeAndJudge(String hashcode, String transactionId) {
		//计算ticket值,更新该用户的ticket，hashcode值。
		String ticket = "";
		tPaidlotteryDetailsDAO.updateHashcode(hashcode, ticket, transactionId);
		
		//根据transactionId获取lotteryId，再用lotteryId查Info，联表查询
		//TPaidlotteryInfoDomain tpid = tPaidlotteryInfoDAO.selectOnelotteryBylotteryId(String lotteryId);
		
		Boolean flag = false;
		//判断开奖条件，需要typeCode...
		//TODO
		
		//不开,返回。开，根据lotteryId，更新此次参与者的result，winTicket，winReword字段,更新t_paidlottery_info表flag，lotteryTime
		if(flag == true) {
			//TODO
		} else {
			return 0;
		}
		
		return 0;
	}

	@Override
	public String generateTicket(int lotteryId, String itcode, String hashcode) {
		List<String> tempTxList = new ArrayList<String>();
		tempTxList.add(String.valueOf(lotteryId));
		tempTxList.add(itcode);
		tempTxList.add(hashcode);
		MerkleTrees merkleTrees = new MerkleTrees(tempTxList);
	    merkleTrees.merkle_tree();
	    
	    String merkleTreesRoot = merkleTrees.getRoot();
	    String ticket = "";
	    
	    for (int i = merkleTreesRoot.length(); i > 0; i = i - 2) {
	    	ticket = ticket + merkleTreesRoot.subSequence(i-1, i);        
	    }
	    
		return ticket;
	}

	@Override
	public List<String> generateWinTicket(int lotteryId, int winCount) {
		return tPaidlotteryDetailsDAO.generateWinTicket(lotteryId, winCount);
	}
}
