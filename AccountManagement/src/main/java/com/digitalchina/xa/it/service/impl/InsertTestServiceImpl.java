package com.digitalchina.xa.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.InsertTestDAO;
import com.digitalchina.xa.it.model.InsertTestDomain;
import com.digitalchina.xa.it.service.InsertTestService;

@Service(value = "InsertTestService")
public class InsertTestServiceImpl implements InsertTestService {
	@Autowired
	private InsertTestDAO insertTestDAO;

	@Override
	public Boolean insertData(String dataStr, String timeStamp) {
		if(dataStr != null && timeStamp != null) {
			try {
				int effectedNumber = insertTestDAO.insertData(dataStr, timeStamp);
				if(effectedNumber > 0) {
					return true;
				} else {
					throw new RuntimeException("插入失败");
				}
			} catch(Exception e) {
				throw new RuntimeException("插入失败 " + e.getMessage());
			}
		} else {
			throw new RuntimeException("插入不能为空");
		}
	}
}
