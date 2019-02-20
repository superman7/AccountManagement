package com.digitalchina.xa.it.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;

import com.digitalchina.xa.it.dao.EthAccountDAO;
import com.digitalchina.xa.it.dao.PaidVoteDetailDAO;
import com.digitalchina.xa.it.dao.SystemTransactionDetailDAO;
import com.digitalchina.xa.it.model.PaidVoteDetailDomain;
import com.digitalchina.xa.it.model.SystemTransactionDetailDomain;
import com.digitalchina.xa.it.service.PaidVoteDetailService;
import com.digitalchina.xa.it.util.HttpRequest;
import com.digitalchina.xa.it.util.TConfigUtils;

@Service(value = "paidVoteDetailService")
public class PaidVoteDetailServiceImple implements PaidVoteDetailService{
	private volatile static Web3j web3j;
    private static String address = "0x941b8f05561e57f5b9d366c168b85baf900b037c";
//    private static String tempFilePath = "c://";
    private static String tempFilePath = "/eth/temp/";

    @Autowired
    private PaidVoteDetailDAO paidVoteDetailDAO;
    
    @Autowired
    private EthAccountDAO ethAccountDAO;
    @Autowired
    private SystemTransactionDetailDAO systemTransactionDetailDAO;
    
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
		paidVoteDetailDomain.setTransactionStatus(0);
		
		paidVoteDetailDAO.insertPaidVoteDetail(paidVoteDetailDomain);
		Integer transactionDetailId = paidVoteDetailDomain.getId();
		System.err.println(transactionDetailId);
		
		try {
			String ip = TConfigUtils.selectIp();
			if(web3j==null){
		        synchronized (PaidVoteDetailService.class){
		            if(web3j==null){
		                web3j =Web3j.build(new HttpService(ip));
		            }
		        }
		    }
			//FIXME 判断余额是否足够投票
			BigInteger balance = web3j.ethGetBalance(fromaccount,DefaultBlockParameterName.LATEST).send().getBalance().divide(BigInteger.valueOf(10000000000000000L));
			if(Double.valueOf(turncount) > Double.valueOf(balance.toString()) - 10) {
				return "notEnough";
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("查询余额失败");
		}
		
		//向system_transactiondetail表记录信息 
		SystemTransactionDetailDomain stdd = new SystemTransactionDetailDomain("", TConfigUtils.selectValueByKey("lesson_contract"), Double.valueOf(turncount), null, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 0, "remark", fromitcode, "paidvote_contract", "", 0, "", transactionDetailId);
		systemTransactionDetailDAO.insertBaseInfo(stdd);
		
		//向kafka发送交易请求，参数为：account，itcode，金额，transactionDetailId
		String url = TConfigUtils.selectValueByKey("kafka_address") + "/paidVotes/insertVoteDetail";
		String postParam = "itcode=" + fromitcode + "&account=" + toaccount + "&transactionDetailId=" + transactionDetailId 
				+ "&turnBalance=" + BigInteger.valueOf(10000000000000000L).multiply(BigInteger.valueOf(Long.valueOf(turncount)));
		HttpRequest.sendPost(url, postParam);
		return "enough";
	}

	@Override
	public List<Map<String, Object>> selectTop10(Integer topicId) {
		return paidVoteDetailDAO.selectTop5(topicId);
	}

	@Override
	public List<PaidVoteDetailDomain> selectPaidVoteDetailByBeVotedItcode(String beVotedItcode, Integer topicId) {
		return paidVoteDetailDAO.selectPaidVoteDetailByBeVotedItcode(beVotedItcode, topicId);
	}
	
	//FIXME 这里添加扣费逻辑
//	String ip = TConfigUtils.selectIp();
//	if(web3j==null){
//        synchronized (PaidVoteDetailService.class){
//            if(web3j==null){
//                web3j =Web3j.build(new HttpService(ip));
//            }
//        }
//    }
//	TransactionManager transactionManager = null;
//	
//	TransactionReceiptProcessor transactionReceiptProcessor = new NoOpProcessor(web3j);
//	
//	EthAccountDomain ethAccountDomain = new EthAccountDomain();
//	ethAccountDomain.setAccount(fromaccount);
//	String keystore = ethAccountDAO.selectKeystoreByAccount(ethAccountDomain);
//	
//	try {
//		File keystoreFile = new File(tempFilePath + "temp.json");
//        if(!keystoreFile.exists()){
//        	keystoreFile.createNewFile();
//        }
//        FileWriter fw = new FileWriter(keystoreFile.getAbsoluteFile());
//        BufferedWriter bw = new BufferedWriter(fw);
//        bw.write(keystore);
//        bw.close();
//        System.out.println("创建keystore成功。。。");
//        
//		System.out.println("开始解锁。。。");
//		Credentials credentials = WalletUtils.loadCredentials("mini0823", keystoreFile);
//		System.out.println("解锁成功。。。");
//		keystoreFile.delete();
//		System.out.println("删除临时keystore文件成功。。。");
//		
//		transactionManager = new RawTransactionManager(web3j, credentials, (byte) 10, transactionReceiptProcessor);
//		
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (CipherException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//
//	if(transactionManager == null){
//		System.err.println("付费投票模块:构建" + fromitcode + "交易管理异常！");
//		return "error";
//	}
//	
//	PaidVote contract = PaidVote.load(address, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
//	
//	System.err.println("付费投票模块:构建交易管理成功！");
//	
//	try {
//		TransactionReceipt transactionReceipt = contract.buyChapter(new Address(toaccount), 
//				BigInteger.valueOf(10000000000000000L).multiply(BigInteger.valueOf(Long.valueOf(turncount)))).send();
//		
//		String result = transactionReceipt.getTransactionHash();
//		System.out.println("付费投票模块:构建交易管理成功！Hash值为" + result);
//		paidVoteDetailDAO.updateTransactionHash(paidVoteDetailDomain.getId(), result);
//
//		web3j.shutdown();
//		return result;
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		web3j.shutdown();
//		return "error";
//	}
}
