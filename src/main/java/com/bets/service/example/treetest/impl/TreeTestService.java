package com.bets.service.example.treetest.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.entity.TreeTest;
import com.bets.service.example.treetest.TreeTestManager;
import com.bets.util.PageData;

/** 
 * 说明： 树形结构测试
 * 创建人：bets
 * 创建时间：2016-09-09
 * @version
 */
@Service("treetestService")
public class TreeTestService implements TreeTestManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TreeTestMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TreeTestMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TreeTestMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TreeTestMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TreeTestMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TreeTestMapper.findById", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<TreeTest> listByParentId(String parentId) throws Exception {
		return (List<TreeTest>) dao.findForList("TreeTestMapper.listByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<TreeTest> listTree(String parentId) throws Exception {
		List<TreeTest> valueList = this.listByParentId(parentId);
		for(TreeTest fhentity : valueList){
			fhentity.setTreeurl("treetest/list.do?TREETEST_ID="+fhentity.getTREETEST_ID());
			fhentity.setSubTreeTest(this.listTree(fhentity.getTREETEST_ID()));
			fhentity.setTarget("treeFrame");
		}
		return valueList;
	}
		
}

