package com.bets.controller.game;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.entity.Page;
import com.bets.entity.system.User;
import com.bets.service.game.cardStatistics.CardStatisticsManager;
import com.bets.service.game.moneyStatistics.MoneyStatisticsManager;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;
import com.bets.util.Tools;

@Controller
@RequestMapping(value="/cardStatistics")
public class CardStatisticsController extends BaseController {
	
	String menuUrl = "cardStatistics/list.do"; //菜单地址(权限用)
	@Resource(name="cardstatisticsService")
	private CardStatisticsManager cardstatisticsService;
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
		Date yesterday = sdf.parse(today);
		String CREATOR_TIME2 = sdf.format(new Date(yesterday.getTime() - 24 * 60 * 60 * 1000));
		pd.put("CREATOR_TIME1", today);
		pd.put("CREATOR_TIME2", CREATOR_TIME2);
		int todaySum;
		int yesterdaySum;
		int sum;
		int todaySum2;
		int yesterdaySum2;
		int sum2;
		PageData todays = cardstatisticsService.findByToday(pd);//今天
		if(todays == null){
			todaySum = 0;
		}else{
			double a = (double)todays.get("A");
			todaySum = (new Double(Math.rint(a))).intValue();
		}
		PageData todays2 = cardstatisticsService.findByToday2(pd);//今天
		if(todays2 == null){
			todaySum2 = 0;
		}else{
			double a = (double)todays2.get("A2");
			todaySum2 = (new Double(Math.rint(a))).intValue();
		}
		PageData yesterdays = cardstatisticsService.findByYesterday(pd);//昨天
		if(yesterdays == null){
			yesterdaySum=0;
		}else{
			double b = (double)yesterdays.get("B");
			yesterdaySum = (new Double(Math.rint(b))).intValue();
		}
		PageData yesterdays2 = cardstatisticsService.findByYesterday2(pd);//昨天
		if(yesterdays2 == null){
			yesterdaySum2=0;
		}else{
			double b = (double)yesterdays2.get("B2");
			yesterdaySum2 = (new Double(Math.rint(b))).intValue();
		}
		PageData s = cardstatisticsService.findBySum(pd);//全部
		if(s == null){
			sum=0;
		}else{
			double c = (double)s.get("C");
			sum = (new Double(Math.rint(c))).intValue();
		}
		PageData s2 = cardstatisticsService.findBySum2(pd);//全部
		if(s2 == null){
			sum2=0;
		}else{
			double c = (double)s2.get("C2");
			sum2 = (new Double(Math.rint(c))).intValue();
		}
		mv.setViewName("game/cardStatistics");
		mv.addObject("todaySum", todaySum+todaySum2);
		mv.addObject("yesterdaySum", yesterdaySum+yesterdaySum2);
		mv.addObject("sum", sum+sum2);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	//售卡统计
		@RequestMapping(value="/sellcardstatistics")
		public ModelAndView sellcardstatistics(Page page) throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd = new PageData();
			pd = this.getPageData();
			String keywords = pd.getString("keywords");				//关键词检索条件
			if(null != keywords && !"".equals(keywords)){
				pd.put("keywords", keywords.trim());
			}
			String lastStart = pd.getString("lastStart");	//开始时间
			String lastEnd = pd.getString("lastEnd");		//结束时间
			
			if(lastStart != null && !"".equals(lastStart)){
				pd.put("lastStart", lastStart+" 00:00:00");
			}
			if(lastEnd != null && !"".equals(lastEnd)){
				pd.put("lastEnd", lastEnd+" 00:00:00");
			}
			if(lastStart != null && lastEnd != null ){
				if(lastEnd.equals(lastStart)){
					pd.put("today",lastStart );
					pd.remove("lastEnd");
					pd.remove("lastStart");
				}
			}
			page.setPd(pd);
			List<PageData> list = cardstatisticsService.cardsalestatisticslistPage(page);
			List<PageData> totallist = cardstatisticsService.totalsalecards(page);
			PageData lists= new PageData();
			double MONEY=0.0;
			for(int i=0;i<totallist.size();i++){
				PageData datas=totallist.get(i);
				lists.put("type"+datas.getString("CHARGE_TYPE"),datas.get("num") );
				if(datas.get("money")!=null && "1".equals(datas.getString("CHARGE_TYPE"))){
					MONEY= MONEY+(double)datas.get("money");
				}
				lists.put("MONEY",MONEY );
			}
			pd.put("lastEnd",lastEnd );
			pd.put("lastStart",lastStart );
			mv.setViewName("game/statisticsCard");
			mv.addObject("pd",pd);
			mv.addObject("list",list);
			//mv.addObject("totallist",totallist);
			mv.addObject("total", lists);
			return mv;
		}
		
		/**
		 * 平台售卡金额月统计
		 */
		@RequestMapping(value="/Mmonthstatistics")
		public ModelAndView mstatisticscardformonth(Page page)throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd =new PageData();
			page.setPd(pd);
			List<PageData> ptotallist =moneystatisticsService.monthtotalmoneylistPage(page);
			mv.addObject("total", ptotallist);
			mv.setViewName("game/Mmonthstatistics");
			return mv;
		}
		/**
		 * 微信售卡金额月统计
		 */
		@RequestMapping(value="/Wmonthstatistics")
		public ModelAndView wstatisticscardformonth(Page page)throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd =new PageData();
			page.setPd(pd);
			List<PageData> ptotallist =moneystatisticsService.wxmonthtotalmoneye();
			mv.addObject("total", ptotallist);
			mv.setViewName("game/Wmonthstatistics");
			return mv;
		}
}
