package com.digitalchina.xa.it.dao;

import java.util.List;

public interface LessonDetailDAO {
	int insertItcode(String itcode);
	
	List<String> selectOrders(String lesson);
}
