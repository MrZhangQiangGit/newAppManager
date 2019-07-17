package com.bets.service.game.recharge;

import java.util.List;
import com.bets.entity.Page;
import com.bets.util.PageData;

/** 
 * 说明： 玩家冲卡接口
 * 创建人：bets
 * 创建时间：2016-11-08
 * @version
 */
public interface RechargeManager{

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
	
	/**列表(用户名)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAlluser(PageData pd)throws Exception;
	
	/**通过id获取NUMBER
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByname(PageData pd)throws Exception;
	
	/**修改Agent
	 * @param pd
	 * @throws Exception
	 */
	public void editAgent(PageData pd)throws Exception;
	
	/**修改Agent
	 * @param pd
	 * @throws Exception
	 */
	public void editAgent2(PageData pd)throws Exception;
	/**修改Agent
	 * @param pd
	 * @throws Exception
	 */
	public void editAgent3(PageData pd)throws Exception;
	
	/**修改AppUser
	 * @param pd
	 * @throws Exception
	 */
	public void editAppUser(PageData pd)throws Exception;
	
	/**修改AppUser
	 * @param pd
	 * @throws Exception
	 */
	public void editAppUser2(PageData pd)throws Exception;
	
	/**查询Agent房卡和销售总数根据被充值ID
	 * @param pd
	 * @throws Exception
	 */
	public PageData findMunById(PageData pd)throws Exception;
	
	/**查询Agent房卡和销售总数根据充值人ID
	 * @param pd
	 * @throws Exception
	 */
	public PageData findMunById2(PageData pd)throws Exception;
	
	/**查询AppUser房卡根据ID
	 * @param pd
	 * @throws Exception
	 */
	public PageData findCardById(PageData pd)throws Exception;
	
	/**查询Agent房卡根据被充值ID
	 * @param pd
	 * @throws Exception
	 */
	public PageData findMun(String id)throws Exception;
	
	/**
	 * 查询玩家充值列表(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findUserRechargeList(PageData pd)throws Exception;
	
	/**
	 * 查询玩家充值记录数(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findUserRechargeTotal(PageData pd)throws Exception;
	
	
}

