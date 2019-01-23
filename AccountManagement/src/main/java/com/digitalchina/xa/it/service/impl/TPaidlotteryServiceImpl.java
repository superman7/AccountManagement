package com.digitalchina.xa.it.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock.Block;
import org.web3j.protocol.http.HttpService;

import com.digitalchina.xa.it.dao.TPaidlotteryDetailsDAO;
import com.digitalchina.xa.it.dao.TPaidlotteryInfoDAO;
import com.digitalchina.xa.it.model.TPaidlotteryDetailsDomain;
import com.digitalchina.xa.it.model.TPaidlotteryInfoDomain;
import com.digitalchina.xa.it.service.EthAccountService;
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
	
	@Autowired
	private EthAccountService ethAccountService;
	
	
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
		List<String> ticketList = generateWinTicketNew(tpid.getId(), tpid.getWinCount(), 0);
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
		tPaidlotteryInfoDAO.updateAfterLotteryFinished(tpid.getId(), new Timestamp(new Date().getTime()), winItcodes, winTickets, 0);
	}

	@Override
	public void runOptionLottery(Integer lotteryId, Integer option) {
		TPaidlotteryInfoDomain tpid = tPaidlotteryInfoDAO.selectLotteryInfoById(lotteryId);
		//开奖，根据lotteryId，更新此次参与者的result，winTicket，winReword字段,更新t_paidlottery_info表flag，lotteryTime，winner，winTicket
		List<String> ticketList = generateWinTicketNew(tpid.getId(), tpid.getWinCount(), option);
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
					if(tpid.getBackup5() == 1){
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
		tPaidlotteryInfoDAO.updateAfterLotteryFinished(tpid.getId(), new Timestamp(new Date().getTime()), winItcodes, winTickets, option);
	}
	
	@Override
	public Boolean updateHashcodeAndJudge(String hashcode, int transactionId) {
		//根据transactionId获取lotteryId
		TPaidlotteryDetailsDomain tpdd = tPaidlotteryDetailsDAO.selectLotteryDetailsById(transactionId);
		int lotteryId = tpdd.getLotteryId();
		
		//计算ticket值,更新该用户的ticket值。
		if(tpdd.getBackup4() - 2 <= 0){
			String ticket = generateTicket(lotteryId, tpdd.getItcode(), hashcode);
			tPaidlotteryDetailsDAO.updateTicket(ticket, transactionId);	
		}
		
		if(tpdd.getBackup4() - 2 > 0){
			String ticket = generateTicket(lotteryId, tpdd.getItcode(), hashcode);
			String account = ethAccountService.selectDefaultEthAccount(tpdd.getItcode()).getAccount();
			tPaidlotteryDetailsDAO.updateInviteTicket(ticket, transactionId, account);	
		}
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
	public List<String> generateWinTicketNew(int lotteryId, int winCount, int option) {
		List<String> result = new ArrayList<String> ();
		Web3j web3j = Web3j.build(new HttpService(TConfigUtils.selectIp()));
		try {
			TPaidlotteryInfoDomain tplid = tPaidlotteryInfoDAO.selectNewOpen(1).get(0);
			String lastWinner = tplid.getWinner();
			String lastWinTicket = tplid.getWinTicket();
			
			Block winBlock = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getResult();
			String winBlockHash = String.valueOf(winBlock.getHash());
			String winBlockTotalDifficulty = String.valueOf(winBlock.getTotalDifficulty());
			String winBlockNonce = String.valueOf(winBlock.getNonce());
			String winBlockTimestamp = String.valueOf(winBlock.getTimestamp());
			//根据以上参数计算MerkleTreesRoot
			List<String> tempTxList = new ArrayList<String>();
			tempTxList.add(lastWinner);
			tempTxList.add(lastWinTicket);
			tempTxList.add(winBlockHash);
			tempTxList.add(winBlockTotalDifficulty);
			tempTxList.add(winBlockNonce);
			tempTxList.add(winBlockTimestamp);
			MerkleTrees merkleTrees = new MerkleTrees(tempTxList);
		    merkleTrees.merkle_tree();
		    String merkleTreesRoot = merkleTrees.getRoot();
		    BigInteger temp1 = new BigInteger(merkleTreesRoot, 16);
		    
		    List<String> ticketList = tPaidlotteryDetailsDAO.generateWinTicketNew1(lotteryId, option);
		    BigInteger ticketListSize = new BigInteger(String.valueOf(ticketList.size() - 1));
//		    System.err.println(ticketListSize);
//		    System.err.println(temp1.divideAndRemainder(ticketListSize)[1].toString());
//		    System.err.println(ticketList.get(Integer.valueOf(temp1.divideAndRemainder(ticketListSize)[1].toString())));
		    //添加中奖数字
		    result.add(ticketList.get(Integer.valueOf(temp1.divideAndRemainder(ticketListSize)[1].toString())));
		    
		    //更新当期开奖区块hash
		    tPaidlotteryInfoDAO.updateLotteryWinBlockHash(lotteryId,winBlockHash);
		} catch (IOException e) {
			result.add("error");
			e.printStackTrace();
			return result;
		}
		return result;
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

	@Override
	public Integer selectLastRMBLottery() {
		return tPaidlotteryInfoDAO.selectLastRMBLottery();
	}

	@Override
	public Boolean updateLotteryReward(int id, String reward) {
		if(id != 0 && reward != null) {
			try {
				Integer effectedNumber = tPaidlotteryInfoDAO.updateLotteryReward(id, reward);
				if(effectedNumber > 0) {
					return true;
				} else {
					throw new RuntimeException("更新reward失败");
				}
			} catch(Exception e) {
				throw new RuntimeException("更新reward失败 " + e.getMessage());
			}
		} else {
			throw new RuntimeException("reward或id不正确");
		}
	}

	@Override
	public List<TPaidlotteryDetailsDomain> selectUninviteLotteryDetailsByItcodeAndLotteryId(String itcode, int lotteryId) {
		return tPaidlotteryDetailsDAO.selectUninviteLotteryDetailsByItcodeAndLotteryId(itcode, lotteryId);
	}

	@Override
	public List<TPaidlotteryDetailsDomain> selectHaveInvitedByItcodeAndLotteryId(String itcode, int lotteryId) {
		return tPaidlotteryDetailsDAO.selectHaveInvitedByItcodeAndLotteryId(itcode, lotteryId);
	}

	@Override
	public List<TPaidlotteryDetailsDomain> selectIfInvitedByItcodeAndLotteryId(String itcode, String invitedItcode, int lotteryId) {
		return tPaidlotteryDetailsDAO.selectIfInvitedByItcodeAndLotteryId(itcode, invitedItcode, lotteryId);
	}

	@Override
	public List<TPaidlotteryDetailsDomain> selectInviteLotteryDetailsByItcodeAndLotteryId(String itcode, int lotteryId) {
		return tPaidlotteryDetailsDAO.selectInviteLotteryDetailsByItcodeAndLotteryId(itcode, lotteryId);
	}
	
	@Override
	public List<TPaidlotteryDetailsDomain> selectAcceptInviteLotteryDetailsByItcodeAndLotteryId(String itcode, int lotteryId) {
		return tPaidlotteryDetailsDAO.selectAcceptInviteLotteryDetailsByItcodeAndLotteryId(itcode, lotteryId);
	}
}
