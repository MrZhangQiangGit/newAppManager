package com.bets.service.game.cardStatistics.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.service.game.cardStatistics.CardStatisticsManager;
import com.bets.util.PageData;

@Service("cardstatisticsService")
public class CardStatisticsService implements CardStatisticsManager {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**模糊查询今天的统计
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByToday(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByToday", pd);
	}
	public PageData findByToday2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByToday2", pd);
	}

	/**模糊查询昨天的统计
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByYesterday(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByYesterday", pd);
	}
	@Override
	public PageData findByYesterday2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByYesterday2", pd);
	}

	/**模糊查询今天和昨天的统计
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findBySum(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findBySum", pd);
	}
	@Override
	public PageData findBySum2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findBySum2", pd);
	}

	/**
	 * 1月
	 */
	@Override
	public PageData findByJanuary(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByJanuary", pd);
	}
	@Override
	public PageData findByJanuary2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByJanuary2", pd);
	}

	/**
	 * 2月
	 */
	@Override
	public PageData findByFebruary(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByFebruary", pd);
	}
	@Override
	public PageData findByFebruary2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByFebruary2", pd);
	}

	/**
	 * 3月
	 */
	@Override
	public PageData findByMarch(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByMarch", pd);
	}
	@Override
	public PageData findByMarch2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByMarch2", pd);
	}

	/**
	 * 4月
	 */
	@Override
	public PageData findByApril(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByApril", pd);
	}
	@Override
	public PageData findByApril2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByApril2", pd);
	}

	/**
	 * 5月
	 */
	@Override
	public PageData findByMay(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByMay", pd);
	}
	@Override
	public PageData findByMay2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByMay2", pd);
	}

	/**
	 * 6月
	 */
	@Override
	public PageData findByJune(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByJune", pd);
	}
	@Override
	public PageData findByJune2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByJune2", pd);
	}

	/**
	 * 7月
	 */
	@Override
	public PageData findByJuly(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByJuly", pd);
	}
	@Override
	public PageData findByJuly2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByJuly2", pd);
	}

	/**
	 * 8月
	 */
	@Override
	public PageData findByAugust(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByAugust", pd);
	}
	@Override
	public PageData findByAugust2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByAugust2", pd);
	}

	/**
	 * 9月
	 */
	@Override
	public PageData findBySeptember(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findBySeptember", pd);
	}
	@Override
	public PageData findBySeptember2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findBySeptember2", pd);
	}

	/**
	 * 10月
	 */
	@Override
	public PageData findByOctober(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByOctober", pd);
	}
	@Override
	public PageData findByOctober2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByOctober2", pd);
	}

	/**
	 * 11月
	 */
	@Override
	public PageData findByNovember(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByNovember", pd);
	}
	@Override
	public PageData findByNovember2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByNovember2", pd);
	}

	/**
	 * 12月
	 */
	@Override
	public PageData findByDecember(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByDecember", pd);
	}
	@Override
	public PageData findByDecember2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("cardStatisticsMapper.findByDecember2", pd);
	}
	@Override
	public List<PageData> cardsalestatisticslistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("cardStatisticsMapper.cardsalestatisticslistPage", page);
	}
	@Override
	public List<PageData> totalsalecards(Page page) throws Exception {
		return (List<PageData>) dao.findForList("cardStatisticsMapper.totalsalecards", page);
	}

}
