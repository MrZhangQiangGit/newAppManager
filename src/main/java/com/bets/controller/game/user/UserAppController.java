package com.bets.controller.game.user;

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
import com.bets.service.game.UserManager;
import com.bets.service.game.agent.AgentManager;
import com.bets.util.AppUtil;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Tools;

import net.sf.json.JSONArray;

/** 
 * 说明：app用户
 * 创建人：bets 
 * 创建时间：2016-09-18
 */
@Controller
@RequestMapping(value="/userApp")
public class UserAppController extends BaseController {
	
	String menuUrl = "userApp/list.do"; //菜单地址(权限用)
	@Resource(name="majUserService")
	private UserManager userService;
	@Resource(name="agentService")
	private AgentManager agentService;
	
	/**
	 * 代理下属玩家列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/lowerUserList")
	public ModelAndView lowerUserList()throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();//获取sessionpd
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}else{
			pd.put("keywords", "");
		}
		/*String lastStart = pd.getString("lastStart");	//开始时间
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
				pd.remove("lastStart");
				pd.remove("lastEnd");
			}
		}*/
		
		//获取session,和登录用户信息
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		
		String number = user.getNUMBER();//登录人游戏id
		String name = user.getUSERNAME();//登录人用户名
		String cid = pd.getString("cid");
		if(cid != null && !"".equals(cid)){
			pd.put("number", cid);
		}else{
			pd.put("number", number);
		}
		pd.put("USERNAME", name);
		
		pd.put("total", (int)(long)userService.findTotalUser(pd).get("total"));//查询总记录数
		pd = paging(pd);//获取分页参数
		List<PageData> userList = userService.findUserList(pd);//查询分页
		pd.put("list", userList);//列表
		pd.put("AGENT_CODE", number);//代理id
		
		
		pd.put("stas", 2);
		pd.put("menuName", "玩家列表");
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/user/user_list");
		return mv;
	}
	
	/**
	 * 加载更多
	 * @param res
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(value="loadMoreUserList")
	public void loadMoreUserList(HttpServletResponse res,HttpServletRequest req)throws Exception{
		PageData pd = this.getPageData();
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}else{
			pd.put("keywords", "");
		}
		/*String lastStart = pd.getString("lastStart");	//开始时间
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
				pd.remove("lastStart");
				pd.remove("lastEnd");
			}
		}*/
		
		//获取session,和登录用户信息
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		
		String number = user.getNUMBER();//登录人游戏id
		String name = user.getUSERNAME();//登录人用户名
		String cid = pd.getString("cid");
		if(cid != null && !"".equals(cid)){
			pd.put("number", cid);
		}else{
			pd.put("number", number);
		}
		pd.put("USERNAME", name);
		
		pd.put("total", (int)(long)userService.findTotalUser(pd).get("total"));//查询总记录数
		pd = paging(pd);//获取分页参数
		List<PageData> userList = userService.findUserList(pd);//查询分页
		pd.put("list", userList);//列表
		pd.put("AGENT_CODE", number);//代理id
		
		res.setContentType("applocation/json");
		res.setHeader("Pragma", "no-cache");
		res.setHeader("cache-control", "no-cache");
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = res.getWriter();
		JSONArray jary = JSONArray.fromObject(pd);
		pw.print(jary);
		pw.flush();
		pw.close();
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除MajUser");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		userService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/editUser")
	public void editUser(HttpServletResponse res) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改MajUser");
		
		
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("OPT_TIME", Tools.date2Str(new Date()));	//操作时间
		userService.edit(pd);
		PageData p = agentService.findByEdit(pd);
		if(p != null){
			agentService.editName(pd);
		}
		res.setCharacterEncoding("utf-8");
		res.setContentType("appliaction/json");
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = res.getWriter();
		JSONArray jay = JSONArray.fromObject(pd);
		pw.print(jay);
		pw.flush();
		pw.close();
		
	}
	
	/**玩家列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表MajUser");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String id =pd.getString("AGENT_CODE");
		if(id !=null && !"".equals(id) ){
			pd.put("AGENT_CODE",id);//代理商id
		}else{
			pd.put("AGENT_CODE",user.getNUMBER() );//代理商id
		}
		
		PageData agentinfo = agentService.findById(pd);
		pd.put("YQ",agentinfo.getString("YQ_CODE") );
		String province = pd.getString("selOpt");
		String keywords = pd.getString("keywords");				//关键词检索条件
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
		if (null != province && !"".equals(province)) {
			pd.put("PROVINCE", province);
		}
		if(lastStart != null && lastEnd != null ){
			if(lastStart.equals(lastEnd)){
				pd.put("today",lastEnd );
				pd.remove("lastStart");
				pd.remove("lastEnd");
			}
		}
		page.setPd(pd);
		List<PageData>	varList = userService.list(page);	//列出MajUser列表
		List<PageData> listProvince =  userService.listProvince();
		pd.put("lastStart",lastStart);
		pd.put("lastEnd",lastEnd);
		mv.setViewName("game/majuser/majuser_list");
		mv.addObject("varList", varList);
		mv.addObject("listProvince", listProvince);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditUser")
	public ModelAndView goEditUser()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = userService.findById(pd);	//根据ID读取
		
		pd.put("stas", 2);
		pd.put("menuName", "修改玩家资料");
		mv.setViewName("newgame/user/user_edit_data");
		mv.addObject("msg", "editUser");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除MajUser");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			userService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
