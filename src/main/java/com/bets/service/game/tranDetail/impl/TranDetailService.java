package com.bets.service.game.tranDetail.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.util.PageData;
import com.bets.service.game.tranDetail.TranDetailManager;

/** 
 * 说明： 分润明细
 * 创建人：bets
 * 创建时间：2017-02-09
 * @version
 */
@Service("trandetailService")
public class TranDetailService implements TranDetailManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TranDetailMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TranDetailMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TranDetailMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TranDetailMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TranDetailMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TranDetailMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TranDetailMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public List<PageData> selectbyjs() throws Exception {
		return (List<PageData>) dao.findForList("TranDetailMapper.selectbyjs", "");
	}

	@Override
	public void addsettlement(PageData pd) throws Exception {
		dao.update("TranDetailMapper.addsettlement",pd );//插入结算记录
		dao.update("TranDetailMapper.edit", pd);//更改结算状态
		dao.update("TranDetailMapper.updateagentmoney", pd);//更新代理金额
		
	}
	public void addsettlement1(PageData pd,PageData upd) throws Exception {
		dao.update("TranDetailMapper.addsettlement",pd );//插入分润记录
		dao.update("TranDetailMapper.updateagentmoney", pd);//更新售卡总金额/剩余金额
		dao.update("TranDetailMapper.addsettlement",upd );
		dao.update("TranDetailMapper.updateagentmoney", upd);
		dao.update("TranDetailMapper.edit", pd);//更新结算状态
		
	}
	public void addsettlement2(PageData pd,PageData upd,PageData tpd) throws Exception {
		dao.update("TranDetailMapper.addsettlement",pd );
		dao.update("TranDetailMapper.updateagentmoney", pd);
		dao.update("TranDetailMapper.addsettlement",upd );
		dao.update("TranDetailMapper.updateagentmoney", upd);
		dao.update("TranDetailMapper.addsettlement",tpd );
		dao.update("TranDetailMapper.updateagentmoney", tpd);
		dao.update("TranDetailMapper.edit", pd);
		
	}

	@Override
	public void updatnoeagent() throws Exception {
		dao.update("TranDetailMapper.updatnoeagent", "");
		
	}

	@Override
	public void updatestatus(PageData pd) throws Exception {
		dao.update("SettlementMapper.updatestatus",pd );
	}

	@Override
	public PageData getprofitmoneybytype(PageData pd) throws Exception {
		return (PageData) dao.findForObject("TranDetailMapper.getprofitmoneybytype", pd);
	}

	@Override
	public PageData getperformance(PageData pd) throws Exception {
		return (PageData) dao.findForObject("TranDetailMapper.getperformance", pd);
	}
}

