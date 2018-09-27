package com.digitalchina.xa.it.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.LessonsDAO;
import com.digitalchina.xa.it.service.LessonsService;

@Service(value = "LessonsService")
public class LessonsServiceImpl implements LessonsService {
	@Autowired
	private LessonsDAO lessonDAO;

	@Override
	public List<Map<String, Object>> selectTop10() {
		return lessonDAO.selectTop10();
	}
}
