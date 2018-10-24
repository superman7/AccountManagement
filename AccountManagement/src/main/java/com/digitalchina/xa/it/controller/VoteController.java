package com.digitalchina.xa.it.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.model.TopicDomain;
import com.digitalchina.xa.it.model.TopicOptionDomain;
import com.digitalchina.xa.it.model.VoteDomain;
import com.digitalchina.xa.it.service.TopicOptionService;
import com.digitalchina.xa.it.service.TopicService;
import com.digitalchina.xa.it.service.VoteService;
import com.digitalchina.xa.it.util.Encrypt;
import com.digitalchina.xa.it.util.EncryptImpl;

import cn.com.weaver.workflow.webservices.CreateTopicServicePortType;
import cn.com.weaver.workflow.webservices.CreateTopicServicePortTypeProxy;

@Controller
@RequestMapping(value = "/vote")
public class VoteController {
	@Autowired
	private TopicService topicService;
	@Autowired
	private TopicOptionService topicOptionService;
	@Autowired
	private VoteService voteService;
	
	/**
	 * @desc 添加投票主题及选项（管理员调用）
	 * @return
	 */
	@ResponseBody
	@GetMapping("/topic/add")
	@Transactional
	public int addTopic(
            @RequestParam(name = "param", required = true) String jsonValue){
		System.out.println(jsonValue);
		Encrypt encrypt = new EncryptImpl();
    	String decrypt = null;
		try {
			decrypt = encrypt.decrypt(jsonValue);
		} catch (Exception e1) {
			e1.printStackTrace();
			return 0;
		}
    	String data = null;
		try {
			data = URLDecoder.decode(decrypt, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		}
    	System.err.println("解密的主题及选项JSON为:" + data);
    	
		JSONObject obj = JSON.parseObject(data);
		String topicName = obj.getString("topic");
		JSONArray options = (JSONArray) obj.get("options");
		
		//添加提交人的itcode
		String itcode = obj.getString("itcode");

		CreateTopicServicePortType ctspt = new CreateTopicServicePortTypeProxy();
		
		String params = topicName + "--" + itcode + "--";
		
		for(int i = 0; i < options.size(); i++){
			params += (String) options.get(i) + "##";
		}
		params = params.substring(0, params.length() - 2);
		try {
			System.err.println(params);
			String result = ctspt.autoTriggerTopic(params);
			if(!result.contains("<flag>T</flag>")){
				return 0;
			}
			System.err.println(result);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		TopicDomain topic = new TopicDomain();
		topic.setName(topicName);
		topic.setType(itcode);
		//待审批设置为3
		topic.setAvailable(3);
		int insertCounter = topicService.addTopic(topic);
		int topicId = 0;
		if(insertCounter > 0){
			topicId = topic.getId();
		}
		
		for(int i = 0; i < options.size(); i++){
			TopicOptionDomain tod = new TopicOptionDomain();
			tod.setTopicid(topicId);
			tod.setOptionkey((String) options.get(i));
			topicOptionService.addTopicOption(tod);
		}
	    return 1;
	}
	
	/**
	 * @desc 添加投票话题审批后更新状态
	 * @return
	 */
	@ResponseBody
	@GetMapping("/topic/updateTopicStatus")
	@Transactional
	public int updateTopicStatus(
            @RequestParam(name = "param", required = true) String topicName){
		Encrypt encrypt = new EncryptImpl();
    	String decrypt = null;
		try {
			decrypt = encrypt.decrypt(topicName);
		} catch (Exception e1) {
			e1.printStackTrace();
			return 0;
		}
    	String data = null;
		try {
			data = URLDecoder.decode(decrypt, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		}
		System.err.println(data);
		topicService.updateTopicStatus(data);
		return 1;
	}
	
	/**
	 * @desc 查询所有投票主题
	 * @return
	 */
	@ResponseBody
	@GetMapping("/topic/all")
	public Object findAllTopic(
	        @RequestParam(name = "pageNum", required = false, defaultValue = "1")
	                int pageNum,
	        @RequestParam(name = "pageSize", required = false, defaultValue = "10")
	                int pageSize){
	    return topicService.findAllTopic(pageNum, pageSize);
	}
	
	/**
	 * @desc 根据热度查询所有投票主题
	 * @return
	 */
	@ResponseBody
	@GetMapping("/topic/hot")
	public Object findHotTopic(
	        @RequestParam(name = "pageNum", required = false, defaultValue = "1")
	                int pageNum,
	        @RequestParam(name = "pageSize", required = false, defaultValue = "10")
	                int pageSize){
	    return topicService.selectHotTopicByPopularityDesc(pageNum, pageSize);
	}
	
	/**
	 * @desc 根据话题时间查询所有投票主题
	 * @return
	 */
	@ResponseBody
	@GetMapping("/topic/new")
	public Object findNewTopic(
	        @RequestParam(name = "pageNum", required = false, defaultValue = "1")
	                int pageNum,
	        @RequestParam(name = "pageSize", required = false, defaultValue = "10")
	                int pageSize){
	    return topicService.selectNewTopicByPopularityDesc(pageNum, pageSize);
	}
	
	/**
	 * @desc 根据优先级查询所有投票主题
	 * @return
	 */
	@ResponseBody
	@GetMapping("/topic/priority")
	public Object findPriorityTopic(
	        @RequestParam(name = "pageNum", required = false, defaultValue = "1")
	                int pageNum,
	        @RequestParam(name = "pageSize", required = false, defaultValue = "10")
	                int pageSize){
	    return topicService.selectPriorityTopicByPopularityDesc(pageNum, pageSize);
	}
	
	/**
	 * @desc 根据ID查询投票主题
	 * @return
	 */
	@ResponseBody
	@GetMapping("/topic/{id}")
	public Object findTopicByID(@PathVariable int id){
	    return topicService.findTopicByID(id);
	}
	
	/**
	 * @desc 添加投票选项（暂未使用）
	 * @return
	 */
	@ResponseBody
	@GetMapping("/topicOption/add")
	public int addTopicOption(TopicOptionDomain topicOption){
	    return topicOptionService.addTopicOption(topicOption);
	}
	
	/**
	 * @desc 根据投票主题ID查询所有的选项
	 * @return
	 */
	@ResponseBody
	@GetMapping("/topicOption/topicID/{topicID}")
	public Object findTopicOptionBytopicID(@PathVariable int topicID){
	    return topicOptionService.findTopicOptionByID(topicID);
	}
	
	/**
	 * @desc 添加投票信息，必填参数，主题ID，选项ID，账户
	 * @return
	 */
	@ResponseBody
	@GetMapping("/add")
	public int addVote(
	        @RequestParam(name = "topicID", required = true) int topicID,
            @RequestParam(name = "optionID", required = true) int optionID,
            @RequestParam(name = "account", required = true) String account){
		
		VoteDomain vote = new VoteDomain();
		vote.setTopicID(topicID);
		vote.setOptionID(optionID);
		vote.setAccount(account);
		Timestamp nousedate = new Timestamp(System.currentTimeMillis());
		vote.setVotetime(nousedate);
		
		int result = voteService.addVote(vote);
		if(result != 0){
			topicService.updatePopularity(vote.getTopicID());
			topicOptionService.updatePopularity(vote.getOptionID());
		}
	    return result;
	}
	
	/**
	 * @desc 根据投票主题ID和账户，查询今日投票状态
	 * @return
	 */
	@ResponseBody
	@GetMapping("/todayStatus")
	public Object findVoteInfoByAccountToday(
	        @RequestParam(name = "topicID", required = true) int topicID,
            @RequestParam(name = "account", required = true) String account){
		VoteDomain record = new VoteDomain();
		record.setAccount(account);
		record.setTopicID(topicID);
		int result = voteService.findVoteInfoByAccountToday(record).size();
		if(result > 0){
			return "false";
		}
	    return "true";
	}
	

	/**
	 * @desc 返回今天的投票主题及选项（每日会放出一条投票主题进行投票）
	 * @return
	 */
	@ResponseBody
	@GetMapping("/todayTopicAndOptions")
	public String findTodayVoteTopicAndOptions(){
		String result ="{\"status\":\"success\",\"topic\":\"";
		List<TopicDomain> topicDomainList = topicService.selectTopicToday();
		if(topicDomainList == null || topicDomainList.size() == 0){
			return "{\"status\":\"failed\",\"info\":\"无投票话题!\"}";
		}
		TopicDomain topic = topicDomainList.get(0);
		result += topic.getName() + "\",\"topicid\":\"" + topic.getId() + "\",\"options\":[";
		List<TopicOptionDomain> topicOptionList = topicOptionService.findTopicOptionByID(topic.getId());
		if(topicOptionList == null || topicOptionList.size() == 0){
			return "{\"status\":\"failed\",\"info\":\"无投票话题选项!\"}";
		}
		
		for(TopicOptionDomain topicOption : topicOptionList){
			result += "{\"optionid\":\"" + topicOption.getId() + "\",\"topicid\":\"" + topicOption.getTopicid() 
				+ "\",\"optionkey\":\"" + topicOption.getOptionkey() + "\"},";
		}
		
		result = result.substring(0, result.length() - 1);
		result += "]}";
		return result;
	};
}
