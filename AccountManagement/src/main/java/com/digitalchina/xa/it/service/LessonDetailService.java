package com.digitalchina.xa.it.service;

import com.digitalchina.xa.it.model.LessonDetailDomain;

public interface LessonDetailService {
	Boolean insertItcode(String itcode, String lesson);

	Integer selectOrderCount(String lesson);
	
	LessonDetailDomain selectOneRecord(String itcode, String lesson);
}
