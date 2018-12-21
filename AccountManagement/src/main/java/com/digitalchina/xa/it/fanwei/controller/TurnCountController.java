/*package com.digitalchina.xa.it.fanwei.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcone.eth.domain.TurnCountBean;
import com.dcone.eth.service.TurnCountService;


*//**
 * 转账业务的控制层
 * 
 * @author xueleic
 *
 *//*
@RestController
@Scope("prototype")
public class TurnCountController {

	@Autowired
	private TurnCountService turnCountService;
	
	*//**
	 * @api {get} /eth/findAll/:account 查询所有明细
	 * @apiVersion 0.0.1
	 *
	 * @apiName FindAllTransitionDetail
	 * @apiGroup Transition
	 *
	 * @apiParam {String} account 账户地址.
	 *
	 * @apiSuccess {Integer} id 交易ID
	 * @apiSuccess {String} fromcount 转出账户地址
	 * @apiSuccess {String} tocount 转入账户地址
	 * @apiSuccess {Double} value 转账金额
	 * @apiSuccess {Double} gas 交易手续费
	 * @apiSuccess {String} turndate 转账日期
	 * @apiSuccess {Integer} flag 交易状态(2为成功,null为交易被拒绝)
	 * @apiSuccess {String} remark 转账备注
	 * @apiSuccess {String} fromitcode 转出itcode
	 * @apiSuccess {String} toitcode 转入itcode
	 * @apiSuccess {String} turnhash 转账交易Hash值
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     [
	 *         {
	 *             "id": 42092, 
	 *             "fromcount": "0x8c735de7b8c388347b7443b492740a9c80df20a6", 
	 *             "tocount": "0x189abcd4cb82534d9d7b2ee181b28bcc86c64853", 
	 *             "value": 5, 
	 *             "gas": null, 
	 *             "turndate": "2018-09-04 16:14:03", 
	 *             "flag": 2, 
	 *             "remark": "签到随机奖励！", 
	 *             "fromitcode": "root", 
	 *             "toitcode": "fannl", 
	 *             "turnhash": "0xd30292ce945bdd71a14b1d4913f5aecf8585570faeca18c0d2918f1b590654d3", 
	 *         }, 
	 *         {
	 *             "id": 41447, 
	 *             "fromcount": "0x8c735de7b8c388347b7443b492740a9c80df20a6", 
	 *             "tocount": "0x189abcd4cb82534d9d7b2ee181b28bcc86c64853", 
	 *             "value": 1, 
	 *             "gas": null, 
	 *             "turndate": "2018-09-02 23:42:48", 
	 *             "flag": 2, 
	 *             "remark": "参与投票获得奖励！", 
	 *             "fromitcode": "root", 
	 *             "toitcode": "fannl", 
	 *             "turnhash": "0x16ea99cff72898e1ccf9e0771b3d2bfcc0e7801deb514c2335703d90bce10928", 
	 *         }
	 *     ]
	 *     
	 *//*
	@RequestMapping("/eth/findAll/{account}")
	public List<TurnCountBean> findAll(@PathVariable String account){
		List<TurnCountBean> list = turnCountService.findAll(account,account);
		return list;
	}
	
	*//**
	 * @api {get} /eth/findByFromcount/:fromcount 查询支出明细
	 * @apiVersion 0.0.1
	 *
	 * @apiName FindPayTransitionDetail
	 * @apiGroup Transition
	 *
	 * @apiParam {String} fromcount 账户地址.
	 *
	 * @apiSuccess {Integer} id 交易ID
	 * @apiSuccess {String} fromcount 转出账户地址
	 * @apiSuccess {String} tocount 转入账户地址
	 * @apiSuccess {Double} value 转账金额
	 * @apiSuccess {Double} gas 交易手续费
	 * @apiSuccess {String} turndate 转账日期
	 * @apiSuccess {Integer} flag 交易状态(2为成功,null为交易被拒绝)
	 * @apiSuccess {String} remark 转账备注
	 * @apiSuccess {String} fromitcode 转出itcode
	 * @apiSuccess {String} toitcode 转入itcode
	 * @apiSuccess {String} turnhash 转账交易Hash值
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     [
	 *         {
	 *             "id": 42092, 
	 *             "fromcount": "0x189abcd4cb82534d9d7b2ee181b28bcc86c64853", 
	 *             "tocount": "0x8c735de7b8c388347b7443b492740a9c80df20a6", 
	 *             "value": 5, 
	 *             "gas": null, 
	 *             "turndate": "2018-09-05 16:14:03", 
	 *             "flag": 2, 
	 *             "remark": "付费购买课程。", 
	 *             "fromitcode": "fannl", 
	 *             "toitcode": "root", 
	 *             "turnhash": "0xd30292ce945bdd71a14b1d4913f5aecf8585570faeca18c0d2918f1b590654d3", 
	 *         }
	 *     ]
	 *     
	 *//*
	@RequestMapping("/eth/findByFromcount/{fromcount}")
	public List<TurnCountBean> findByFromcount(@PathVariable String fromcount){
		List<TurnCountBean> list = turnCountService.findByFromcount(fromcount);
		return list;
	}
	
	*//**
	 * @api {get} /eth/findByTocount/:toaccount 查询收入明细
	 * @apiVersion 0.0.1
	 *
	 * @apiName FindIncomeTransitionDetail
	 * @apiGroup Transition
	 *
	 * @apiParam {String} toaccount 账户地址.
	 *
	 * @apiSuccess {Integer} id 交易ID
	 * @apiSuccess {String} fromcount 转出账户地址
	 * @apiSuccess {String} tocount 转入账户地址
	 * @apiSuccess {Double} value 转账金额
	 * @apiSuccess {Double} gas 交易手续费
	 * @apiSuccess {String} turndate 转账日期
	 * @apiSuccess {Integer} flag 交易状态(2为成功,null为交易被拒绝)
	 * @apiSuccess {String} remark 转账备注
	 * @apiSuccess {String} fromitcode 转出itcode
	 * @apiSuccess {String} toitcode 转入itcode
	 * @apiSuccess {String} turnhash 转账交易Hash值
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     [
	 *         {
	 *             "id": 42092, 
	 *             "fromcount": "0x8c735de7b8c388347b7443b492740a9c80df20a6", 
	 *             "tocount": "0x189abcd4cb82534d9d7b2ee181b28bcc86c64853", 
	 *             "value": 5, 
	 *             "gas": null, 
	 *             "turndate": "2018-09-04 16:14:03", 
	 *             "flag": 2, 
	 *             "remark": "签到随机奖励！", 
	 *             "fromitcode": "root", 
	 *             "toitcode": "fannl", 
	 *             "turnhash": "0xd30292ce945bdd71a14b1d4913f5aecf8585570faeca18c0d2918f1b590654d3", 
	 *         }
	 *     ]
	 *     
	 *//*
	@RequestMapping("/eth/findByTocount/{tocount}")
	public List<TurnCountBean> findByTocount(@PathVariable String tocount){
		List<TurnCountBean> list = turnCountService.findByTocount(tocount);
		return list;
	}

	*//**
	 * @api {get} /eth/updateTurnhash/:fromcount/:turnhash/:turndate 更新转账hash
	 * @apiVersion 0.0.1
	 *
	 * @apiName UpdateTransitionHash
	 * @apiGroup Transition
	 *
	 * @apiParam {String} fromcount 账户地址.
	 * @apiParam {String} turnhash 交易Hash值.
	 * @apiParam {String} turndate 交易时间.格式为"yyyy-MM-dd hh:mm:ss".
	 * 
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     
	 *//*
	@RequestMapping("/eth/updateTurnhash/{fromcount}/{turnhash}/{turndate}")
	public void updateTurnhash(@PathVariable String fromcount, @PathVariable String turnhash, @PathVariable String turndate){
		turnCountService.updateTurnhash(fromcount, turnhash, turndate);
	}
	
	*//**
	 * @api {get} /eth/findByTocount/:account 获取账户余额
	 * @apiVersion 0.0.1
	 *
	 * @apiName GetAccountBalance
	 * @apiGroup Transition
	 *
	 * @apiParam {String} account 账户地址.
	 *
	 * @apiSuccess {String} balance 账户神州币余额
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     43.25
	 *     
	 *//*
	 @RequestMapping("/eth/getBalance/{account}")
	 public  String getBalance(@PathVariable String account) {
		 String balance = turnCountService.getBalance(account);
		 return balance;
	 }
	 
	 *//**
	 * @api {get} /eth/transition/:msg 转账交易
	 * @apiVersion 0.0.1
	 *
	 * @apiName SendTransition
	 * @apiGroup Transition
	 *
	 * @apiParam {String} msg 加密后的交易信息,由fromcount-tocount-remark-money-fromitcode-toitcode格式加密.
	 * 
	 * @apiSuccess {String} turnhash 转账交易Hash值
	 * @apiSuccessExample Success-Response:
	 *     HTTP/1.1 200 OK
	 *     0x16ea99cff72898e1ccf9e0771b3d2bfcc0e7801deb514c2335703d90bce10928
	 *     
	 * @apiError TransitionFailed-TransactionException 交易异常
	 * @apiErrorExample TransactionException-Response:
	 *     HTTP/1.1 200 OK
	 *     error.
	 *//*
	 @RequestMapping("/eth/transition/{msg}")
	 public  String transition(@PathVariable String msg){
		 String hash = turnCountService.transition(msg);
		 return hash;
	 }
	 
 	 *//**
	  * @api {get} /eth/transition/counter/:itcode 查询交易笔数
	  * @apiVersion 0.0.1
	  *
	  * @apiName GetAccountTransitionCount
	  * @apiGroup Transition
	  *
	  * @apiParam {String} itcode 账户itcode.
	  *
	  * @apiSuccess {String} transitionCount 账户交易笔数
	  * @apiSuccessExample Success-Response:
	  *     HTTP/1.1 200 OK
	  *     13
	  *     
	  *//*
	 @RequestMapping("/eth/transition/counter/{itcode}")
	 public String todayTransfer(@PathVariable String itcode){
		 Integer result = turnCountService.selectUserTransferCounter(itcode);
		 return result.toString();
	 }
	 
	 *//**
	  * @api {get} /eth/transition/counter/:itcode 查询交易笔数
	  * @apiVersion 0.0.1
	  *
	  * @apiName GetAccountTransitionCount
	  * @apiGroup Transition
	  *
	  * @apiParam {String} itcode 账户itcode.
	  *
	  * @apiSuccess {String} transitionCount 账户交易笔数
	  * @apiSuccessExample Success-Response:
	  *     HTTP/1.1 200 OK
	  *     13
	  *     
	  *//*
	 @RequestMapping("/eth/attendance/top10")
	 public String attendanceTop10(){
		 String result = turnCountService.selectAttendanceTop10();
		 return result;
	 }
}
*/