package com.digitalchina.xa.it.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.xa.it.dao.SystemTransactionDetailDAO;
import com.digitalchina.xa.it.model.SystemTransactionDetailDomain;
import com.digitalchina.xa.it.service.SystemTransactionDetailService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "/transactionDetail")
public class SystemTransactionDetailController {
	@Autowired
	private SystemTransactionDetailService systemTransactionDetailService;
	
	@Autowired
	private SystemTransactionDetailDAO systemTransactionDetailDAO;
	
	private static int pageNum = 0;
	private static int pageSize = 20;

	@ResponseBody
	@GetMapping("/all")
	public PageInfo<SystemTransactionDetailDomain> selectAllTransactionDetailByItcode(
			@RequestParam(name = "itcode", required = true) String itcode){
		return systemTransactionDetailService.selectAllTransactionDetailByItcode(itcode, pageNum, pageSize);
	}
}
