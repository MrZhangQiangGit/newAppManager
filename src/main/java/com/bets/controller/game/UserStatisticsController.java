package com.bets.controller.game;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.service.game.userStatistics.UserStatisticsManager;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;
import com.bets.util.Tools;

@Controller
@RequestMapping(value="/userStatistics")
public class UserStatisticsController extends BaseController {
	
	String menuUrl = "userStatistics/list.do"; //菜单地址(权限用)
	@Resource(name="userstatisticsService")
	private UserStatisticsManager userstatisticsService;
	
	@RequestMapping(value="/list")
	public ModelAndView list() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String today = Tools.date2Str(new Date(),"yyyy-MM-dd");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date yesterday = sdf.parse(today);
		String CREATOR_TIME2 = sdf.format(new Date(yesterday.getTime() - 24 * 60 * 60 * 1000));
		pd.put("CREATOR_TIME1", today);
		pd.put("CREATOR_TIME2", CREATOR_TIME2);
		PageData todaySum = userstatisticsService.findByToday(pd);
		PageData yesterdaySum = userstatisticsService.findByYesterday(pd);
		PageData sum = userstatisticsService.findBySum(pd);
		mv.setViewName("game/userStatistics");
		mv.addObject("todaySum", todaySum);
		mv.addObject("yesterdaySum", yesterdaySum);
		mv.addObject("sum", sum);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
}
