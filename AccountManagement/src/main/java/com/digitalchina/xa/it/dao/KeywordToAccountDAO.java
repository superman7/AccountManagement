package com.digitalchina.xa.it.dao;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.KeywordToAccountDomain;

public interface KeywordToAccountDAO {
	KeywordToAccountDomain selectByKeyword(@Param("keyword")String keyword);
	Integer insertKeyword(KeywordToAccountDomain keywordToAccountDomain);
}
