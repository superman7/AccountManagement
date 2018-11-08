package com.digitalchina.xa.it.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.LessonBuyDomain;

public interface LessonBuyService {
	List<String> selectUserPurchased(String itcode, String lessonId);
	List<String> selectFreeChapter(@Param("itcode")String lessonId);
	List<LessonBuyDomain> selectCanUse(@Param("itcode")String itcode, @Param("lessonId")String lessonId);
	LessonBuyDomain selectCostAndDiscount(@Param("chapterNum")String chapterNum, @Param("lessonId")String lessonId);
	Integer insertBuyInfo(LessonBuyDomain lessonBuyDomain);
}
