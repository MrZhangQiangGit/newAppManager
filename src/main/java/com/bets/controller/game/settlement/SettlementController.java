package com.bets.controller.game.settlement;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.bets.controller.base.BaseController;
import com.bets.entity.Page;
import com.bets.entity.system.User;
import com.bets.service.game.agent.AgentManager;
import com.bets.service.game.moneyStatistics.MoneyStatisticsManager;
import com.bets.service.game.settlement.SettlementManager;
import com.bets.service.game.tranDetail.TranDetailManager;
import com.bets.util.AppUtil;
import com.bets.util.Const;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Jurisdiction;
import com.bets.util.Tools;
import com.bets.util.UuidUtil;

import net.sf.json.JSONArray;

/** 
 * 说明：结算管理
 * 创建人：bets 
 * 创建时间：2017-04-17
 */
@Controller
@RequestMapping(value="/settlement")
public class SettlementController extends BaseController {
	
	String menuUrl = "settlement/list.do"; //菜单地址(权限用)
	@Resource(name="settlementService")
	private SettlementManager settlementService;
	@Resource(name="trandetailService")
	private TranDetailManager trandetailService;
	@Resource(name="agentService")
	private AgentManager agentService;
	@Resource(name="moneystatisticsService")
	private MoneyStatisticsManager moneystatisticsService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Settlement");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ID", this.get32UUID());	//主键
		pd.put("CREATE_TIME", Tools.date2Str(new Date()));	//交易时间
		pd.put("UPDATE_TIME", Tools.date2Str(new Date()));	//结算时间
		pd.put("UPDATOR", "");	//结算人
		settlementService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Settlement");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		settlementService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Settlement");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		settlementService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Settlement");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
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
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String CREATOR=pd.getString("CREATOR");//分润代理商
		//String ctype = pd.getString("ctype");//分润类型
		if(CREATOR !=null && !"".equals(CREATOR) && !"888888".equals(CREATOR)){
			pd.put("CREATOR",CREATOR);
		}
		if("888888".equals(CREATOR)){
			pd.remove("ctype");
			pd.remove("CREATOR");
		}
		page.setPd(pd);
		List<PageData>	varList = settlementService.list(page);	//列出Settlement列表
		pd.put("lastEnd",lastEnd );
		pd.put("lastStart",lastStart);
		mv.setViewName("game/settlement/settlement_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
		mv.setViewName("game/settlement/settlement_edit");
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
		pd = settlementService.findById(pd);	//根据ID读取
		mv.setViewName("game/settlement/settlement_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Settlement");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			settlementService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Settlement到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("交易流水id");	//1
		titles.add("玩家游戏id");	//2
		titles.add("游戏昵称");	//3
		titles.add("交易金额");	//4
		titles.add("代理商id");	//5
		titles.add("分润比例");	//6
		titles.add("分润金额");	//7
		titles.add("交易内容");	//8
		titles.add("是否付款");	//9
		titles.add("交易时间");	//10
		titles.add("结算时间");	//11
		titles.add("结算人");	//12
		dataMap.put("titles", titles);
		List<PageData> varOList = settlementService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("TRAN_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("USER_ID"));	    //2
			vpd.put("var3", varOList.get(i).getString("USER_NICK_NAME"));	    //3
			vpd.put("var4", varOList.get(i).getString("TRAN_MONEY"));	    //4
			vpd.put("var5", varOList.get(i).getString("AGENT_ID"));	    //5
			vpd.put("var6", varOList.get(i).getString("AGENT_PROFIT_BL")+"%");	    //6
			vpd.put("var7", varOList.get(i).getString("AGENT_PROFIT_MONEY"));	    //7
			vpd.put("var8", varOList.get(i).getString("TRAN_CONTENT"));	    //8
			String fk="未付款";
			String status=varOList.get(i).getString("STATUS");
			if("1".equals("status")){
				fk="已付款";
			}
			vpd.put("var9",fk);	    //9
			vpd.put("var10",Tools.date2Str((Date) varOList.get(i).get("CREATE_TIME")) );	    //10
			vpd.put("var11", Tools.date2Str((Date) varOList.get(i).get("UPDATE_TIME")));	    //11
			vpd.put("var12", varOList.get(i).getString("UPDATOR"));	    //12
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
	
	/**
	 * 给代理商付款
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="updatejs")
	public void updatejs(HttpServletRequest request,HttpServletResponse response)throws Exception{
		PageData pd = new PageData();
		pd=this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("UPDATOR",user.getUSERNAME() );
		Date time = new Date();
		pd.put("UPDATE_TIME", time);
		pd.put("STATUS",1 );
		trandetailService.updatestatus(pd);
		response.setContentType("application/json");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        pd.put("time", Tools.date2Str(time) );
		JSONArray arry = JSONArray.fromObject(pd);
		out.print(arry);
        out.flush();
        out.close();
	}
	
	/**微信充值统计
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/onlinerechargetotal")
	public ModelAndView onlinerechargetotal(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Settlement");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
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
			if(lastEnd.equals(lastStart)){
				pd.put("today",lastStart );
				pd.remove("lastEnd");
				pd.remove("lastStart");
			}
		}
		page.setPd(pd);
		List<PageData> tradelist =moneystatisticsService.getonlinerechargelistPage(page);
		PageData totallist=moneystatisticsService.getonlinerechargetotal(page);
		if(totallist ==null){
			totallist.put("money", 0);
		}
		pd.put("lastEnd",lastEnd );
		pd.put("lastStart",lastStart );
		mv.setViewName("game/statisticswxsale");
		mv.addObject("tradelist", tradelist);
		mv.addObject("totallist", totallist);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
}
