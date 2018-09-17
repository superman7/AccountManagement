package com.digitalchina.xa.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.LessonDetailDAO;
import com.digitalchina.xa.it.model.LessonDetailDomain;
import com.digitalchina.xa.it.service.LessonDetailService;

@Service(value = "LessonDetailService")
public class LessonDetailServiceImpl implements LessonDetailService {
	@Autowired
	private LessonDetailDAO lessonDetailDAO;

	@Override
	public Boolean insertItcode(String itcode,String lesson) {
		if(itcode != null && itcode !="" && lesson != null && lesson !="") {
			try {
				int effectedNumber = lessonDetailDAO.insertItcode(itcode, lesson);
				if(effectedNumber > 0) {
					return true;
				} else {
					throw new RuntimeException("插入itcode，lesson失败");
				}
			} catch(Exception e) {
				throw new RuntimeException("插入itcode，lesson失败 " + e.getMessage());
			}
		} else {
			throw new RuntimeException("插入 itcode，lesson不能为空");
		}
	}

	@Override
	public Integer selectOrderCount(String lesson) {
		return lessonDetailDAO.selectOrderCount(lesson);
	}

	@Override
	public LessonDetailDomain selectOneRecord(String itcode, String lesson) {
		return lessonDetailDAO.selectOneRecord(itcode, lesson);
	}

	//FIXME 暂时修改课程学习重复记录itcode的bug
	@Override
	public Integer selectLessonAndItcodeRecord(String itcode, Integer lessonid) {
		// TODO Auto-generated method stub
		return lessonDetailDAO.selectLessonAndItcodeRecord(itcode, lessonid);
	}

	@Override
	public Boolean updateChapterAndRecentTime(LessonDetailDomain lessonDetailDomain) {
		if(lessonDetailDomain != null) {
			try {
				int effectedNumber = lessonDetailDAO.updateChapterAndRecentTime(lessonDetailDomain);
				if(effectedNumber > 0) {
					return true;
				} else {
					throw new RuntimeException("插入lessonDetailDomain失败");
				}
			} catch(Exception e) {
				throw new RuntimeException("插入lessonDetailDomain失败 " + e.getMessage());
			}
		} else {
			throw new RuntimeException("插入lessonDetailDomain不能为空");
		}
	}

	@Override
	public String selectChapter(String itcode, Integer lessonid) {
		return lessonDetailDAO.selectChapter(itcode, lessonid);
	}
}
