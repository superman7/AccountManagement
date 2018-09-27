package com.digitalchina.xa.it.dao;

import java.util.List;
import java.util.Map;

public interface LessonsDAO {
	List<Map<String, Object>> selectTop10();
}
