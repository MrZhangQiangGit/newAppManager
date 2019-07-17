package com.bets.service.game;

import java.util.List;
import com.bets.entity.Page;
import com.bets.util.PageData;

/** 
 * 说明： 房间战绩表接口
 * 创建人：bets
 * 创建时间：2016-09-19
 * @version
 */
public interface FightResultManager{

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
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 查询玩家战绩记录列表(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findUserFightList(PageData pd)throws Exception;
	/**
	 * 查询玩家战绩记录总条数(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findUserFightTotal(PageData pd)throws Exception;
	
}

