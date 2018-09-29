package com.digitalchina.xa.it.service.impl;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

import com.digitalchina.xa.it.service.PaidVoteDetailService;

@Service(value = "paidVoteDetailService")
public class PaidVoteDetailServiceImple implements PaidVoteDetailService{
	private volatile static Web3j web3j;
    private static String ip = "http://10.7.10.124:8545";
    private static String[] ipArr = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};

}
