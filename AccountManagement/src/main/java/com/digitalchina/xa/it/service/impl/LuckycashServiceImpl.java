package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.digitalchina.xa.it.dao.LuckycashDAO;
import com.digitalchina.xa.it.model.LuckycashDomain;
import com.digitalchina.xa.it.service.LuckycashService;

/**
 * Created by Administrator on 2017/8/16.
 */
@Service(value = "luckycashService")
public class LuckycashServiceImpl implements LuckycashService {

    @Autowired
    private LuckycashDAO luckycashDAO;

	@Override
    @Transactional
	public int insert(LuckycashDomain record) {
		return luckycashDAO.insert(record);
	}

	@Override
	public List<LuckycashDomain> selectAvailableLuckyNum() {
		return luckycashDAO.selectAvailableLuckyNum();
	}

	@Override
	public int updateLuckyGuysCount(Integer id) {
		return luckycashDAO.updateLuckyGuysCount(id);
	}

	@Override
	public int updateAvailable(Integer id) {
		return luckycashDAO.updateAvailable(id);
	}
}
