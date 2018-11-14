package com.digitalchina.xa.it.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.PaidReadArticleDomain;
import com.github.pagehelper.PageInfo;

public interface PaidReadArticleService {
	//插入新的付费阅读数据
	int insertPaidReadArticle(PaidReadArticleDomain paidReadArticleDomain);
	//分页查询所有的付费阅读文章-按时间顺序排序
	List<PaidReadArticleDomain> selectPaidReadArticleByTime(int pageNum, int pageSize);
	//分页查询所有的付费阅读文章-按当前价值排序
	List<PaidReadArticleDomain> selectPaidReadArticleByHot(int pageNum, int pageSize);
	//根据itcode查询该用户上传的所有文章
	List<PaidReadArticleDomain> selectMyArticles(String id);
	//根据文章id查询该文章的内容（content字段）
	String selectArticleContent(@Param("id")Integer id);
	//更新某文章的阅读量
	Boolean updateReadingCapacity(@Param("id")Integer id);
}
