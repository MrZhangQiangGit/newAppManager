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
import com.bets.service.game.moneyStatistics.MoneyStatisticsManager;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;
import com.bets.util.Tools;

@Controller
@RequestMapping(value="/moneyStatistics")
public class MoneyStatisticsController extends BaseController {
	
	String menuUrl = "moneyStatistics/list.do"; //菜单地址(权限用)
	@Resource(name="moneystatisticsService")
	private MoneyStatisticsManager moneystatisticsService;
	
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("CREATOR", user.getUSERNAME());	//创建用户名
		
		String today = Tools.date2Str(new Date(),"yyyy-MM-dd");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse(today);
		String yesterday = sdf.format(new Date(d.getTime() - 24 * 60 * 60 * 1000));
		PageData total =moneystatisticsService.gettotalcharge(pd);
		pd.put("time", today);
		PageData tmoney = moneystatisticsService.gettotalcharge(pd);
		pd.put("time", yesterday);
		PageData ymoney = moneystatisticsService.gettotalcharge(pd);
		
		mv.setViewName("game/moneyStatistics");
		mv.addObject("todaySum", tmoney !=null?tmoney.get("MONEY"):0);
		mv.addObject("yesterdaySum", ymoney !=null?ymoney.get("MONEY"):0);
		mv.addObject("sum", total !=null?total.get("MONEY"):0);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
}
