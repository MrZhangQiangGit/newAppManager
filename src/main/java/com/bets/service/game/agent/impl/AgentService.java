package com.bets.service.game.agent.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.util.PageData;
import com.bets.service.game.agent.AgentManager;

/** 
 * 说明： 代理商资料管理
 * 创建人：bets
 * 创建时间：2016-11-07
 * @version
 */
@Service("agentService")
public class AgentService implements AgentManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AgentMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AgentMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AgentMapper.edit", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit1(PageData pd)throws Exception{
		dao.update("AgentMapper.edit1", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit2(PageData pd)throws Exception{
		dao.update("AgentMapper.edit2", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit3(PageData pd)throws Exception{
		dao.update("AgentMapper.edit3", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AgentMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AgentMapper.listAll", pd);
	}
	
	/**列表(除自己外全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAlls(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AgentMapper.listAlls", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AgentMapper.findById", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByNumber(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AgentMapper.findByNumber", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByReId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AgentMapper.findByReId", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByReIds(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AgentMapper.findByReIds", pd);
	}
	
	/**通过user_id获取微信和名字 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findNameByUserId(String id)throws Exception{
		return (PageData)dao.findForObject("AgentMapper.findNameByUserId", id);
	}
	
	/**通过user_id获取剩余房卡数
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByUserId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AgentMapper.findByUserId", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AgentMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void editName(PageData pd)throws Exception{
		dao.update("AgentMapper.editName", pd);
	}
	
	public PageData findByEdit(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AgentMapper.findByEdit", pd);
	}

	/**查询邀请码是否重复
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findYqCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AgentMapper.findYqCode", pd);
	}

	@Override
	public PageData finduserlev(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AgentMapper.finduserlev",pd );
	}

	@Override
	public PageData getagentinfo(PageData pd) throws Exception {
		PageData agent = (PageData) dao.findForObject("AgentMapper.getagent", pd);
		PageData player = (PageData) dao.findForObject("AgentMapper.getplayer", pd);
		PageData num = new PageData();
		num.put("agent",agent.get("num") );
		num.put("player",player.get("num") );
		return num;
	}

	@Override
	public PageData getonlineuser(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AgentMapper.getonlineuser", pd);
	}

	@Override
	public List<PageData> getuserbyqycode(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("MajUserMapper.getuserbyqycode", pd);
	}

	@Override
	public PageData findagentbyqy(PageData pd) throws Exception {
		return (PageData) dao.findForObject("AgentMapper.findagentbyqy",pd );
	}

	@Override
	public void upgentyqcode(PageData pd) throws Exception {
		dao.update("AgentMapper.upgentyqcode", pd);
	}

	@Override
	public List<PageData> getagentbycreat(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("AgentMapper.getagentbycreat",pd );
	}
	
	/**
	 * 查询代理列表(分页)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findAgentList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("AgentMapper.findAgentList", pd);
	}
	/**
	 * 查询代理总数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findTotalAgent(PageData pd)throws Exception{
		return (PageData) dao.findForObject("AgentMapper.findTotalAgent", pd);
	}
	
	/**
	 * 修改系统用户密码
	 * @param pd
	 * @throws Exception
	 */
	public void editSysPwd(PageData pd)throws Exception{
		dao.update("AgentMapper.editSysPwd", pd);
	}
	
}

