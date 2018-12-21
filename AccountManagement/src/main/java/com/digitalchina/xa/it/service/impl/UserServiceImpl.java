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

import com.digitalchina.xa.it.dao.UserDAO;
import com.digitalchina.xa.it.model.UserDomain;
import com.digitalchina.xa.it.service.UserService;
import com.digitalchina.xa.it.util.TConfigUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

	private volatile static Web3j web3j;
    @Autowired
    private UserDAO userDAO;//这里会报错，但是并不会影响

    @Override
    @Transactional
    public int addUser(UserDomain user) {
        return userDAO.insert(user);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public PageInfo<UserDomain> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<UserDomain> userDomains = userDAO.selectUsers();
        PageInfo<UserDomain> result = new PageInfo<UserDomain>(userDomains);
        return result;
    }

	@Override
    @Transactional
	public void updateBalance(String itcode, Double balance) {
		UserDomain ud = new UserDomain();
		ud.setItcode(itcode);
		ud.setBalance(balance);
		userDAO.updateBalance(ud);
	}

	@Override
	public void refreshBalance() {
		List<UserDomain> userDomains = userDAO.selectUsers();
		for(int i = 0; i < userDomains.size(); i++){
			if(web3j==null){
	            synchronized (UserService.class){
	                if(web3j==null){
	                    web3j =Web3j.build(new HttpService(TConfigUtils.selectIp()));
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
	}

	@Override
	public List<UserDomain> findUserByItcode(String itcode) {
		List<UserDomain> result = userDAO.selectUserByItcode(itcode);
		return result;
	}
}
