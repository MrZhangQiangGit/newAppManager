package com.bets.service.game.controlSign.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.service.game.controlSign.ControlSignManager;
import com.bets.util.PageData;

/** 
 * 说明： 开发测试
 * 创建人：bets
 * 创建时间：2016-12-31
 * @version
 */
@Service("controlsignService")
public class ControlSignService implements ControlSignManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ControlSignMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ControlSignMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ControlSignMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ControlSignMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ControlSignMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ControlSignMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ControlSignMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**通过玩家游戏ID获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public List<PageData> findByUserId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("ControlSignMapper.findByUserId", pd);
	}
	
	//全部置为无效
	@Override
	public void toinvalid(PageData pd) throws Exception {
		dao.update("ControlSignMapper.toinvalid", pd);
		
	}
	
	//指定id置为无效
	@Override
	public void updatevalid(PageData pd) throws Exception {
		dao.update("ControlSignMapper.updatevalid", pd);
		
	}
	
	/**
	 * 总控列表
	 */
	@Override
	public List<PageData> dict() throws Exception {
		return (List<PageData>)dao.findForList("ControlSignMapper.dict", "");
	}
	
	/**
	 * 更新状态
	 */
	@Override
	public void updict(PageData pd) throws Exception {
		dao.update("ControlSignMapper.updict", pd);
		
	}
	
}

