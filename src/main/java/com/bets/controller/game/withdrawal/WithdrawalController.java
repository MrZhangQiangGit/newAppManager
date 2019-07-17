package com.bets.controller.game.withdrawal;

import java.io.PrintWriter;
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
import com.bets.service.game.agent.AgentManager;
import com.bets.service.game.withdrawal.WithdrawalManager;
import com.bets.util.AppUtil;
import com.bets.util.Const;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Jurisdiction;
import com.bets.util.Tools;

import net.sf.json.JSONArray;

/** 
 * 说明：提现管理
 * 创建人：bets 
 * 创建时间：2017-05-20
 */
@Controller
@RequestMapping(value="/withdrawal")
public class WithdrawalController extends BaseController {
	
	String menuUrl = "withdrawal/list.do"; //菜单地址(权限用)
	@Resource(name="withdrawalService")
	private WithdrawalManager withdrawalService;
	@Resource(name="agentService")
	private AgentManager agentService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
//		logBefore(logger, Jurisdiction.getUsername()+"新增Withdrawal");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("IS_PAY",0 );//未付款
		pd.put("WITHDRAWAL_ID", this.get32UUID());	//主键
		pd.put("CREATOR", user.getNUMBER());	//创建人
		pd.put("CREATOR_TIME",new Date());	//创建时间
		withdrawalService.save(pd);
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
//		logBefore(logger, Jurisdiction.getUsername()+"删除Withdrawal");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		withdrawalService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
//		logBefore(logger, Jurisdiction.getUsername()+"修改Withdrawal");
//		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		withdrawalService.edit(pd);
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
		//logBefore(logger, Jurisdiction.getUsername()+"列表Withdrawal");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("loger",user.getNUMBER() );
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");	//开始时间
		String lastEnd = pd.getString("lastEnd");		//结束时间
		if(lastStart != null && lastEnd != null && lastStart.equals(lastEnd)){
			pd.put("tm", lastStart);
			pd.remove("lastStart");
			pd.remove("lastEnd");
		}else{
			if(lastStart != null && !"".equals(lastStart)){
				pd.put("lastStart", lastStart+" 00:00:00");
			}
			if(lastEnd != null && !"".equals(lastEnd)){
				pd.put("lastEnd", lastEnd+" 00:00:00");
			} 
		}
		
		page.setPd(pd);
		List<PageData>	varList = withdrawalService.list(page);	//列出Withdrawal列表
		pd.put("lastStart",lastStart );
		pd.put("lastEnd",lastEnd );
		mv.setViewName("game/withdrawal/withdrawal_list");
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
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		
		pd.put("AGENT_CODE", user.getNUMBER());
		PageData agent = agentService.findById(pd);
		pd.put("AGENT_NAME", agent.getString("AGENT_NAME"));
		mv.setViewName("game/withdrawal/withdrawal_edit");
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
		pd = withdrawalService.findById(pd);	//根据ID读取
		mv.setViewName("game/withdrawal/withdrawal_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Withdrawal");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			withdrawalService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Withdrawal到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("代理ID");	//1
		titles.add("代理名");	//2
		titles.add("提现金额");	//3
		titles.add("是否付款");	//4
		titles.add("创建人");	//5
		titles.add("创建时间");	//6
		dataMap.put("titles", titles);
		List<PageData> varOList = withdrawalService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("AGENT_CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("AGENT_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("MONEY"));	    //3
			vpd.put("var4", varOList.get(i).getString("IS_PAY"));	    //4
			vpd.put("var5", varOList.get(i).getString("CREATOR"));	    //5
			vpd.put("var6", varOList.get(i).getString("CREATOR_TIME"));	    //6
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
	 * 更新付款状态
	 * @param cardNo
	 * @throws Exception 
	 */
	@RequestMapping("/updatestatus")
	public void findObjectByCardNo(HttpServletResponse response,HttpServletRequest request) throws Exception {
		PageData pd = new PageData();
		pd=this.getPageData();
		
		PageData agent = agentService.findById(pd);
		String SURPLUS_MONEY= agent.getString("SURPLUS_MONEY");//余额
		double smoney = Double.parseDouble(SURPLUS_MONEY);
		String cash =pd.getString("CASH_MONEY");//提现金额
		double scash=Double.parseDouble(cash);
		if(smoney <scash){
			pd.put("msg","余额不足" );
			pd.put("IS_PAY",2 );
			withdrawalService.updatestatusnotenough(pd);
		}else{
			pd.put("IS_PAY",1 );
			withdrawalService.updatestatus(pd);
			pd.put("msg","已付款" );
		}
		response.setContentType("application/json");
	    response.setHeader("Pragma", "No-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setCharacterEncoding("UTF-8");
	    PrintWriter out= null;
	    out = response.getWriter();
	    JSONArray arry = JSONArray.fromObject(pd);
	    out.print(arry);
	    out.flush();
	    out.close();
	}
}
