/*package com.digitalchina.xa.it.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digitalchina.xa.it.service.SigninRewardService;
import com.digitalchina.xa.it.util.HttpRequest;
import com.digitalchina.xa.it.util.TConfigUtils;

@Controller
@RequestMapping(value = "/signinReward")
public class SigninRewardController {
	@Autowired
	private SigninRewardService srService;
	
	@ResponseBody
	@GetMapping("/saveRandom")
	public Object saveSigninStatusRandom(
			@RequestParam(name = "itcode", required = true) String itcode){
		return srService.saveSigninInfo(itcode);
	}
	@ResponseBody
	@GetMapping("/saveConstant")
	public Object saveSigninStatusConstant(
			@RequestParam(name = "itcode", required = true) String itcode){
		return srService.saveSigninInfoConstant(itcode);
	}
	@ResponseBody
	@GetMapping("/signinStatus")
	public Object signinStatus(
			@RequestParam(name = "itcode", required = true) String itcode){
		return srService.checkSigninStatus(itcode);
	}
	
	@ResponseBody
	@GetMapping("/addLuckyNumber")
	public Map<String, Object> addLuckyNumber(
			@RequestParam(name = "param", required = true) String param){
		return srService.addLuckyNumber(param);
	}
	
	//NEWCODE START-泛微签到模块的奖励代码-START
	*//**
	 * @api {get} /signinReward/chargeToContract/:value 签到合约充值
	 * @apiVersion 0.0.1
	 *
	 * @apiName ChargeToSignInContract
	 * @apiGroup SignIn
	 *
	 * @apiParam {Integer} value 充值金额,正整数.
	 * 
	 *//*
	@ResponseBody
	@GetMapping("/chargeToContract/{value}")
	public void chargeToContract(@PathVariable String value) {
		//向kafka集群发送充值信息
		String ip = TConfigUtils.selectValueByKey("kafka_address");
		String url = ip + "/signin/chargeToContract";
		String postParam = "value=" + value;
		HttpRequest.sendGet(url, postParam);
	}
	
	*//**
	 * @api {get} /signinReward/signinReward/:itcode/:reward 发放签到奖励
	 * @apiVersion 0.0.1
	 *
	 * @apiName SignInReward
	 * @apiGroup SignIn
	 *
	 * @apiParam {String} itcode 奖励用户的itcode.
	 * @apiParam {Integer} reward 奖励金额,正整数.
	 *
	 * @apiSuccess {String} hashcode 发放签到奖励交易的hash值.
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     0x2f58a9a88392d9f5fcdfef155b1a361969d1e5ebf8862f5973be69146bb11348
	 *     
	 * @apiError SignInRewardFailed-ItcodeNotFound itcode未找到
	 * @apiErrorExample ItcodeNotFound-Response:
	 *     HTTP/1.1 200 OK
	 *     error.Can not find this itcode.
	 *     
	 * @apiError SignInRewardFailed-TransactionException 交易异常
	 * @apiErrorExample TransactionException-Response:
	 *     HTTP/1.1 200 OK
	 *     error.
	 *//*
	@ResponseBody
	@GetMapping("/signinReward/{itcode}/{reward}/{transactionDetailId}")
	public void signinReward(@PathVariable String itcode, @PathVariable int reward, @PathVariable String transactionDetailId) {
		//向kafka集群发送充值信息
		String ip = TConfigUtils.selectValueByKey("kafka_address");
		String url = ip + "/signin/signinReward";
		String postParam = "itcode=" + itcode + "&reward=" + reward + "&transactionDetailId=" + transactionDetailId;
		HttpRequest.sendGet(url, postParam);
	}
	
	*//**
	 * @api {get} /signinReward/voteReward/:itcode 发放投票奖励
	 * @apiVersion 0.0.1
	 *
	 * @apiName VoteReward
	 * @apiGroup Vote
	 *
	 * @apiParam {String} itcode 奖励用户的itcode.
	 *
	 * @apiSuccess {String} hashcode 发放投票奖励交易的hash值.
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     0x2f58a9a88392d9f5fcdfef155b1a361969d1e5ebf8862f5973be69146bb11348
	 *     
	 * @apiError VoteRewardFailed-ItcodeNotFound itcode未找到
	 * @apiErrorExample ItcodeNotFound-Response:
	 *     HTTP/1.1 200 OK
	 *     error.Can not find this itcode.
	 *     
	 * @apiError VoteRewardFailed-TransactionException 交易异常
	 * @apiErrorExample TransactionException-Response:
	 *     HTTP/1.1 200 OK
	 *     error.
	 *//*
	@ResponseBody
	@GetMapping("/voteReward/{itcode}/{reward}/{transactionDetailId}")
	public void voteReward(@PathVariable String itcode) {
		String voteRewardResult = srService.voteReward(itcode);
		if(!voteRewardResult.equals("error")){
			String[] voteRewardResultList = voteRewardResult.split(",");
			//向kafka集群发送充值信息
			String ip = TConfigUtils.selectValueByKey("kafka_address");
			String url = ip + "/signin/voteReward";
			String postParam = "itcode=" + itcode + "&reward=" + voteRewardResultList[0] + "&transactionDetailId=" + voteRewardResultList[1];
			HttpRequest.sendGet(url, postParam);
		}
	}
	//NEWCODE END-泛微签到模块的奖励代码-END
}
*/