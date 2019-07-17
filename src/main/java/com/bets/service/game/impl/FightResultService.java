package com.bets.service.game.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.service.game.FightResultManager;
import com.bets.util.PageData;

/** 
 * 说明： 房间战绩表
 * 创建人：bets
 * 创建时间：2016-09-19
 * @version
 */
@Service("fightresultService")
public class FightResultService implements FightResultManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("FightResultMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("FightResultMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("FightResultMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FightResultMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("FightResultMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("FightResultMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("FightResultMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 查询玩家战绩记录列表(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findUserFightList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("FightResultMapper.findUserFightList", pd);
	}
	/**
	 * 查询玩家战绩记录总条数(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findUserFightTotal(PageData pd)throws Exception{
		return (PageData) dao.findForObject("FightResultMapper.findUserFightTotal", pd);
	}
	
}

