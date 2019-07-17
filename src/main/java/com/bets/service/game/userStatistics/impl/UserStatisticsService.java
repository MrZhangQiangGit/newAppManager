package com.bets.service.game.userStatistics.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bets.dao.DaoSupport;
import com.bets.service.game.userStatistics.UserStatisticsManager;
import com.bets.util.PageData;

@Service("userstatisticsService")
public class UserStatisticsService implements UserStatisticsManager {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**模糊查询今天的统计
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByToday(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByToday", pd);
	}

	/**模糊查询昨天的统计
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByYesterday(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByYesterday", pd);
	}

	/**模糊查询今天和昨天的统计
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findBySum(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findBySum", pd);
	}

	/**
	 * 1月
	 */
	@Override
	public PageData findByJanuary(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByJanuary", pd);
	}

	/**
	 * 2月
	 */
	@Override
	public PageData findByFebruary(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByFebruary", pd);
	}

	/**
	 * 3月
	 */
	@Override
	public PageData findByMarch(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByMarch", pd);
	}

	/**
	 * 4月
	 */
	@Override
	public PageData findByApril(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByApril", pd);
	}

	/**
	 * 5月
	 */
	@Override
	public PageData findByMay(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByMay", pd);
	}

	/**
	 * 6月
	 */
	@Override
	public PageData findByJune(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByJune", pd);
	}

	/**
	 * 7月
	 */
	@Override
	public PageData findByJuly(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByJuly", pd);
	}

	/**
	 * 8月
	 */
	@Override
	public PageData findByAugust(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByAugust", pd);
	}

	/**
	 * 9月
	 */
	@Override
	public PageData findBySeptember(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findBySeptember", pd);
	}

	/**
	 * 10月
	 */
	@Override
	public PageData findByOctober(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByOctober", pd);
	}

	/**
	 * 11月
	 */
	@Override
	public PageData findByNovember(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByNovember", pd);
	}

	/**
	 * 12月
	 */
	@Override
	public PageData findByDecember(PageData pd) throws Exception {
		return (PageData)dao.findForObject("userStatisticsMapper.findByDecember", pd);
	}
	
}
