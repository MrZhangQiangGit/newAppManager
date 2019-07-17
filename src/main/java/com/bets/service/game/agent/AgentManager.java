package com.bets.service.game.agent;

import java.util.List;
import com.bets.entity.Page;
import com.bets.util.PageData;

/** 
 * 说明： 代理商资料管理接口
 * 创建人：bets
 * 创建时间：2016-11-07
 * @version
 */
public interface AgentManager{

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
	
	/**列表(除自己外全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAlls(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByNumber(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByReId(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByReIds(PageData pd)throws Exception;
	
	/**通过user_id获取剩余房卡数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByUserId(PageData pd)throws Exception;
	
	/**通过user_id获取微信和名字 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findNameByUserId(String id)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**修改名字和微信号
	 * @param pd
	 * @throws Exception
	 */
	public void editName(PageData pd)throws Exception;
	
	/**列表是否存在
	 * @param page
	 * @throws Exception
	 */
	public PageData findByEdit(PageData pd)throws Exception;
	
	/**
	 * 查询邀请码是否重复
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findYqCode(PageData pd)throws Exception;
	public PageData finduserlev(PageData pd)throws Exception;
	public PageData getagentinfo(PageData pd)throws Exception;
	public PageData getonlineuser(PageData pd)throws Exception;
	
	public List<PageData> getuserbyqycode(PageData pd)throws Exception;
	
	public PageData findagentbyqy(PageData pd)throws Exception;
	
	public void upgentyqcode(PageData pd)throws Exception;
	
	public List<PageData> getagentbycreat(PageData pd)throws Exception;
	/**
	 * 查询代理列表(分页)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findAgentList(PageData pd)throws Exception;
	/**
	 * 查询代理总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findTotalAgent(PageData pd)throws Exception;
	
	/**
	 * 修改系统用户密码
	 * @param pd
	 * @throws Exception
	 */
	public void editSysPwd(PageData pd)throws Exception;
	
	
}

