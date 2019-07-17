package com.bets.service.game;

import java.util.List;

import com.bets.entity.Page;
import com.bets.util.PageData;

/** 
 * 说明： 麻将用户查询接口
 * 创建人：bets
 * 创建时间：2016-09-18
 * @version
 */
public interface UserManager{

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
	
	/**
	 * 通过userId获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUserId(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	/**
	 * 查找麻将用户省份list
	 * @return
	 * @throws Exception
	 * 2016年9月20日 上午10:39:26
	 * List<PageData> listProvince
	 */
	public List<PageData> listProvince() throws Exception;
	
	/**
	 * app查询玩家列表
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findUserList(PageData pd)throws Exception;
	
	/**
	 * app查询玩家列表总记录数
	 * @return
	 * @throws Exception
	 */
	public PageData findTotalUser(PageData pd)throws Exception;
	
}

