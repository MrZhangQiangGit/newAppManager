package com.bets.controller.game.agent;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.bets.service.game.UserManager;
import com.bets.service.game.agent.AgentManager;
import com.bets.service.game.recharge.RechargeManager;
import com.bets.util.AppUtil;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Tools;

import net.sf.json.JSONArray;

/** 
 * 说明：代理商资料管理
 * 创建人：bets 
 * 创建时间：2016-11-07
 */
@Controller
@RequestMapping(value="/agent")
public class AgentController extends BaseController {
	
	//调用日志需按照该方式申明使用可用控制台输出
	private static  Logger logger= LoggerFactory.getLogger(AgentController.class);
	
	String menuUrl = "agent/list.do"; //菜单地址(权限用)
	@Resource(name="agentService")
	private AgentManager agentService;
	@Resource(name="rechargeService")
	private RechargeManager rechargeService;
	@Resource(name="majUserService")
	private UserManager userService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String num = getRoomNum();
//		pd.put("YqCode", num);
		//PageData n = agentService.findYqCode(pd);
//		while(true){
//			if(n==null){
//				pd.put("YQ_CODE", num);
//				break;
//			}else{
//				num = getRoomNum();
//				pd.put("YqCode", num);
//				n = agentService.findYqCode(pd);
//			}
//		}
		pd.put("YQ_CODE", pd.getString("AGENT_CODE"));
		PageData npd = new PageData();
		npd.put("P_ID", user.getNUMBER());
		PageData alv = agentService.finduserlev(pd);
		int userlv = Integer.parseInt(alv.getString("LEVEL"));//当前用户lv
		pd.put("LEVEL", userlv+1);//新增代理lv
		pd.put("CREATOR", user.getUSERNAME());	//创建用户名
		pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("CARD_NUM", 0);//剩余房卡数
		pd.put("SALE_TOTAL", "0");//销售总量
		pd.put("CASH_MONEY", "0");//提现金额
		pd.put("SURPLUS_MONEY", "0");//余额
		pd.put("TOTAL_MONEY", "0");//总金额
		pd.put("STATUS", "1");//默认 正常
		agentService.save(pd);
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
		agentService.delete(pd);
		out.write("success");
		out.close();
	}
	
	public String getRoomNum(){
		String seq = "";
		for(int i = 0;i<6;i++){
			int x=(int)(Math.random()*10);//0到9的随机数
			seq = seq + x;
		}
		return seq;
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
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("UPDATOR", user.getUSERNAME());	//修改人
		pd.put("UPDATOR_TIME", Tools.date2Str(new Date()));	//修改时间
		agentService.edit(pd);
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
		
		logger.info("方法list()开始");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastEnd = pd.getString("lastEnd");               //结束日期
		if(null != lastEnd && !"".equals(lastEnd)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date lastDate = sdf.parse(lastEnd);
			lastEnd = sdf.format(new Date(lastDate.getTime() + 24 * 60 * 60 * 1000));
			pd.put("lastEnd", lastEnd);
		}
		
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String number = user.getNUMBER();//游戏id
		String name = user.getUSERNAME();
		String cid = pd.getString("cid");
		if(cid != null && !"".equals(cid)){
			pd.put("id", cid);
		}else{
			pd.put("id", number);
		}
		pd.put("USERNAME", user.getUSERNAME());
//		PageData ps = rechargeService.findByname(pd);//登陆人游戏ID
//		String number = ps.getString("NUMBER");
//		pd.put("id", number);
		page.setPd(pd);
		List<PageData>	varList = agentService.list(page);	//列出Agent列表
		PageData nlv = new PageData();
		nlv.put("P_ID",number );
		PageData plv = agentService.finduserlev(nlv);
		mv.setViewName("game/agent/agent_list");
		mv.addObject("list", varList);
		mv.addObject("level", plv.getString("LEVEL"));
		mv.addObject("pd", pd);
		mv.addObject("number", number);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		logger.error(" list()方法假装出错! ");
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
		pd.put("P_ID", user.getNUMBER());	//修改人
		PageData us = agentService.finduserlev(pd);
		List<PageData> agnet = agentService.listAll(pd);
		JSONArray jsonagent = JSONArray.fromObject(agnet);
		mv.setViewName("game/agent/agent_add");
		mv.addObject("msg", "save");
		String lev = us.getString("LEVEL");
		mv.addObject("level",lev );
		mv.addObject("pd", pd);
		mv.addObject("agnet", agnet);
		mv.addObject("jsonagent", jsonagent);
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
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("P_ID", user.getNUMBER());	//修改人
		PageData us = agentService.finduserlev(pd);
		List<PageData> agnet = agentService.listAlls(pd);
		JSONArray jsonagent = JSONArray.fromObject(agnet);
		pd = agentService.findById(pd);	//根据ID读取
		String lev = us.getString("LEVEL");
		mv.addObject("level",lev );
		mv.setViewName("game/agent/agent_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		mv.addObject("agnet", agnet);
		mv.addObject("jsonagent", jsonagent);
		return mv;
	}	
	
	 /**打开平移用户
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/updateyq")
		public ModelAndView updateyq()throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			mv.setViewName("game/agent/agent_change");
			mv.addObject("pd", pd);
			return mv;
		}
		
		/**平移用户
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/edityq")
		public ModelAndView edityq() throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			agentService.upgentyqcode(pd);
			mv.addObject("msg","success");
			mv.setViewName("save_result");
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
			agentService.deleteAll(ArrayDATA_IDS);
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
		titles.add("代理商ID");	//1
		titles.add("代理商名称");	//2
		titles.add("性别");	//3
		titles.add("手机号");	//4
		titles.add("微信号");	//5
		titles.add("上级ID");	//6
		titles.add("推荐人ID");	//7
		titles.add("剩余房卡");	//8
		titles.add("售卡总量");	//9
		titles.add("创建人");	//10
		titles.add("创建时间");	//11
		titles.add("修改人");	//12
		titles.add("修改时间");	//13
		dataMap.put("titles", titles);
		List<PageData> varOList = agentService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("AGENT_CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("AGENT_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("SEX"));	    //3
			vpd.put("var4", varOList.get(i).getString("PHONE_NO"));	    //4
			vpd.put("var5", varOList.get(i).getString("WX_CODE"));	    //5
			vpd.put("var6", varOList.get(i).getString("P_ID"));	    //6
			vpd.put("var7", varOList.get(i).getString("RECOMMAND_ID"));	    //7
			vpd.put("var8", varOList.get(i).getString("CARD_NUM"));	    //8
			vpd.put("var9", varOList.get(i).getString("SALE_TOTAL"));	    //9
			vpd.put("var10", varOList.get(i).getString("CREATOR"));	    //10
			vpd.put("var11", Tools.date2Str((Date)varOList.get(i).get("CREATOR_TIME")));	//11
			vpd.put("var12", varOList.get(i).getString("UPDATOR"));	    //12
			if(varOList.get(i).get("UPDATOR_TIME")!=null){
				vpd.put("var13", Tools.date2Str((Date)varOList.get(i).get("UPDATOR_TIME")));//13
			}else{
				vpd.put("var13", "");
			}
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**
	 * 通过卡号查找客户姓名,微信
	 * @param cardNo
	 * @throws Exception 
	 */
	@RequestMapping("/findObjectByCardNo")
	public void findObjectByCardNo(HttpServletResponse response,String id) throws Exception {
		PageData pd = agentService.findNameByUserId(id);
		if (null!=pd && pd.size()>0) {
			 //设置页面不缓存
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
	
	/**
	 * 查询代理
	 * @param cardNo
	 * @throws Exception 
	 */
	@RequestMapping("/findbyid")
	public void findbyid(HttpServletResponse response,HttpServletRequest request) throws Exception {
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		PageData pd =new PageData();
		pd.put("USER_ID", user.getNUMBER());	//修改人
		PageData agent = agentService.findByEdit(pd);
		if (null!=agent && agent.size()>0) {
			pd= agent;
		}
		 //设置页面不缓存
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
	
	/**
	 * 校验代理ID
	 * @param cardNo
	 * @throws Exception 
	 */
	@RequestMapping("/checkagent")
	public void checkagent(HttpServletResponse response,HttpServletRequest request) throws Exception {
		PageData pd=new PageData();
		pd=this.getPageData();
	    response.setContentType("application/json");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out= null;
        out = response.getWriter();
		PageData agent = agentService.findById(pd);
		if (null !=agent && agent.size()>0) {
			pd=agent;
	        pd.put("msg","OK" );
		}else{
			 pd.put("msg","NO" );
		}
		JSONArray arry = JSONArray.fromObject(pd);
        out.print(arry);
        out.flush();
        out.close();
	}
	
	/**获取直属代理
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getownagent")
	public void getownagent(HttpServletResponse response,HttpServletRequest request) throws Exception {
		PageData pd=new PageData();
		pd=this.getPageData();
		pd.put("pages", Integer.parseInt(pd.getString("pages"))*10);
		List<PageData> agentlist=agentService.getagentbycreat(pd);//代理
	    response.setContentType("application/json");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out= null;
        out = response.getWriter();
		JSONArray arry = JSONArray.fromObject(agentlist);
        out.print(arry);
        out.flush();
        out.close();
	}
	
	/**获取直属玩家
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getownuser")
	public void getownuser(HttpServletResponse response,HttpServletRequest request) throws Exception {
		PageData pd=new PageData();
		pd=this.getPageData();
		pd.put("pages", Integer.parseInt(pd.getString("pages"))*10);
		List<PageData> userlist= agentService.getuserbyqycode(pd);//用户
	    response.setContentType("application/json");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out= null;
        out = response.getWriter();
		JSONArray arry = JSONArray.fromObject(userlist);
        out.print(arry);
        out.flush();
        out.close();
	}
	
	/**
	 * 根据邀请码查询所有代理
	 * @param cardNo
	 * @throws Exception 
	 */
	@RequestMapping("/findagentbyyq")
	public void findagentbyyq(HttpServletResponse response,HttpServletRequest request) throws Exception {
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		PageData pd =new PageData();
		pd=this.getPageData();
		pd.put("CREATOR", user.getUSERNAME());	//修改人
		PageData agent = agentService.findagentbyqy(pd);
		if(agent !=null && agent.size()>0){
			pd=agent;
			pd.put("msg", "OK");
		}else{
			pd.put("msg", "NO");
		}
		 //设置页面不缓存
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	/**打开用户/代理商页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/getlist")
	public ModelAndView getlist()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("game/agent/agentuser_list");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	
}
