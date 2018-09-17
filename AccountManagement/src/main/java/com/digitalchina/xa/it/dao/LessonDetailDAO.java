package com.digitalchina.xa.it.dao;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.LessonDetailDomain;

public interface LessonDetailDAO {
	int insertItcode(@Param("itcode")String itcode, @Param("lesson")String lesson);
	
	int selectOrderCount(String lesson);
	
	LessonDetailDomain selectOneRecord(@Param("itcode")String itcode, @Param("lesson")String lesson);

	//FIXME 暂时修改课程学习重复记录itcode的bug
	Integer selectLessonAndItcodeRecord(@Param("itcode")String itcode, @Param("lessonid")Integer lessonid);
	
	int updateChapterAndRecentTime(LessonDetailDomain lessonDetailDomain);
	
	String selectChapter(@Param("itcode")String itcode, @Param("lessonid")Integer lessonid);
}
