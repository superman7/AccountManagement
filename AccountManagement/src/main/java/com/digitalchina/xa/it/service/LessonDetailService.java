package com.digitalchina.xa.it.service;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.LessonDetailDomain;

public interface LessonDetailService {
	Boolean insertItcode(String itcode, String lesson);

	Integer selectOrderCount(String lesson);
	
	//FIXME 暂时修改课程学习重复记录itcode的bug
	Integer selectLessonAndItcodeRecord(String itcode, Integer lessonid);
	
	Boolean updateChapterAndRecentTime(LessonDetailDomain lessonDetailDomain);
	
	String selectBackup1(@Param("itcode")String itcode, @Param("lessonid")Integer lessonid);
}
