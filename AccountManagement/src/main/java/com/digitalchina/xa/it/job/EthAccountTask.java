package com.digitalchina.xa.it.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import com.digitalchina.xa.it.dao.CashBackDAO;
import com.digitalchina.xa.it.dao.EthAccountDAO;
import com.digitalchina.xa.it.dao.PaidVoteDetailDAO;
import com.digitalchina.xa.it.dao.TConfigDAO;
import com.digitalchina.xa.it.dao.TopicDAO;
import com.digitalchina.xa.it.dao.VirtualMachineDAO;
import com.digitalchina.xa.it.dao.WalletAccountDAO;
import com.digitalchina.xa.it.dao.WalletTransactionDAO;
import com.digitalchina.xa.it.model.EthAccountDomain;
import com.digitalchina.xa.it.model.TopicDomain;
import com.digitalchina.xa.it.model.VirtualMachineDomain;
import com.digitalchina.xa.it.model.WalletTransactionDomain;
import com.digitalchina.xa.it.service.EthAccountService;
import com.digitalchina.xa.it.service.TopicOptionService;
import com.digitalchina.xa.it.service.TopicService;
import com.digitalchina.xa.it.service.VoteService;
import com.digitalchina.xa.it.util.HttpRequest;
import com.digitalchina.xa.it.util.TConfigUtils;

@Component
public class EthAccountTask {
	@Autowired
    private EthAccountDAO ethAccountDAO;
	
	//每天09:30:30初始化新itcode
	@Transactional
	@Scheduled(cron="30 30 09 * * ?")
	public void initNewItcode(){
		//查询出需要更新的itcode
		List<EthAccountDomain> ethAccountDomains = ethAccountDAO.selectEthAccountWhereAvailableIs4();
		for(EthAccountDomain ead : ethAccountDomains){
			//FIXME 此处添加调用公共账户为该账户充值初始化的代码
			//充值成功后更新数据状态
			ethAccountDAO.updateDefaultAccountFrom4To3(ead);
		}
	}
}
