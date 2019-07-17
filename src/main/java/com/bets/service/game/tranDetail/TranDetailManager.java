package com.bets.service.game.tranDetail;

import java.util.List;
import com.bets.entity.Page;
import com.bets.util.PageData;

/** 
 * 说明： 分润明细接口
 * 创建人：bets
 * 创建时间：2017-02-09
 * @version
 */
public interface TranDetailManager{

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
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> selectbyjs()throws Exception;
	/**
	 * 插入结算表-一级代理
	 */
	public void addsettlement(PageData pd) throws Exception;
	/**
	 * 插入结算表-二级代理
	 */
	public void addsettlement1(PageData pd,PageData upd) throws Exception;
	/**
	 * 插入结算表-三级代理
	 */
	public void addsettlement2(PageData pd,PageData upd,PageData tpd) throws Exception;
	/**
	 * 更改代理商为空的记录
	 */
	public void updatnoeagent()throws Exception;
	/**
	 * 更新给代理商付款状态
	 */
	public void updatestatus(PageData pd)throws Exception;
	/**
	 * 根据分润类型获取代理分润金额
	 */
	public PageData getprofitmoneybytype(PageData pd)throws Exception;
	/**
	 * 查询线上业绩
	 */
	public PageData getperformance(PageData pd)throws Exception;
}

