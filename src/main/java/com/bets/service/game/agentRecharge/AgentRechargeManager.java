package com.bets.service.game.agentRecharge;

import java.util.List;
import com.bets.entity.Page;
import com.bets.util.PageData;

/** 
 * 说明： 代理冲卡接口
 * 创建人：bets
 * 创建时间：2016-12-17
 * @version
 */
public interface AgentRechargeManager{

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
	
	/**列表(代理商)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findAgentByUser(PageData pd)throws Exception;
	
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
	
	//保存
	
	/**代理商
	 * @param pd
	 * @throws Exception
	 */
	public PageData findCardById(PageData pd)throws Exception;
	
	/**登陆人
	 * @param pd
	 * @throws Exception
	 */
	public PageData findCardById2(PageData pd)throws Exception;
	
	/**登陆人
	 * @param pd
	 * @throws Exception
	 */
	public PageData findMun(String id)throws Exception;
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit1(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit2(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit3(PageData pd)throws Exception;
	
	//补卡
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit4(PageData pd)throws Exception;
	/**充卡数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findchargeMun(String aid)throws Exception;
	/**
	 * 查询购卡记录
	 */
	public List<PageData> findbuycardlistPage(Page page)throws Exception;
	
	/**
	 * 查询代理充值记录列表(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findAgentRechargeList(PageData pd)throws Exception;
	/**
	 * 查询代理充值总记录数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findAgentRechargeTotal(PageData pd)throws Exception;
	
}

