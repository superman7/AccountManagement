package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.LessonDetailDAO;
import com.digitalchina.xa.it.dao.LessonsDAO;
import com.digitalchina.xa.it.service.LessonDetailService;

@Service(value = "LessonDetailService")
public class LessonDetailServiceImpl implements LessonDetailService {
	@Autowired
	private LessonDetailDAO lessonDetailDAO;

	@Override
	public Boolean insertItcode(String itcode) {
		if(itcode != null && itcode != "") {
			try {
				int effectedNumber = lessonDetailDAO.insertItcode(itcode);
				if(effectedNumber > 0) {
					return true;
				} else {
					throw new RuntimeException("插入itcode失败");
				}
			} catch(Exception e) {
				throw new RuntimeException("插入itcode失败 " + e.getMessage());
			}
		} else {
			throw new RuntimeException("插入 itcode不能为空");
		}
	}

	@Override
	public List<String> selectOrders(String lesson) {
		return lessonDetailDAO.selectOrders(lesson);
	}
}
