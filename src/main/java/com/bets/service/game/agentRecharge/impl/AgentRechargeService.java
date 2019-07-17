package com.bets.service.game.agentRecharge.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.util.PageData;
import com.bets.service.game.agentRecharge.AgentRechargeManager;

/** 
 * 说明： 代理冲卡
 * 创建人：bets
 * 创建时间：2016-12-17
 * @version
 */
@Service("agentrechargeService")
public class AgentRechargeService implements AgentRechargeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AgentRechargeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AgentRechargeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AgentRechargeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AgentRechargeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AgentRechargeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AgentRechargeMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AgentRechargeMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PageData> findAgentByUser(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("AgentRechargeMapper.findAgentByUser", pd);
	}

	//保存
	
	@Override
	public PageData findCardById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AgentRechargeMapper.findCardById", pd);
	}

	@Override
	public PageData findCardById2(PageData pd) throws Exception {
		return (PageData)dao.findForObject("AgentRechargeMapper.findCardById2", pd);
	}
	
	@Override
	public void edit1(PageData pd) throws Exception {
		dao.update("AgentRechargeMapper.edit1", pd);
	}

	@Override
	public void edit2(PageData pd) throws Exception {
		dao.update("AgentRechargeMapper.edit2", pd);
	}

	@Override
	public void edit3(PageData pd) throws Exception {
		dao.update("AgentRechargeMapper.edit3", pd);
	}

	@Override
	public PageData findMun(String id) throws Exception {
		return (PageData)dao.findForObject("AgentRechargeMapper.findMun", id);
	}
	
	//补卡
	@Override
	public void edit4(PageData pd) throws Exception {
		dao.update("AgentRechargeMapper.edit4", pd);
	}
	@Override
	public PageData findchargeMun(String aid) throws Exception {
		return (PageData)dao.findForObject("AgentRechargeMapper.findchargeMun", aid);
	}

	@Override
	public List<PageData> findbuycardlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("AgentRechargeMapper.findbuycardlistPage", page);
	}
	
	/**
	 * 查询代理充值记录列表(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findAgentRechargeList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("AgentRechargeMapper.findAgentRechargeList", pd);
	}
	/**
	 * 查询代理充值总记录数
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findAgentRechargeTotal(PageData pd)throws Exception{
		return (PageData) dao.findForObject("AgentRechargeMapper.findAgentRechargeTotal", pd);
	}
	
}

