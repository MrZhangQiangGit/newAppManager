package com.bets.controller.game;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.entity.system.User;
import com.bets.service.game.OnlineStatistics.OnlineStatisticsManager;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;
import com.bets.util.Tools;
 
@Controller
@RequestMapping(value="/onlineStatistics")
public class OnlineStatisticsController extends BaseController {
	
	String menuUrl = "onlineStatistics/list.do"; //菜单地址(权限用)
	@Resource(name="onlinestatisticsService")
	private OnlineStatisticsManager onlinestatisticsService;
	
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
//		String today = Tools.date2Str(new Date(),"yyyy-MM-dd");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date now = new Date();//sdf.parse(today);
		Date befor = new Date(now.getTime() - 15*60 * 60 * 100);//1.5*60*60*1000 一个半小时之内的在线玩家
		pd.put("now", now);
		pd.put("befor", befor);
		pd.put("CREATOR", user.getUSERNAME());	//创建用户名
		PageData wait = onlinestatisticsService.findByWait(pd);//等待人数
		PageData proceed = onlinestatisticsService.findByProceed(pd);//进行中人数
		mv.setViewName("game/onlineStatistics");
		mv.addObject("wait", wait);
		mv.addObject("proceed", proceed);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
}
