package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.PaidReadArticleDomain;

public interface PaidReadArticleDAO {
	
	int insertPaidReadArticle(PaidReadArticleDomain paidReadArticleDomain);
	
	List<PaidReadArticleDomain> selectPaidReadArticleByTime();
	
	List<PaidReadArticleDomain> selectPaidReadArticleByHot();
	
	List<PaidReadArticleDomain> selectMyArticles(@Param("id")String id);
	
	String selectArticleContent(@Param("id")Integer id);
}
