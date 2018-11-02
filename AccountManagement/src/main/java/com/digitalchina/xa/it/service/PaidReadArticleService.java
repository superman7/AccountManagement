package com.digitalchina.xa.it.service;

import java.util.List;

import com.digitalchina.xa.it.model.PaidReadArticleDomain;
import com.github.pagehelper.PageInfo;

public interface PaidReadArticleService {
	//插入新的付费阅读数据
	int insertPaidReadArticle(PaidReadArticleDomain paidReadArticleDomain);
	//分页查询所有的付费阅读文章-按时间顺序排序
	List<PaidReadArticleDomain> selectPaidReadArticleByTime(int pageNum, int pageSize);
	//分页查询所有的付费阅读文章-按当前价值排序
	PageInfo<PaidReadArticleDomain> selectPaidReadArticleByHot(int pageNum, int pageSize);
}
