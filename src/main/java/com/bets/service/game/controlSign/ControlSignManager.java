package com.bets.service.game.controlSign;

import java.util.List;
import com.bets.entity.Page;
import com.bets.util.PageData;

/** 
 * 说明： 开发测试接口
 * 创建人：bets
 * 创建时间：2016-12-31
 * @version
 */
public interface ControlSignManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**全部置为无效
	 * @param pd
	 * @throws Exception
	 */
	public void toinvalid(PageData pd)throws Exception;
	
	/**指定id置为无效
	 * @param pd
	 * @throws Exception
	 */
	public void updatevalid(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**通过玩家游戏ID获取数据
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findByUserId(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**总控列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> dict()throws Exception;
	
	/**更新状态
	 * @param pd
	 * @throws Exception
	 */
	public void updict(PageData pd)throws Exception;
	
}

