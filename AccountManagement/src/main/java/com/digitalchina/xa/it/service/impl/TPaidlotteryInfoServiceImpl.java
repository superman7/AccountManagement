package com.digitalchina.xa.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.TPaidlotteryDetailsDAO;
import com.digitalchina.xa.it.dao.TPaidlotteryInfoDAO;
import com.digitalchina.xa.it.service.TPaidlotteryDetailsService;
import com.digitalchina.xa.it.service.TPaidlotteryInfoService;

@Service(value = "TPaidlotteryInfoService")
public class TPaidlotteryInfoServiceImpl implements TPaidlotteryInfoService {
	@Autowired
	private TPaidlotteryInfoDAO tPaidlotteryInfoDAO;

}
