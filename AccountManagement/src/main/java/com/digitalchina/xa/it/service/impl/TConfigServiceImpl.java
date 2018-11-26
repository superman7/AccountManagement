package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.TConfigDAO;
import com.digitalchina.xa.it.service.TConfigService;

@Service(value = "tConfigService")
public class TConfigServiceImpl implements TConfigService {
	@Autowired
    private TConfigDAO tConfigDAO;
	
	@Override
	public List<String> selectIpArr() {
		return tConfigDAO.selectIpArr();
	}
}
