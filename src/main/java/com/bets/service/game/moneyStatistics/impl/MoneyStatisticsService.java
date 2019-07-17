package com.bets.service.game.moneyStatistics.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.service.game.moneyStatistics.MoneyStatisticsManager;
import com.bets.util.PageData;

@Service("moneystatisticsService")
public class MoneyStatisticsService implements MoneyStatisticsManager {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**模糊查询今天的统计
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByToday(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByToday", pd);
	}
	@Override
	public PageData findByToday2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByToday2", pd);
	}

	/**模糊查询昨天的统计
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findByYesterday(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByYesterday", pd);
	}
	@Override
	public PageData findByYesterday2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByYesterday2", pd);
	}

	/**模糊查询今天和昨天的统计
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findBySum(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findBySum", pd);
	}
	@Override
	public PageData findBySum2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findBySum2", pd);
	}

	/**
	 * 1月
	 */
	@Override
	public PageData findByJanuary(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByJanuary", pd);
	}
	@Override
	public PageData findByJanuary2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByJanuary2", pd);
	}

	/**
	 * 2月
	 */
	@Override
	public PageData findByFebruary(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByFebruary", pd);
	}
	@Override
	public PageData findByFebruary2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByFebruary2", pd);
	}

	/**
	 * 3月
	 */
	@Override
	public PageData findByMarch(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByMarch", pd);
	}
	@Override
	public PageData findByMarch2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByMarch2", pd);
	}

	/**
	 * 4月
	 */
	@Override
	public PageData findByApril(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByApril", pd);
	}
	@Override
	public PageData findByApril2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByApril2", pd);
	}

	/**
	 * 5月
	 */
	@Override
	public PageData findByMay(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByMay", pd);
	}
	@Override
	public PageData findByMay2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByMay2", pd);
	}

	/**
	 * 6月
	 */
	@Override
	public PageData findByJune(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByJune", pd);
	}
	@Override
	public PageData findByJune2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByJune2", pd);
	}

	/**
	 * 7月
	 */
	@Override
	public PageData findByJuly(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByJuly", pd);
	}
	@Override
	public PageData findByJuly2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByJuly2", pd);
	}

	/**
	 * 8月
	 */
	@Override
	public PageData findByAugust(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByAugust", pd);
	}
	@Override
	public PageData findByAugust2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByAugust2", pd);
	}

	/**
	 * 9月
	 */
	@Override
	public PageData findBySeptember(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findBySeptember", pd);
	}
	@Override
	public PageData findBySeptember2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findBySeptember2", pd);
	}

	/**
	 * 10月
	 */
	@Override
	public PageData findByOctober(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByOctober", pd);
	}
	@Override
	public PageData findByOctober2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByOctober2", pd);
	}

	/**
	 * 11月
	 */
	@Override
	public PageData findByNovember(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByNovember", pd);
	}
	@Override
	public PageData findByNovember2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByNovember2", pd);
	}

	/**
	 * 12月
	 */
	@Override
	public PageData findByDecember(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByDecember", pd);
	}
	@Override
	public PageData findByDecember2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("moneyStatisticsMapper.findByDecember2", pd);
	}
	@Override
	public PageData gettotalcharge(PageData pd) throws Exception {
		return (PageData) dao.findForObject("moneyStatisticsMapper.gettotalcharge",pd );
	}
	@Override
	public List<PageData> getonlinerechargelistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("moneyStatisticsMapper.getonlinerechargelistPage", page);
	}
	@Override
	public PageData getonlinerechargetotal(Page page) throws Exception {
		return (PageData) dao.findForObject("moneyStatisticsMapper.getonlinerechargetotal",page);
	}
	@Override
	public List<PageData> monthtotalmoneylistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("moneyStatisticsMapper.monthtotalmoneylistPage", page);
	}
	@Override
	public List<PageData> wxmonthtotalmoneye() throws Exception {
		return (List<PageData>) dao.findForList("moneyStatisticsMapper.wxmonthtotalmoneye","" );
	}

}
