package com.digitalchina.xa.it.dao;

import java.util.List;

import com.digitalchina.xa.it.model.UserDomain;

public interface UserDAO {
	int insert(UserDomain record);

    List<UserDomain> selectUsers();
    
    void updateBalance(UserDomain record);

	List<UserDomain> selectUserByItcode(String itcode);
}
