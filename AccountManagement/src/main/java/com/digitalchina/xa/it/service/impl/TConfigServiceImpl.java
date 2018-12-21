package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.TConfigDAO;
import com.digitalchina.xa.it.model.TConfigDomain;
import com.digitalchina.xa.it.service.TConfigService;

@Service(value = "tConfigService")
public class TConfigServiceImpl implements TConfigService {
	@Autowired
    private TConfigDAO tConfigDAO;
	
	@Override
	public List<String> selectIpArr() {
		return tConfigDAO.selectIpArr();
	}

	@Override
	public Boolean UpdateEthNodesStatus(String cfgValue, Boolean cfgStatus) {
		if(cfgValue != null && cfgValue != "" && cfgStatus != null) {
			try {
				int effectedNumber = tConfigDAO.UpdateEthNodesStatus(cfgValue, cfgStatus);
				if(effectedNumber > 0) {
					return true;
				} else {
					throw new RuntimeException("更新以太坊节点状态失败");
				}
			} catch (Exception e) {
				throw new RuntimeException("更新以太坊节点状态失败" + e.getMessage());
			}
		} else {
			throw new RuntimeException("更新以太坊节点状态cfgValue,cfgStatus不能为空");
		}
	}

	@Override
	public String selectValueByKey(String cfgKey) {
		return tConfigDAO.selectValueByKey(cfgKey);
	}

	@Override
	public List<TConfigDomain> selectEthNodesInfo() {
		return tConfigDAO.selectEthNodesInfo();
	}

	@Override
	public List<TConfigDomain> selectContractInfo() {
		return tConfigDAO.selectContractInfo();
	}
}
