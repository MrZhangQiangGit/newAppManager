package com.bets.service.game.userStatistics;

import com.bets.util.PageData;

public interface UserStatisticsManager {

	
	/**模糊查询今天登陆统计
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByToday(PageData pd)throws Exception;
	
	/**模糊查询昨天登陆统计
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByYesterday(PageData pd)throws Exception;
	
	/**模模糊查询昨天和今天的登陆统计
	 * @param pd
	 * @throws Exception
	 */
	public PageData findBySum(PageData pd)throws Exception;
	
	/**1月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByJanuary(PageData pd)throws Exception;
	
	/**2月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByFebruary(PageData pd)throws Exception;
	
	/**3月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByMarch(PageData pd)throws Exception;
	
	/**4月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByApril(PageData pd)throws Exception;
	
	/**5月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByMay(PageData pd)throws Exception;
	
	/**6月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByJune(PageData pd)throws Exception;
	
	/**7月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByJuly(PageData pd)throws Exception;
	
	/**8月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByAugust(PageData pd)throws Exception;
	
	/**9月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findBySeptember(PageData pd)throws Exception;
	
	/**10月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByOctober(PageData pd)throws Exception;
	
	/**11月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByNovember(PageData pd)throws Exception;
	
	/**12月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByDecember(PageData pd)throws Exception;
	
	
}
