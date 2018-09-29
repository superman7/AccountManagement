package com.digitalchina.xa.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digitalchina.xa.it.service.PaidVoteDetailService;
import com.digitalchina.xa.it.service.PaidVoteTop10Service;
import com.digitalchina.xa.it.service.PaidVoteTopicService;

@Controller
@RequestMapping(value = "/paidVotes")
public class PaidVoteController {
	@Autowired
	private PaidVoteDetailService paidVoteDetailService;
	@Autowired
	private PaidVoteTop10Service paidVoteTop10Service;
	@Autowired
	private PaidVoteTopicService paidVoteTopicService;
	
	
}
