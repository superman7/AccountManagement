package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.LuckycashDomain;

public interface LuckycashService {
	int insert(LuckycashDomain record);

    List<LuckycashDomain> selectAvailableLuckyNum();
    
    int updateLuckyGuysCount(Integer id);
    
    int updateAvailable(Integer id);
}
