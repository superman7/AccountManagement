package com.digitalchina.xa.it.service;

import java.util.List;

public interface LessonDetailService {
	Boolean insertItcode(String itcode);
	
	List<String> selectOrderCount(String lesson);
}
