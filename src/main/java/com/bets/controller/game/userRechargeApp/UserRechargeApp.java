package com.bets.controller.game.userRechargeApp;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.entity.system.User;
import com.bets.service.game.UserManager;
import com.bets.service.game.agent.AgentManager;
import com.bets.service.game.recharge.RechargeManager;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;
import com.bets.util.Tools;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/userRechargeApp")
public class UserRechargeApp extends BaseController{
	private static Logger logger = LoggerFactory.getLogger(UserRechargeApp.class);
	
	@Resource(name="rechargeService")
	private RechargeManager rechargeService;
	@Resource(name="majUserService")
	private UserManager userService;
	@Resource(name="agentService")
	private AgentManager agentService;
	
	/**
	 * 跳转到玩家充值页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUserRecharge")
	public ModelAndView goUserRecharge()throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		
		PageData pUser = userService.findByUserId(pd);
		if(pUser != null){
			pd = pUser;
		}
		
		pd.put("msg", "userRecharge");
		pd.put("stas", 2);
		pd.put("menuName", "玩家充值");
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/user/user_recharge");
		return mv;
	}
	
	/**
	 * 玩家充值
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/userRecharge")
	public void userRechage(HttpServletResponse res)throws Exception{
		PageData pd = this.getPageData();
		
		//业务逻辑
		
		
		//登陆人游戏ID
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
//		pd.put("USERNAME", user.getUSERNAME());
//		PageData p = rechargeService.findByname(pd);//查询sys_user
//		String number = p.getString("NUMBER");
		
		String number = user.getNUMBER();
		
		//页面传过来的值
		String gross1 = pd.getString("CHARGE_NUM");//充值房卡数量
		int gross = Integer.parseInt(gross1);//转换为Int
		
		/*
		 * 检查代理房卡是否充足
		 */
		String msg = "ok";//充值信息,ok为充值成功,no,为房卡不足
		
		
		//查询玩家中数据库剩余房卡根据被充值的ID
		PageData pasa = rechargeService.findCardById(pd);
		int surplusCard = (int)pasa.get("ROOM_CARD");//玩家剩余的房卡 pasa
		
		int roomCardSub =0;//代理房卡
		int totalAdd = 0;//代理售卡
		
		//查询代理表中登陆人数据库房卡数和销售总量根据充值人的id
		pd.put("agent_code", number);//充值人的ID
		PageData pas = rechargeService.findMunById2(pd);//查询m_agent充值代理房卡和售卡
		if(pas != null){
			String roomCard2 = pas.getString("CARD_NUM");//代理表中的房卡数
			if(null!=roomCard2&&roomCard2.length()>0){
				roomCardSub = Integer.parseInt(roomCard2);//转换为Int  房卡减
			}
			String total2 = pas.getString("SALE_TOTAL");//代理表中的售卡总数
			if(null!=total2&&total2.length()>0){
				totalAdd = Integer.parseInt(total2);//转换为Int   总量加
			}
		}
	
		if("admin".equals(user.getUSERNAME())){//登陆为admin
			String sum = (surplusCard+gross)+"";//房卡和（玩家）
			pd.put("ROOM_CARD", sum);
			rechargeService.editAppUser(pd);//更新玩家房卡
			String totalSum = (totalAdd+gross)+"";//销售总量和（代理商）
			pd.put("sale_total", totalSum);
			rechargeService.editAgent3(pd);//更新代理售卡
			pd.put("CREATOR", user.getUSERNAME());	//创建用户名
			pd.put("CHARGE_NAME", user.getNAME());
			pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
			pd.put("RECHARGE_ID", this.get32UUID());	//主键
			rechargeService.save(pd);//保存充值记录
		}else{//不是admin登陆
			if(roomCardSub>=gross){
				String sum = (surplusCard+gross)+"";//房卡和（玩家）
				pd.put("ROOM_CARD", sum);
				rechargeService.editAppUser(pd);//更新玩家房卡
				String bad = (roomCardSub-gross)+"";  //房卡差（代理商）
				pd.put("card_num", bad);
				String totalSum = (totalAdd+gross)+"";//销售总量和（代理商）
				pd.put("sale_total", totalSum);
				rechargeService.editAgent2(pd);//更新代理房卡和售卡
				pd.put("CREATOR", user.getUSERNAME());	//创建用户名
				pd.put("CHARGE_NAME", user.getNAME());
				pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
				pd.put("RECHARGE_ID", this.get32UUID());	//主键
				rechargeService.save(pd);//保存充值记录
			}else{
				msg = "no";
			}
		}
		
		pd.put("msg", msg);
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/json");
		res.setHeader("Pragma", "No-cache");
		res.setHeader("cache-Control", "no-cache");
		PrintWriter pw = res.getWriter();
		JSONArray jay = JSONArray.fromObject(pd);
		pw.print(jay);
		pw.flush();
		pw.close();
	}
	/**
	 * 验证玩家是否存在
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/checkUserId")
	public void checkUserId(HttpServletResponse res)throws Exception{
		PageData pd = this.getPageData();//获取前端传来的值
		String msg = "no";
		PageData pUser = userService.findByUserId(pd);
		if(pUser != null){
			pd = pUser;
			msg = "ok";
		}
		pd.put("msg", msg);
		res.setCharacterEncoding("utf-8");
		res.setContentType("application/json");
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = res.getWriter();
		JSONArray jay = JSONArray.fromObject(pd);
		pw.print(jay);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 跳转到玩家充值记录页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userRechargeList")
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
		
		pd.put("total", (int)(long)rechargeService.findUserRechargeTotal(pd).get("total"));//查询总记录数
		pd = paging(pd);//获取分页参数
		List<PageData> userList = rechargeService.findUserRechargeList(pd);//查询分页
		pd.put("list", userList);//列表
		pd.put("AGENT_CODE", number);//代理id
		
		pd.put("stas", 2);
		pd.put("menuName", "玩家充值记录");
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/user/user_recharge_list");
		return mv;
	}
	
	/**
	 * 加载更多
	 * @param res
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(value="loadMoreURList")
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
		
		pd.put("total", (int)(long)rechargeService.findUserRechargeTotal(pd).get("total"));//查询总记录数
		pd = paging(pd);//获取分页参数
		List<PageData> userList = rechargeService.findUserRechargeList(pd);//查询分页
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
