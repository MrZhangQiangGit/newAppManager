package com.bets.controller.game.room;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.entity.system.User;
import com.bets.service.game.RoomManager;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;

import net.sf.json.JSONArray;

/** 
 * 说明：玩家消费查询
 * 创建人：bets 
 * 创建时间：2016-09-19
 */
@Controller
@RequestMapping(value="/roomApp")
public class RoomAppController extends BaseController {
	
	@Resource(name="roomService")
	private RoomManager roomService;
	
	/**
	 * 跳转到玩家消费记录页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userConsumeList")
	public ModelAndView agentRechargeList()throws Exception{
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
		
		pd.put("total", (int)(long)roomService.findUserConsumeTotal(pd).get("total"));//查询总记录数
		pd = paging(pd);//获取分页参数
		List<PageData> userList = roomService.findUserConsumeList(pd);//查询分页
		pd.put("list", userList);//列表
		pd.put("AGENT_CODE", number);//代理id
		
		pd.put("stas", 2);
		pd.put("menuName", "玩家消费记录");
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/user/user_consume_list");
		return mv;
	}
	
	/**
	 * 加载更多
	 * @param res
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(value="loadMoreUCList")
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
		
		pd.put("total", (int)(long)roomService.findUserConsumeTotal(pd).get("total"));//查询总记录数
		pd = paging(pd);//获取分页参数
		List<PageData> userList = roomService.findUserConsumeList(pd);//查询分页
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
	
	
}
