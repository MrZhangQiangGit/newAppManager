package com.bets.service.game.OnlineStatistics.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bets.dao.DaoSupport;
import com.bets.service.game.OnlineStatistics.OnlineStatisticsManager;
import com.bets.util.PageData;

@Service("onlinestatisticsService")
public class OnlineStatisticsService implements OnlineStatisticsManager {

	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//等待
	@Override
	public PageData findByWait(PageData pd) throws Exception {
		return (PageData)dao.findForObject("OnlineStatisticsMapper.findByWait", pd);
	}

	//进行
	@Override
	public PageData findByProceed(PageData pd) throws Exception {
		return (PageData)dao.findForObject("OnlineStatisticsMapper.findByProceed", pd);
	}

}
