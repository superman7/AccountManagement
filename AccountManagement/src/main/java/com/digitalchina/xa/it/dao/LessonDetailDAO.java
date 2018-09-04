package com.digitalchina.xa.it.dao;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.LessonDetailDomain;

public interface LessonDetailDAO {
	int insertItcode(@Param("itcode")String itcode, @Param("lesson")String lesson);
	
	int selectOrderCount(String lesson);
	
	LessonDetailDomain selectOneRecord(@Param("itcode")String itcode, @Param("lesson")String lesson);
}
