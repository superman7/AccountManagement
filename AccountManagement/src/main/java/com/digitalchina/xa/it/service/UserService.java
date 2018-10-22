package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.UserDomain;
import com.github.pagehelper.PageInfo;

public interface UserService {

	int addUser(UserDomain user);

    PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);
	
    void updateBalance(String itcode, Double balance);
    
    void refreshBalance();
    
    List<UserDomain> findUserByItcode(String itcode);
}
