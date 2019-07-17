package com.bets.controller.game.fight;

import java.io.PrintWriter;
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
import com.bets.service.game.FightResultManager;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;

import net.sf.json.JSONArray;

/**
 * 玩家战绩
 * @author 12593
 *
 */

@Controller
@RequestMapping(value="/userFight")
public class FightResultAppController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(FightResultAppController.class);
	
	@Resource(name="fightresultService")
	private FightResultManager fightresultService;
	
	/**
	 * 跳转到玩家战绩记录页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userFightList")
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
		
		pd.put("total", (int)(long)fightresultService.findUserFightTotal(pd).get("total"));//查询总记录数
		pd = paging(pd);//获取分页参数
		List<PageData> userList = fightresultService.findUserFightList(pd);//查询分页
		pd.put("list", userList);//列表
		pd.put("AGENT_CODE", number);//代理id
		
		pd.put("stas", 2);
		pd.put("menuName", "玩家战绩");
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/user/user_fight_list");
		return mv;
	}
	
	/**
	 * 加载更多
	 * @param res
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(value="loadMoreUFList")
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
		
		pd.put("total", (int)(long)fightresultService.findUserFightTotal(pd).get("total"));//查询总记录数
		pd = paging(pd);//获取分页参数
		List<PageData> userList = fightresultService.findUserFightList(pd);//查询分页
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
