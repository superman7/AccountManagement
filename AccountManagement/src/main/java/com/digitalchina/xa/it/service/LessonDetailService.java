package com.digitalchina.xa.it.service;

import com.digitalchina.xa.it.model.LessonDetailDomain;

public interface LessonDetailService {
	Boolean insertItcode(String itcode, String lesson);

	Integer selectOrderCount(String lesson);
	
	LessonDetailDomain selectOneRecord(String itcode, String lesson);

	//FIXME 暂时修改课程学习重复记录itcode的bug
	Integer selectLessonAndItcodeRecord(String itcode, Integer lessonid);
}
