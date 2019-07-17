package com.bets.service.game.OnlineStatistics;

import com.bets.util.PageData;

public interface OnlineStatisticsManager {
	/**查询等待的统计
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByWait(PageData pd)throws Exception;
	
	/**查询游戏中的统计
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByProceed(PageData pd)throws Exception;
}
