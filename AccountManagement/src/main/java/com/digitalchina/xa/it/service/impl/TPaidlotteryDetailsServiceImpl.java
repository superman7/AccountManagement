package com.digitalchina.xa.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.TPaidlotteryDetailsDAO;
import com.digitalchina.xa.it.service.TPaidlotteryDetailsService;

@Service(value = "TPaidlotteryDetailsService")
public class TPaidlotteryDetailsServiceImpl implements TPaidlotteryDetailsService {
	@Autowired
	private TPaidlotteryDetailsDAO tPaidlotteryDetailsDAO;

}
