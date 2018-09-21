package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.LessonBuyDomain;

public interface LessonBuyDAO {
	List<String> selectUserPurchased(@Param("itcode")String itcode, @Param("lessonId")String lessonId);
	List<String> selectFreeChapter(@Param("lessonId")String lessonId);
	List<LessonBuyDomain> selectCanUse(@Param("itcode")String itcode, @Param("lessonId")String lessonId);
	LessonBuyDomain selectCostAndDiscount(@Param("chapterNum")String chapterNum, @Param("lessonId")String lessonId);
	int insertBuyInfo(LessonBuyDomain lessonBuyDomain);
}
