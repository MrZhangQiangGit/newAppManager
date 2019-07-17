package com.bets.controller.game.agentApp;

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

import org.apache.shiro.crypto.hash.SimpleHash;
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
 * 说明：代理app页面
 * 创建人：bets 
 * 创建时间：2016-11-07
 */
@Controller
@RequestMapping(value="/agentApp")
public class AgentAppController extends BaseController {
	
	//调用日志需按照该方式申明使用可用控制台输出
	private static  Logger log= LoggerFactory.getLogger(AgentAppController.class);
	
	String menuUrl = "agentApp/list.do"; //菜单地址(权限用)
	@Resource(name="agentService")
	private AgentManager agentService;
	@Resource(name="rechargeService")
	private RechargeManager rechargeService;
	@Resource(name="majUserService")
	private UserManager userService;
	
	/**
	 * 去我的信息页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goMe")
	public ModelAndView goMe()throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String num = user.getNUMBER();//获取用户id
		pd.put("AGENT_CODE", num);
		pd = agentService.findById(pd);
		pd.put("stas", 2);//底部按钮状态
		pd.put("menuName", "我的资料");
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/me/index");
		return mv;
	}
	/**
	 * 去修改密码页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditPwd")
	public ModelAndView goEditPwd()throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		pd.put("USERNAME", user.getUSERNAME());
		pd.put("PASSWORD", user.getPASSWORD());
		pd.put("stas", 2);
		pd.put("menuName", "修改密码");
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/me/edit_pwd");
		return mv ;
	}
	/**
	 * 修改密码
	 * @param res
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(value="/editPwd")
	public void editPwd(HttpServletResponse res,HttpServletRequest req)throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		String PASSWORD = pd.getString("rpwd");
		
		String msg = "no";
		//获取session
		Session session = Jurisdiction.getSession();
		User user = (User)session.getAttribute(Const.SESSION_USER);
		String USERNAME = user.getUSERNAME();
		String number = user.getNUMBER();
		String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();
		String rpassword = user.getPASSWORD();
		if(rpassword.equals(passwd)){
			msg = "ok";
			pd.put("PASSWORD", new SimpleHash("SHA-1", USERNAME, pd.getString("pwd")).toString());
			pd.put("NUMBER", number);
			agentService.editSysPwd(pd);
		}
		pd.put("msg", msg);
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "No-cache");
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/json");
		PrintWriter pw = res.getWriter();
		JSONArray json = JSONArray.fromObject(pd);
		pw.print(json);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 代理列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/agentList")
	public ModelAndView agentList(HttpServletRequest req)throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		int pageNumber = 8;//每页记录数
		int page;//页数
		int total;//获取总记录数
		int totalPage;//总页数
		int start;//分页起始值
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
//		String lastEnd = pd.getString("lastEnd");               //结束日期
//		if(null != lastEnd && !"".equals(lastEnd)){
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//			Date lastDate = sdf.parse(lastEnd);
//			lastEnd = sdf.format(new Date(lastDate.getTime() + 24 * 60 * 60 * 1000));
//			pd.put("lastEnd", lastEnd);
//		}
		String lastStart = pd.getString("lastStart");	//开始时间
		String lastEnd = pd.getString("lastEnd");		//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 23:59:59");
		} 
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if(lastStart != null && lastEnd != null ){
			if(lastStart.equals(lastEnd)){
				pd.put("today",lastEnd );
				pd.remove("lastStart");
				pd.remove("lastEnd");
			}
		}
		
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String number = user.getNUMBER();//游戏id
		String name = user.getUSERNAME();
		String cid = pd.getString("cid");
		if(cid != null && !"".equals(cid)){
			pd.put("number", cid);
		}else{
			pd.put("number", number);
		}
		pd.put("USERNAME", name);
		
		pd.put("sum", pageNumber);//分页记录数
		
		total = (int)(long)agentService.findTotalAgent(pd).get("total");//查询总记录数
		if(total%pageNumber==0) totalPage = total/pageNumber; else totalPage = total/pageNumber+1;
		
		String p = pd.getString("page");//获取页面传来的页数
		if(p==null || "".equals(p)) {
			page = 1;
		} else {
			page = Integer.parseInt(p);
			if(page<=0){//当页数为负数时跳转最大页
				page = totalPage;
			}
		}
		//分别判断页数是否大于最大页,最大页则跳转到1页,判断页数是否小于0.小于则跳转到最大页
		if(page>totalPage){start = 0;page = 1; }else start = (page-1)*pageNumber;
		pd.put("start", start);
		List<PageData> agentList = agentService.findAgentList(pd);
		pd.put("list", agentList);//列表
		pd.put("AGENT_CODE", number);//代理id
		pd.put("agent", agentService.findById(pd));//根据代理id查询登录人代理信息
		pd.put("page", page);//页数
		pd.put("total", total);//总记录数
		pd.put("totalPage", totalPage);//总页数
		
		pd.put("stas", 2);
		pd.put("menuName", "代理列表");
		
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/agent/agent_list");
		return mv;
	}
	
	/**
	 * 去修改代理资料页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditAgentData")
	public ModelAndView goEditAgentData()throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		
		PageData pa = agentService.findById(pd);
		pd.putAll(pa);
		
		pd.put("NUMBER", user.getNUMBER());
		pd.put("msg", "editAgentData");
		pd.put("stas", 2);
		pd.put("menuName", "代理修改资料");
		
//		for(Object a: pd.keySet()){
//			System.out.println("pd:"+a+":"+pd.get(a));
//		}
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/agent/agent_edit_data");
		return mv;
	}
	/**
	 * 修改代理信息
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/editAgentData")
	public void editAgentData(HttpServletResponse res)throws Exception{
		PageData pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("UPDATOR", user.getUSERNAME());	//修改人
		pd.put("UPDATOR_TIME", Tools.date2Str(new Date()));	//修改时间
		agentService.edit(pd);
		
	    res.setContentType("application/json");
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = res.getWriter();
		JSONArray jary = JSONArray.fromObject(pd);
		pw.print(jary);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 获取随机数
	 * @param n
	 * @return
	 */
	public String getRoomNum(int n){
		String seq = "";
		for(int i = 0;i<n;i++){
			int x=(int)(Math.random()*10);//0到9的随机数
			seq = seq + x;
		}
		return seq;
	}
	
	@RequestMapping(value="/goDemo")
	public ModelAndView goDemo()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("html/amazeHtml/demo.html");
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
	 * 登录获取初始信息
	 * @param response
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("/getMessage")
	public void getMessage(HttpServletResponse response,String id) throws Exception {
		PageData pd = new PageData();
//		Session session = Jurisdiction.getSession();
//		User user = (User) session.getAttribute(Const.SESSION_USER);
//		log.info("SessionUser:"+user);
//		if(user==null){
//			pd.put("SYSNAME","请登录!");
//		}
		
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME));
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
	
	
	public static void main(String[] agrs){
		int abc = 0;
		int b = 1;
		System.out.println(abc++);
		System.out.println(abc);
		System.out.println(++b);
		System.out.println(b);	
		System.err.println(agrs);	
		out:
		for(int i=0;i<100;i++){
			for(int j=0;j<100;j++){
				System.err.println(i+":"+j);
				if(j==0)
					break out;
			}
		}
	}
	
	@RequestMapping(value="/goDefault")
	public ModelAndView goDefault()throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/newsystem/index/default");
		return mv;
	}
}
