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
import com.digitalchina.xa.it.dao.PaidReadDetailDAO;
import com.digitalchina.xa.it.service.PaidReadDetailService;

@Service(value = "paidReadDetailService")
public class PaidReadDetailServiceImpl implements PaidReadDetailService{
	private volatile static Web3j web3j;
    private static String address = "0x941b8f05561e57f5b9d366c168b85baf900b037c";
//    private static String tempFilePath = "c://";
    private static String tempFilePath = "/eth/temp/";

    @Autowired
    private PaidReadDetailDAO paidReadDetailDAO;
    
    @Autowired
    private EthAccountDAO ethAccountDAO;
}
