package com.bets.service.game.rankstatistics;

import java.util.List;

import com.bets.util.PageData;

public interface RankStatisticsManager {
	/**
	 * 查询积分
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getscore(PageData pd)throws Exception;
	
	/**
	 * 查询胜负
	 * 
	 */
	public PageData getresultinfo(PageData pd)throws Exception;
	/**
	 * 清空数据
	 */
	public void deletetabledata()throws Exception;
	/**
	 * 插入数据
	 */
	public void insertrankdata(PageData pd)throws Exception;
	
}
