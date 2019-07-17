package com.bets.service.game.withdrawal.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.bets.dao.DaoSupport;
import com.bets.entity.Page;
import com.bets.service.game.withdrawal.WithdrawalManager;
import com.bets.util.PageData;

/** 
 * 说明： 提现管理
 * 创建人：bets
 * 创建时间：2017-05-20
 * @version
 */
@Service("withdrawalService")
public class WithdrawalService implements WithdrawalManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("WithdrawalMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("WithdrawalMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("WithdrawalMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WithdrawalMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WithdrawalMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WithdrawalMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("WithdrawalMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void updatestatus(PageData pd) throws Exception {
		dao.update("WithdrawalMapper.updatestatus",pd );
		dao.update("WithdrawalMapper.updateagent",pd );
	}

	@Override
	public void updatestatusnotenough(PageData pd) throws Exception {
		dao.update("WithdrawalMapper.updatestatus",pd );
	}
	
}

