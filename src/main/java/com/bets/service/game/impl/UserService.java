package com.bets.service.game.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.service.game.UserManager;
import com.bets.util.PageData;

/** 
 * 说明： 麻将用户查询
 * 创建人：bets
 * 创建时间：2016-09-18
 * @version
 */
@Service("majUserService")
public class UserService implements UserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MajUserMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MajUserMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("MajUserMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("MajUserMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MajUserMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MajUserMapper.findById", pd);
	}
	/**
	 * 通过userId获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUserId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MajUserMapper.findByUserId", pd);
	}
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("MajUserMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 查找省份list
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listProvince() throws Exception {
		return (List<PageData>) dao.findForList("MajUserMapper.findProvinceList","");
	}
	
	
	/**
	 * app查询玩家列表
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findUserList(PageData pd)throws Exception{
		return (List<PageData>) dao.findForList("MajUserMapper.findUserList", pd);
	}
	
	/**
	 * app查询玩家列表总记录数
	 * @return
	 * @throws Exception
	 */
	public PageData findTotalUser(PageData pd)throws Exception{
		return (PageData) dao.findForObject("MajUserMapper.findTotalUser", pd);
	}
	
}

