package com.digitalchina.xa.it.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.TPaidlotteryDetailsDAO;
import com.digitalchina.xa.it.dao.TPaidlotteryInfoDAO;
import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;
import com.digitalchina.xa.it.model.TPaidlotteryInfoDomain;
import com.digitalchina.xa.it.service.TPaidlotteryService;
import com.digitalchina.xa.it.util.HttpRequest;
import com.digitalchina.xa.it.util.MerkleTrees;
import com.digitalchina.xa.it.util.TConfigUtils;

@Service(value = "TPaidlotteryService")
public class TPaidlotteryServiceImpl implements TPaidlotteryService {
	@Autowired
	private TPaidlotteryDetailsDAO tPaidlotteryDetailsDAO;
	@Autowired
	private TPaidlotteryInfoDAO tPaidlotteryInfoDAO;

	@Override
	public int insertLotteryBaseInfo(TPaidlotteryDetailsDomain tPaidlotteryDetailsDomain) {
		if(tPaidlotteryDetailsDomain != null) {
			try {
				Integer effectedNumber = tPaidlotteryDetailsDAO.insertLotteryBaseInfo(tPaidlotteryDetailsDomain);
				if(effectedNumber > 0) {
					System.out.println(tPaidlotteryDetailsDomain.getId());
					return tPaidlotteryDetailsDomain.getId();
				} else {
					throw new RuntimeException("插入购买奖票信息失败");
				}
			} catch(Exception e) {
				throw new RuntimeException("插入购买奖票信息失败 " + e.getMessage());
			}
		} else {
			throw new RuntimeException("tPaidlotteryDetailsDomain为null");
		}
	}
	
	@Override
	public void runALottery(TPaidlotteryInfoDomain tpid) {
		//开奖，根据lotteryId，更新此次参与者的result，winTicket，winReword字段,更新t_paidlottery_info表flag，lotteryTime，winner，winTicket
		List<String> ticketList = generateWinTicket(tpid.getId(), tpid.getWinCount());
		List<TPaidlotteryDetailsDomain> tpddList = tPaidlotteryDetailsDAO.selectLotteryDetailsByLotteryId(tpid.getId());
		String winTickets = "";
		String winItcodes = "";
		
		for(int index = 0; index < ticketList.size(); index ++) {
			winTickets += ticketList.get(index) + "&";
		}
		winTickets = winTickets.substring(0, winTickets.length() - 1);
		String[] rewardList = tpid.getReward().split("&");
		
		for(int index1 = 0; index1 < tpddList.size(); index1++) {
			TPaidlotteryDetailsDomain tpddTemp = tpddList.get(index1); 
			for(int index2 = 0; index2 < ticketList.size(); index2++) {
				if(tpddTemp.getTicket().equals(ticketList.get(index2))) {
					tPaidlotteryDetailsDAO.updateDetailAfterLotteryFinished(tpddTemp.getId(), 2, winTickets, rewardList[index2]);
					tpddTemp.setResult(2);
					winItcodes += tpddTemp.getItcode() + "&";
					
					//抽奖奖品为神州币
					if(tpid.getTypeCode() == 1){
						//向kafka发送请求，参数为itcode, transactionId,  金额？， lotteryId？; 产生hashcode，更新account字段，并返回hashcode与transactionId。
						String url = TConfigUtils.selectValueByKey("kafka_address") + "/lottery/issueReward";
//						String url = "http://10.7.10.186:8083/lottery/issueReward";
						String postParam = "itcode=" + tpddTemp.getItcode() + "&turnBalance=" + rewardList[index2].toString() + "&transactionDetailId=" + tpid.getId();
						HttpRequest.sendPost(url, postParam);
					}
				} else if(tpddTemp.getResult() != 2) {
					tPaidlotteryDetailsDAO.updateDetailAfterLotteryFinished(tpddTemp.getId(), 1, winTickets, "无");
				}
			}
		}
		winItcodes = winItcodes.substring(0, winItcodes.length() - 1);
		tPaidlotteryInfoDAO.updateAfterLotteryFinished(tpid.getId(), new Timestamp(new Date().getTime()), winItcodes, winTickets);
	}
	
	@Override
	public Boolean updateHashcodeAndJudge(String hashcode, int transactionId) {
		//根据transactionId获取lotteryId
		TPaidlotteryDetailsDomain tpdd = tPaidlotteryDetailsDAO.selectLotteryDetailsById(transactionId);
		int lotteryId = tpdd.getLotteryId();
		
		//计算ticket值,更新该用户的ticket值。
		String ticket = generateTicket(lotteryId, tpdd.getItcode(), hashcode);
		tPaidlotteryDetailsDAO.updateTicket(ticket, transactionId);
		return true;
	}

	@Override
	public List<TPaidlotteryInfoDomain> selectLotteryInfoByFlag(int flag) {
		return tPaidlotteryInfoDAO.selectLotteryInfoByFlag(flag);
	}

	@Override
	public List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcode(String itcode) {
		return tPaidlotteryDetailsDAO.selectLotteryDetailsByItcode(itcode);
	}

	@Override
	public List<TPaidlotteryDetailsDomain> selectLotteryDetailsByLotteryId(int lotteryId) {
		return tPaidlotteryDetailsDAO.selectLotteryDetailsByLotteryId(lotteryId);
	}

	@Override
	public TPaidlotteryInfoDomain selectLotteryInfoById(int id) {
		return tPaidlotteryInfoDAO.selectLotteryInfoById(id);
	}

	@Override
	public List<TPaidlotteryDetailsDomain>selectLotteryDetailsByItcodeAndLotteryId(String itcode, int lotteryId) {
		return tPaidlotteryDetailsDAO.selectLotteryDetailsByItcodeAndLotteryId(itcode, lotteryId);
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

	@Override
	public Boolean updateNowSumAmountAndBackup4(int id) {
		try {
			Integer effectedNumber = tPaidlotteryInfoDAO.updateNowSumAmountAndBackup4(id);
			if(effectedNumber > 0) {
				return true;
			} else {
				throw new RuntimeException("updateNowSumAmountAndBackup4失败");
			}
		} catch(Exception e) {
			throw new RuntimeException("updateNowSumAmountAndBackup4失败 " + e.getMessage());
		}
	}

	@Override
	public TPaidlotteryDetailsDomain selectLotteryDetailsById(int id) {
		return tPaidlotteryDetailsDAO.selectLotteryDetailsById(id);
	}

	@Override
	public TPaidlotteryInfoDomain selectOneSmbTpid() {
		return tPaidlotteryInfoDAO.selectOneSmbTpid();
	}

	@Override
	public List<TPaidlotteryInfoDomain> selectHbTpids() {
		return tPaidlotteryInfoDAO.selectHbTpids();
	}

	@Override
	public List<TPaidlotteryInfoDomain> selectOtherTpids() {
		return tPaidlotteryInfoDAO.selectOtherTpids();
	}

	@Override
	public List<TPaidlotteryInfoDomain> selectNewOpen(int count) {
		return tPaidlotteryInfoDAO.selectNewOpen(count);
	}

	@Override
	public List<TPaidlotteryDetailsDomain> selectLotteryDetailsByItcodeAndResult(String itcode, int result) {
		return tPaidlotteryDetailsDAO.selectLotteryDetailsByItcodeAndResult(itcode, result);
	}
}
