package com.digitalchina.xa.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import com.digitalchina.xa.it.dao.PaidVoteTop10DAO;
import com.digitalchina.xa.it.service.PaidVoteTop10Service;

@Service(value = "paidVoteTop10Service")
public class PaidVoteTop10ServiceImpl implements PaidVoteTop10Service{

	private volatile static Web3j web3j;
    private static String ip = "http://10.7.10.124:8545";
    
    @Autowired
    private PaidVoteTop10DAO paidVoteTop10DAO;
    
    
}
