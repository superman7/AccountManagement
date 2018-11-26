package com.digitalchina.xa.it.fanwei.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ChainId;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import com.digitalchina.xa.it.contract.Qiandao;
import com.digitalchina.xa.it.fanwei.service.QiandaoContractService;

@Service("qiandaoContractService")
@Transactional(timeout = 600)
public class QiandaoContractServiceImpl implements QiandaoContractService{
	@Autowired
	private TurnCountRepository turnCountRepository;
	@Autowired
	private SmbDchRepository smbDchRepository;
	@Autowired
	private TurnCountService turnCountService;
	
	private static final String rootPath = "/eth/datadir/keystore/";
	private static final String address = "0xa3bd7ba69b93d2e1f7708fafd14ba5723ae4799a";
	private volatile static Web3j web3j;
    private static String ip = "";
    private static String[] ipArr = {"http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};

	@Override
	public String chargeToContract(String value) {
		Integer index = (int)(Math.random()*5);
    	ip = ipArr[index];
		System.err.println("为签到合约充值的以太坊链接ip为"+ip);
		if(web3j==null){
            synchronized (QiandaoContractService.class){
                if(web3j==null){
                    web3j =Web3j.build(new HttpService(ip));
                }
            }
        }
		long chargeValueLong;
		try {
			chargeValueLong = Long.parseLong(value);
		} catch (NumberFormatException e) {
			chargeValueLong = 1;
		}
		String walletfile = rootPath + "UTC--2018-02-05T15-28-30.373580131Z--8c735de7b8c388347b7443b492740a9c80df20a6";
		
		Credentials credentials;
		try {
			credentials = WalletUtils.loadCredentials("mini0823", walletfile);
			
			Qiandao contract = Qiandao.load(address, web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
			
			BigInteger charge = BigInteger.valueOf(10000000000000000L).multiply(BigInteger.valueOf(chargeValueLong));
			
			TransactionReceipt transactionReceipt = contract.chargeToContract(charge).send();
			
			String result = transactionReceipt.getTransactionHash();
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@Override
	public String signinReward(String itcode) {
		Integer index = (int)(Math.random()*5);
    	ip = ipArr[index];
		System.err.println("签到获得奖励的以太坊链接ip为"+ip);
		if(web3j==null){
            synchronized (QiandaoContractService.class){
                if(web3j==null){
                    web3j =Web3j.build(new HttpService(ip));
                }
            }
        }
		SmbDchBean sdb = findByItcode(itcode);
		
		if(sdb == null){
			return "error.Can not find this itcode.";
		}
		
		String accountkey = sdb.getAccountkey();
		String keystore = sdb.getKeystore();
		
		String contractBalance = turnCountService.getBalance(address);
        
		String turnBalance = new BigDecimal(contractBalance).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_DOWN).toString();
		
		// 获取当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
		
		TurnCountBean turnCountBean = new TurnCountBean(null, "0x8c735de7b8c388347b7443b492740a9c80df20a6", accountkey, Double.parseDouble(turnBalance), null, dateStr, null, null, "签到随机奖励！", "root", itcode, null,null);
		turnCountRepository.save(turnCountBean);  // 保存奖励交易基本信息
		
		String walletfile = rootPath + keystore;
		
		Credentials credentials;
		try {
			credentials = WalletUtils.loadCredentials("mini0823", walletfile);
			
			Qiandao contract = Qiandao.load(address, web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
			
//			TransactionReceipt transactionReceipt = contract.checkUser(BigInteger.valueOf(10000L)).sendAsync().get();
//			
//			String result = transactionReceipt.getTransactionHash();
//			
//			turnCountRepository.updateTurnhash("0x8c735de7b8c388347b7443b492740a9c80df20a6", result, dateStr);
			
//			return result;
			
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
	}
	
	@Override
	public String signinReward(String itcode, int reward) {
		Integer index = (int)(Math.random()*5);
    	ip = ipArr[index];
		System.err.println("签到获得奖励的以太坊链接ip为"+ip);
		if(web3j==null){
            synchronized (QiandaoContractService.class){
                if(web3j==null){
                    web3j =Web3j.build(new HttpService(ip));
                }
            }
        }
		SmbDchBean sdb = findByItcode(itcode);
		
		if(sdb == null){
			return "error.Can not find this itcode.";
		}
		
		String accountkey = sdb.getAccountkey();
		String keystore = sdb.getKeystore();
		
		//FIXME 此处后期添加对合约余额的监控及充值代码
		String contractBalance = turnCountService.getBalance(address);
        System.out.println("当前签到合约余额为：" + contractBalance);
//		String turnBalance = new BigDecimal(contractBalance).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_DOWN).toString();
		
		String turnBalance = new BigDecimal(reward).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN).toString();
		// 获取当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
		
		TurnCountBean turnCountBean = new TurnCountBean(null, "0x8c735de7b8c388347b7443b492740a9c80df20a6", accountkey, Double.parseDouble(turnBalance), null, dateStr, null, null, "签到随机奖励！", "root", itcode, null,null);
		turnCountRepository.save(turnCountBean);  // 保存奖励交易基本信息
		
		String walletfile = rootPath + keystore;
		
		TransactionManager transactionManager = this.buildTransactionManager(web3j, walletfile);
		if(transactionManager == null){
			System.err.println("签到奖励模块:构建" + itcode + "交易管理异常！");
			return "error";
		}
		
		Qiandao contract = Qiandao.load(address, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		
		try {
			TransactionReceipt transactionReceipt = contract.qiandaoReward(new Uint256(BigInteger.valueOf(10000000000000000L).multiply(BigInteger.valueOf(reward)))).send();
			
			String result = transactionReceipt.getTransactionHash();
			System.err.println(dateStr + "发放" + itcode + "签到奖励!__" + result);
			turnCountRepository.updateTurnhash("0x8c735de7b8c388347b7443b492740a9c80df20a6", result, dateStr);

			web3j.shutdown();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			web3j.shutdown();
			return "error";
		}
	}
	
	public SmbDchBean findByItcode(String itcode) {
		List<SmbDchBean> list = smbDchRepository.findByItcode(itcode);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public String findItcodeByEmployeeNumber(String employeeNumber) {
		List<String> list = smbDchRepository.findItcodeByEmployeeNumber(employeeNumber);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	public TransactionManager buildTransactionManager(Web3j web3j1, String walletfile){
		TransactionReceiptProcessor transactionReceiptProcessor = new NoOpProcessor(web3j1);
		
		try {
			Credentials credentials = WalletUtils.loadCredentials("mini0823", walletfile);
			
			TransactionManager transactionManager = new RawTransactionManager(web3j, credentials, (byte) 10, transactionReceiptProcessor);
			
			return transactionManager;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (CipherException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	};
	
	@Override
	public String voteReward(String itcode) {
		try {
			//FIXME 因为会和签到调用合约的nonce值重复，所以暂时先这样处理，后面使用MQ进行排队
			Thread.sleep(20000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		Integer index = (int)(Math.random()*5);
    	ip = ipArr[index];
		System.err.println("参与投票获得奖励的以太坊链接ip为"+ip);
		if(web3j==null){
            synchronized (QiandaoContractService.class){
                if(web3j==null){
                    web3j =Web3j.build(new HttpService(ip));
                }
            }
        }
		SmbDchBean sdb = findByItcode(itcode);
		
		if(sdb == null){
			return "error.Can not find this itcode.";
		}
		
		String accountkey = sdb.getAccountkey();
		String keystore = sdb.getKeystore();
		
		//FIXME 此处后期添加对合约余额的监控及充值代码
		String contractBalance = turnCountService.getBalance(address);
        
		String turnBalance = new BigDecimal(1).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN).toString();
		// 获取当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
		
		TurnCountBean turnCountBean = new TurnCountBean(null, "0x8c735de7b8c388347b7443b492740a9c80df20a6", accountkey, Double.parseDouble(turnBalance), null, dateStr, null, null, "参与投票获得奖励！", "root", itcode, null,null);
		turnCountRepository.save(turnCountBean);  // 保存奖励交易基本信息
		
		String walletfile = rootPath + keystore;
		
		TransactionManager transactionManager = this.buildTransactionManager(web3j, walletfile);
		if(transactionManager == null){
			System.err.println("投票奖励模块:构建" + itcode + "交易管理异常！");
			return "error";
		}
		
		Qiandao contract = Qiandao.load(address, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
		
		try {
			
			TransactionReceipt transactionReceipt = contract.qiandaoReward(new Uint256(BigInteger.valueOf(10000000000000000L).multiply(BigInteger.valueOf(1)))).send();
			
			String result = transactionReceipt.getTransactionHash();
			System.err.println(dateStr + "发放" + itcode + "投票奖励!__" + result);
			
			turnCountRepository.updateTurnhash("0x8c735de7b8c388347b7443b492740a9c80df20a6", result, dateStr);

			web3j.shutdown();
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			web3j.shutdown();
			return "error";
		}
	}
	/* 
	 * @desc 考勤奖励发放
	 */
	@Override
	public void attendanceReward(String employeeNumber) {
		Integer index = (int)(Math.random()*5);
    	ip = ipArr[index];
		System.err.println("考勤奖励的以太坊链接ip为"+ip);
		if(web3j==null){
            synchronized (QiandaoContractService.class){
                if(web3j==null){
                    web3j =Web3j.build(new HttpService(ip));
                }
            }
        }
		
//		web3j = Web3j.build(new HttpService("http://10.0.22.234:8545"));
		String[] employeeNumberList = employeeNumber.split(",");

		for (String employeeNumberString : employeeNumberList) {
			String itcode = findItcodeByEmployeeNumber(employeeNumberString);
			if(itcode == null){
				continue ;
			}
			SmbDchBean sdb = findByItcode(itcode);
			
			if(sdb == null){
				continue ;
			}
			
			String accountkey = sdb.getAccountkey();
			String keystore = sdb.getKeystore();
			String turnBalance = new BigDecimal(5).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN).toString();
			// 获取当前时间
	        Date date = new Date();
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String dateStr = format.format(date);
			
			TurnCountBean turnCountBean = new TurnCountBean(null, "0x8c735de7b8c388347b7443b492740a9c80df20a6", accountkey, Double.parseDouble(turnBalance), null, dateStr, null, null, "考勤奖励！", "root", itcode, null,null);
			turnCountRepository.save(turnCountBean);  // 保存奖励交易基本信息
			
			String walletfile = rootPath + keystore;
			
			TransactionManager transactionManager = this.buildTransactionManager(web3j, walletfile);
			if(transactionManager == null){
				System.err.println("考勤奖励模块:构建" + itcode + "交易管理异常！");
				continue;
			}
			
			Qiandao contract = Qiandao.load(address, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
			
			TransactionReceipt transactionReceipt;
			try {
				transactionReceipt = contract.qiandaoReward(new Uint256(BigInteger.valueOf(10000000000000000L).multiply(BigInteger.valueOf(5)))).send();
				
				String result = transactionReceipt.getTransactionHash();
				
				System.err.println(dateStr + "发放昨日" + itcode + "考勤奖励!__" + result);
				
				turnCountRepository.updateTurnhash("0x8c735de7b8c388347b7443b492740a9c80df20a6", result, dateStr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
		//关闭web3j的链接
		web3j.shutdown();
//		for (String employeeNumberString : employeeNumberList) {
//			String itcode = findItcodeByEmployeeNumber(employeeNumberString);
//			
//			if(itcode == null){
//				continue ;
//			}
//			SmbDchBean sdb = findByItcode(itcode);
//			
//			if(sdb == null){
//				continue ;
//			}
//			
//			String accountkey = sdb.getAccountkey();
//			String keystore = sdb.getKeystore();
//			
//			//FIXME 此处后期添加对合约余额的监控及充值代码
////			String contractBalance = turnCountService.getBalance(address);
//	        
////			String turnBalance = new BigDecimal(contractBalance).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_DOWN).toString();
//			
//			String turnBalance = new BigDecimal(1).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN).toString();
//			// 获取当前时间
//	        Date date = new Date();
//	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	        String dateStr = format.format(date);
//			
//			TurnCountBean turnCountBean = new TurnCountBean(null, "0x8c735de7b8c388347b7443b492740a9c80df20a6", accountkey, Double.parseDouble(turnBalance), null, dateStr, null, null, "考勤奖励！", "root", itcode, null,null);
//			turnCountRepository.save(turnCountBean);  // 保存奖励交易基本信息
//			
//			String walletfile = rootPath + keystore;
//			
//			Credentials credentials;
//			try {
//				credentials = WalletUtils.loadCredentials("mini0823", walletfile);
//				
//				Qiandao contract = Qiandao.load(address, web3j, credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
//				
//				TransactionReceipt transactionReceipt = contract.qiandaoReward(new Uint256(BigInteger.valueOf(10000000000000000L).multiply(BigInteger.valueOf(1)))).sendAsync().get();
//				
//				String result = transactionReceipt.getTransactionHash();
//				
//				turnCountRepository.updateTurnhash("0x8c735de7b8c388347b7443b492740a9c80df20a6", result, dateStr);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				continue ;
//			}
//		}
	}
	
	/* 
	 * @desc 二次推荐奖励发放
	 */
	@Override
	public void inviteFriendsReward(String itcodeStr) {
		Integer index = (int)(Math.random()*5);
    	ip = ipArr[index];
		System.err.println("考勤奖励的以太坊链接ip为"+ip);
		if(web3j==null){
            synchronized (QiandaoContractService.class){
                if(web3j==null){
                    web3j =Web3j.build(new HttpService(ip));
                }
            }
        }
		
		String[] itcodeArray = itcodeStr.split(",");

		for (String itcode : itcodeArray) {
			if(itcode == null){
				continue ;
			}
			SmbDchBean sdb = findByItcode(itcode);
			
			if(sdb == null){
				continue ;
			}
			
			String accountkey = sdb.getAccountkey();
			String keystore = sdb.getKeystore();
			String turnBalance = new BigDecimal(10).divide(new BigDecimal("1"), 2, BigDecimal.ROUND_DOWN).toString();
			// 获取当前时间
	        Date date = new Date();
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String dateStr = format.format(date);
			
			TurnCountBean turnCountBean = new TurnCountBean(null, "0x8c735de7b8c388347b7443b492740a9c80df20a6", accountkey, Double.parseDouble(turnBalance), null, dateStr, null, null, "邀请好友使用神州区块链奖励！", "root", itcode, null,null);
			turnCountRepository.save(turnCountBean);  // 保存奖励交易基本信息
			
			String walletfile = rootPath + keystore;
			
			TransactionManager transactionManager = this.buildTransactionManager(web3j, walletfile);
			if(transactionManager == null){
				System.err.println("邀请好友奖励模块:构建" + itcode + "交易管理异常！");
				continue ;
			}
			
			Qiandao contract = Qiandao.load(address, web3j, transactionManager, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
			
			TransactionReceipt transactionReceipt;
			try {
				transactionReceipt = contract.qiandaoReward(new Uint256(BigInteger.valueOf(10000000000000000L).multiply(BigInteger.valueOf(1)))).send();
				
				String result = transactionReceipt.getTransactionHash();
				
				System.err.println(dateStr + "发放今日" + itcode + "邀请好友奖励!__" + result);
				
				turnCountRepository.updateTurnhash("0x8c735de7b8c388347b7443b492740a9c80df20a6", result, dateStr);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue ;
			}
		}
		//关闭web3j的链接
		web3j.shutdown();
	}
}
