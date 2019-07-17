package com.bets.service.game.cardStatistics;

import java.util.List;

import com.bets.entity.Page;
import com.bets.util.PageData;

public interface CardStatisticsManager {

	
	/**模糊查询今天的统计
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByToday(PageData pd)throws Exception;
	public PageData findByToday2(PageData pd)throws Exception;
	
	/**模糊查询昨天的统计
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByYesterday(PageData pd)throws Exception;
	public PageData findByYesterday2(PageData pd)throws Exception;
	
	/**模模糊查询昨天和今天的统计
	 * @param pd
	 * @throws Exception
	 */
	public PageData findBySum(PageData pd)throws Exception;
	public PageData findBySum2(PageData pd)throws Exception;
	
	
	/**1月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByJanuary(PageData pd)throws Exception;
	public PageData findByJanuary2(PageData pd)throws Exception;
	
	/**2月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByFebruary(PageData pd)throws Exception;
	public PageData findByFebruary2(PageData pd)throws Exception;
	
	/**3月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByMarch(PageData pd)throws Exception;
	public PageData findByMarch2(PageData pd)throws Exception;
	
	/**4月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByApril(PageData pd)throws Exception;
	public PageData findByApril2(PageData pd)throws Exception;
	
	/**5月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByMay(PageData pd)throws Exception;
	public PageData findByMay2(PageData pd)throws Exception;
	
	/**6月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByJune(PageData pd)throws Exception;
	public PageData findByJune2(PageData pd)throws Exception;
	
	/**7月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByJuly(PageData pd)throws Exception;
	public PageData findByJuly2(PageData pd)throws Exception;
	
	/**8月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByAugust(PageData pd)throws Exception;
	public PageData findByAugust2(PageData pd)throws Exception;
	
	/**9月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findBySeptember(PageData pd)throws Exception;
	public PageData findBySeptember2(PageData pd)throws Exception;
	
	/**10月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByOctober(PageData pd)throws Exception;
	public PageData findByOctober2(PageData pd)throws Exception;
	
	/**11月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByNovember(PageData pd)throws Exception;
	public PageData findByNovember2(PageData pd)throws Exception;
	
	/**12月
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByDecember(PageData pd)throws Exception;
	public PageData findByDecember2(PageData pd)throws Exception;
	public List<PageData> cardsalestatisticslistPage(Page page)throws Exception;
	public List<PageData> totalsalecards(Page page)throws Exception;
	
}
