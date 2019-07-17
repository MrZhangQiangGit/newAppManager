package com.bets.service.game.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.service.game.RoomManager;
import com.bets.util.PageData;

/** 
 * 说明： 麻将房间记录表
 * 创建人：bets
 * 创建时间：2016-09-19
 * @version
 */
@Service("roomService")
public class RoomService implements RoomManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("RoomMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("RoomMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("RoomMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RoomMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RoomMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RoomMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("RoomMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 查询玩家消费记录(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findUserConsumeList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("RoomMapper.findUserConsumeList", pd);
	}
	
	/**
	 * 查询玩家消费记录总数(新)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findUserConsumeTotal(PageData pd)throws Exception{
		return (PageData) dao.findForObject("RoomMapper.findUserConsumeTotal", pd);
	}
	
}

