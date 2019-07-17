package com.bets.controller.game.tranDetail;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.bets.controller.base.BaseController;
import com.bets.entity.Page;
import com.bets.entity.system.User;
import com.bets.util.AppUtil;
import com.bets.util.Const;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Jurisdiction;
import com.bets.service.game.tranDetail.TranDetailManager;

/** 
 * 说明：分润明细
 * 创建人：bets 
 * 创建时间：2017-02-09
 */
@Controller
@RequestMapping(value="/trandetail")
public class TranDetailController extends BaseController {
	
	String menuUrl = "trandetail/list.do"; //菜单地址(权限用)
	@Resource(name="trandetailService")
	private TranDetailManager trandetailService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TRAN_ID", this.get32UUID());	//主键
		trandetailService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		trandetailService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		trandetailService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**修改是否结算
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/editIsJiesuan")
	public void editIsJiesuan(HttpServletResponse response,HttpServletRequest request) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("IS_JIESUAN", "1");
		trandetailService.edit(pd);
		response.sendRedirect("list.do");
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("AGENT_USER_ID",user.getNUMBER() );//代理商id
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");	//开始时间
		String lastEnd = pd.getString("lastEnd");		//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 23:59:59");
		} 
		if(lastStart != null && lastEnd != null ){
			if(lastStart.equals(lastEnd)){
				pd.put("today",lastEnd );
				pd.remove("lastEnd");
				pd.remove("lastStart");
			}
		}
		page.setPd(pd);
		List<PageData>	varList = trandetailService.list(page);	//列出TranDetail列表
		List<PageData> list = new ArrayList<PageData>();
		for(PageData p : varList){
			String stu = p.getString("IS_JIESUAN");
			if("0".equals(stu)){
				p.put("IS_JIESUAN", "否");
			}else if("1".equals(stu)){
				p.put("IS_JIESUAN", "是");
			}
			list.add(p);
		}
		mv.setViewName("game/tranDetail/trandetail_list");
		pd.put("lastEnd",lastEnd );
		pd.put("lastStart",lastStart);
		mv.addObject("varList", list);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("game/tranDetail/trandetail_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = trandetailService.findById(pd);	//根据ID读取
		mv.setViewName("game/tranDetail/trandetail_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			trandetailService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("游戏ID");	//1
		titles.add("昵称");	//2
		titles.add("交易金额");	//3
		titles.add("交易内容");	//4
		titles.add("推荐代理ID");	//5
		titles.add("分润比例");	//6
		titles.add("分润金额");	//7
		titles.add("交易时间");	//8
		titles.add("是否结算");	//9
		titles.add("修改时间");	//10
		titles.add("结算人");	//11
		dataMap.put("titles", titles);
		List<PageData> varOList = trandetailService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("USER_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("USER_NICK_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("TRAN_MONEY"));	    //3
			vpd.put("var4", varOList.get(i).getString("TRAN_CONTENT"));	    //4
			vpd.put("var5", varOList.get(i).getString("AGENT_USER_ID"));	    //5
			vpd.put("var6", varOList.get(i).getString("AGENT_PROFIT_BL"));	    //6
			vpd.put("var7", varOList.get(i).getString("AGENT_PROFIT_MONEY"));	    //7
			vpd.put("var8", varOList.get(i).getString("CREATE_TIME"));	    //8
			vpd.put("var9", varOList.get(i).getString("IS_JIESUAN"));	    //9
			vpd.put("var10", varOList.get(i).getString("UPDATE_TIME"));	    //10
			vpd.put("var11", varOList.get(i).getString("UPDATOR"));	    //11
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/myorder")
	public ModelAndView myorder(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("USER_ID",user.getNUMBER() );//代理商id
		String lastStart = pd.getString("lastStart");	//开始时间
		String lastEnd = pd.getString("lastEnd");		//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 23:59:59");
		} 
		if(lastStart != null && lastEnd != null ){
			if(lastStart.equals(lastEnd)){
				pd.put("today",lastEnd );
				pd.remove("lastEnd");
				pd.remove("lastStart");
			}
		}
		
		page.setPd(pd);
		//分润金额
		pd.put("AGENT_ID",user.getNUMBER() );
		pd.put("PROFIT_TYPE",1 );
		PageData money = new PageData();
		PageData amoney = trandetailService.getprofitmoneybytype(pd);
		Double am=0.0;//间接分润
		Double um=0.0;//直接分润
		Double m1=0.0;//直接业绩
		Double m2=0.0;//间接业绩
		if(amoney !=null){
			am=(Double) amoney.get("money");
		}
		money.put("amoney",am );
		pd.put("PROFIT_TYPE",2 );
		PageData umoney = trandetailService.getprofitmoneybytype(pd);
		if(umoney !=null){
			um=(Double) umoney.get("money");
		}
		money.put("umoney",um);
		BigDecimal b1 = new BigDecimal(am);
		BigDecimal b2 = new BigDecimal(um);
		money.put("total", b1.add(b2).doubleValue());
		//交易金额
		pd.put("P_TYPE",1 );//查询类型 1 直接业绩 2 间接
		PageData money1 = trandetailService.getperformance(pd);
		if(money1 != null){
			m1=(Double) money1.get("money");
		}
		pd.put("P_TYPE", 2);
		PageData money2 = trandetailService.getperformance(pd);
		if(money2 != null){
			m2=(Double) money2.get("money");
		}
		BigDecimal mo1 = new BigDecimal(m1);
		BigDecimal mo2 = new BigDecimal(m2);
		money.put("mo1", mo1);
		money.put("mo2", mo2);
		money.put("ptotal", mo2.add(mo1).doubleValue());
		mv.setViewName("game/tranDetail/myperformance");
		pd.put("lastEnd",lastEnd );
		pd.put("lastStart",lastStart);
		mv.addObject("pd", pd);
		mv.addObject("money", money);
		return mv;
	}
	
	
	
}
