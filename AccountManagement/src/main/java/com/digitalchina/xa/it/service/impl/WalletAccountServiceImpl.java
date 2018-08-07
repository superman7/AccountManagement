package com.digitalchina.xa.it.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.http.HttpService;

import com.digitalchina.xa.it.dao.WalletAccountDAO;
import com.digitalchina.xa.it.model.WalletAccountDomain;
import com.digitalchina.xa.it.service.WalletAccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "walletAccountService")
public class WalletAccountServiceImpl implements WalletAccountService {

	private volatile static Web3j web3j;
    private static String ip = "http://10.7.10.124:8545";
    private static String[] ipArr = {"http://10.7.10.124:8545","http://10.7.10.125:8545","http://10.0.5.217:8545","http://10.0.5.218:8545","http://10.0.5.219:8545"};
    
    @Autowired
    private WalletAccountDAO walletAccountDAO;//这里会报错，但是并不会影响

	@Override
	public PageInfo<WalletAccountDomain> findAllWalletAccount(int pageNum, int pageSize) {
		//将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<WalletAccountDomain> walletAccountDomains = walletAccountDAO.selectWalletAccount();
        PageInfo<WalletAccountDomain> result = new PageInfo<WalletAccountDomain>(walletAccountDomains);
        return result;
	}

	@Override
	public WalletAccountDomain findWalletAccount(String itcode) {
		return walletAccountDAO.selectWalletAccountByItcode(itcode);
	}

	@Override
	@Transactional
	public Boolean insertWalletAccount(WalletAccountDomain walletAccountDomain) {
		if(walletAccountDomain == null) {
			throw new RuntimeException("walletAccountDomain为null");
		}
		if(walletAccountDomain.getItcode() != null && walletAccountDomain.getItcode() != "") {
			try {
				int effectedNumber = walletAccountDAO.insertWalletAccount(walletAccountDomain);
				if(effectedNumber > 0) {
					return true;
				} else {
					throw new RuntimeException("插入信息失败");
				}
			} catch(Exception e) {
				throw new RuntimeException("插入信息失败 " + e.getMessage());
			}
		} else {
			throw new RuntimeException("插入用户itcode不能为空");
		}
	}

	/*@Override
	public void refreshBalance() {
		List<UserDomain> userDomains = userDAO.selectUsers();
		for(int i = 0; i < userDomains.size(); i++){
			if(web3j==null){
	            synchronized (UserService.class){
	                if(web3j==null){
	                    web3j =Web3j.build(new HttpService(ip));
	                }
	            }
	        }
	        EthGetBalance send = null;
			try {
				send = web3j.ethGetBalance(userDomains.get(i).getAccount(), DefaultBlockParameter.valueOf("latest")).send();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("获取账户余额时得到send对象时发生的异常为:"+e.toString());
			}
	        BigDecimal balance = new BigDecimal(send.getBalance().divide(new BigInteger("100000000000")).toString());
	        BigDecimal nbalance = balance.divide(new BigDecimal("100000"),2,BigDecimal.ROUND_DOWN);
	        updateBalance(userDomains.get(i).getItcode(), nbalance.doubleValue());
	        System.out.println(i + ":" + nbalance);
		}
	}*/
}
