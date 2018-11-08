package com.digitalchina.xa.it.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalchina.xa.it.dao.LessonBuyDAO;
import com.digitalchina.xa.it.model.LessonBuyDomain;
import com.digitalchina.xa.it.service.LessonBuyService;

@Service(value = "LessonBuyService")
public class LessonBuyServiceImpl implements LessonBuyService {
	@Autowired
	private LessonBuyDAO lessonBuyDAO;

	@Override
	public List<String> selectUserPurchased(String itcode, String lessonId) {
		return lessonBuyDAO.selectUserPurchased(itcode, lessonId);
	}

	@Override
	public List<String> selectFreeChapter(String lessonId) {
		return lessonBuyDAO.selectFreeChapter(lessonId);
	}

	@Override
	public List<LessonBuyDomain> selectCanUse(String itcode, String lessonId) {
		return lessonBuyDAO.selectCanUse(itcode, lessonId);
	}

	@Override
	public LessonBuyDomain selectCostAndDiscount(String chapterNum, String lessonId) {
		return lessonBuyDAO.selectCostAndDiscount(chapterNum, lessonId);
	}

	@Override
	public Integer insertBuyInfo(LessonBuyDomain lessonBuyDomain) {
		if(lessonBuyDomain != null) {
			try {
				Integer effectedNumber = lessonBuyDAO.insertBuyInfo(lessonBuyDomain);
				if(effectedNumber > 0) {
					return lessonBuyDomain.getId();
				} else {
					throw new RuntimeException("插入购买信息失败");
				}
			} catch(Exception e) {
				throw new RuntimeException("插入购买信息失败 " + e.getMessage());
			}
		} else {
			throw new RuntimeException("ethAccountDomain为null");
		}
	}
}
