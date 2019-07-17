package com.bets.service.game.rankstatistics.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bets.dao.DaoSupport;
import com.bets.service.game.rankstatistics.RankStatisticsManager;
import com.bets.util.PageData;

@Service("rankStatisticsService")
public class RankStatisticsService implements RankStatisticsManager{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<PageData> getscore(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RankstatisticsMapper.getscore", pd);
	}

	@Override
	public PageData getresultinfo(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RankstatisticsMapper.getresultinfo",pd );
	}

	@Override
	public void deletetabledata() throws Exception {
		dao.update("RankstatisticsMapper.deletetabledata","" );
	}

	@Override
	public void insertrankdata(PageData pd) throws Exception {
		dao.update("RankstatisticsMapper.insertrankdata",pd );
	}
}
