package com.digitalchina.xa.it.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.NoOpProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import com.digitalchina.xa.it.dao.EthAccountDAO;
import com.digitalchina.xa.it.dao.PaidReadArticleDAO;
import com.digitalchina.xa.it.model.EthAccountDomain;
import com.digitalchina.xa.it.model.PaidReadArticleDomain;
import com.digitalchina.xa.it.service.PaidReadArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service(value = "paidReadArticleService")
public class PaidReadArticleServiceImpl implements PaidReadArticleService{
	private volatile static Web3j web3j;
    private static String address = "0x941b8f05561e57f5b9d366c168b85baf900b037c";
    private static String[] ipArr = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};
//    private static String tempFilePath = "c://";
    private static String tempFilePath = "/eth/temp/";

    @Autowired
    private PaidReadArticleDAO paidReadArticleDAO;
    
    @Autowired
    private EthAccountDAO ethAccountDAO;

	@Override
    @Transactional
	public int insertPaidReadArticle(PaidReadArticleDomain paidReadArticleDomain) {
		return paidReadArticleDAO.insertPaidReadArticle(paidReadArticleDomain);
	}
	
	@Override
	public PageInfo<PaidReadArticleDomain> selectPaidReadArticleByTime(int pageNum, int pageSize) {
		//将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<PaidReadArticleDomain> paidReadArticleDomain = paidReadArticleDAO.selectPaidReadArticleByTime();
        PageInfo<PaidReadArticleDomain> result = new PageInfo<PaidReadArticleDomain>(paidReadArticleDomain);
        return result;
	}

	@Override
	public PageInfo<PaidReadArticleDomain> selectPaidReadArticleByHot(int pageNum, int pageSize) {
		//将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<PaidReadArticleDomain> paidReadArticleDomain = paidReadArticleDAO.selectPaidReadArticleByHot();
        PageInfo<PaidReadArticleDomain> result = new PageInfo<PaidReadArticleDomain>(paidReadArticleDomain);
        return result;
	}
}
