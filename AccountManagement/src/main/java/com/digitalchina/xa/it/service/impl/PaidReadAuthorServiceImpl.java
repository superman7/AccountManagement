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
import com.digitalchina.xa.it.dao.PaidReadAuthorDAO;
import com.digitalchina.xa.it.model.EthAccountDomain;
import com.digitalchina.xa.it.model.PaidReadAuthorDomain;
import com.digitalchina.xa.it.service.PaidReadAuthorService;

@Service(value = "paidReadAuthorService")
public class PaidReadAuthorServiceImpl implements PaidReadAuthorService{
	private volatile static Web3j web3j;
    private static String address = "0x941b8f05561e57f5b9d366c168b85baf900b037c";
//    private static String tempFilePath = "c://";
    private static String tempFilePath = "/eth/temp/";

    @Autowired
    private PaidReadAuthorDAO paidReadAuthorDAO;
    
    @Autowired
    private EthAccountDAO ethAccountDAO;

	@Override
	public Integer selectAuthorIfSaved(String itcode) {
		Integer counter = paidReadAuthorDAO.selectAuthorIfSaved(itcode);
		if(counter > 0){
			return paidReadAuthorDAO.selectAuthorByItcode(itcode);
		}else{
			PaidReadAuthorDomain prad = new PaidReadAuthorDomain();
			prad.setItcode(itcode);
			paidReadAuthorDAO.insertPaidReadAuthor(prad);
			return prad.getId();
		}
	}
}
