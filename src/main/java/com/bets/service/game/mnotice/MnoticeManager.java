package com.bets.service.game.mnotice;

import java.util.List;
import com.bets.entity.Page;
import com.bets.util.PageData;

/** 
 * 说明： 公告管理接口
 * 创建人：bets
 * 创建时间：2016-11-05
 * @version
 */ 
public interface MnoticeManager{

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
	 * 查询公告只查询一条
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findOneNotice(PageData pd)throws Exception;
	
}

