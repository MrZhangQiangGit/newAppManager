package com.bets.service.game.recharge.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.util.PageData;
import com.bets.service.game.recharge.RechargeManager;

/** 
 * 说明： 玩家冲卡
 * 创建人：bets
 * 创建时间：2016-11-08
 * @version
 */
@Service("rechargeService")
public class RechargeService implements RechargeManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("RechargeMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("RechargeMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("RechargeMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RechargeMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RechargeMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RechargeMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("RechargeMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**列表(用户名)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAlluser(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RechargeMapper.listAlluser", pd);
	}
	
	/**通过id获取NUMBER
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByname(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RechargeMapper.findByname", pd);
	}
	
	/**修改Agent
	 * @param pd
	 * @throws Exception
	 */
	public void editAgent(PageData pd)throws Exception{
		dao.update("RechargeMapper.editAgent", pd);
	}
	
	/**修改Agent
	 * @param pd
	 * @throws Exception
	 */
	public void editAgent2(PageData pd)throws Exception{
		dao.update("RechargeMapper.editAgent2", pd);
	}
	
	/**修改Agent
	 * @param pd
	 * @throws Exception
	 */
	public void editAgent3(PageData pd)throws Exception{
		dao.update("RechargeMapper.editAgent3", pd);
	}
	
	/**修改AppUser
	 * @param pd
	 * @throws Exception
	 */
	public void editAppUser(PageData pd)throws Exception{
		dao.update("RechargeMapper.editAppUser", pd);
	}
	
	/**修改AppUser
	 * @param pd
	 * @throws Exception
	 */
	public void editAppUser2(PageData pd)throws Exception{
		dao.update("RechargeMapper.editAppUser2", pd);
	}
	
	/**查询Agent房卡和销售总数根据被充值ID
	 * @param pd
	 * @throws Exception
	 */
	public PageData findMunById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RechargeMapper.findMunById", pd);
	}
	
	/**查询Agent房卡和销售总数根据充值人ID
	 * @param pd
	 * @throws Exception
	 */
	public PageData findMunById2(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RechargeMapper.findMunById2", pd);
	}
	
	/**查询AppUser房卡根据ID
	 * @param pd
	 * @throws Exception
	 */
	public PageData findCardById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RechargeMapper.findCardById", pd);
	}
	
	/**查询Agent房卡根据被充值ID
	 * @param pd
	 * @throws Exception
	 */
	public PageData findMun(String id)throws Exception{
		return (PageData)dao.findForObject("RechargeMapper.findMun", id);
	}
	
	/**
	 * 查询玩家充值列表(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findUserRechargeList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("RechargeMapper.findUserRechargeList", pd);
	}
	
	/**
	 * 查询玩家充值记录数(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findUserRechargeTotal(PageData pd)throws Exception{
		return (PageData) dao.findForObject("RechargeMapper.findUserRechargeTotal", pd);
	}
	
}

