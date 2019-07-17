package com.bets.service.timetask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bets.service.game.agent.AgentManager;
import com.bets.service.game.rankstatistics.RankStatisticsManager;
import com.bets.service.game.tranDetail.TranDetailManager;
import com.bets.util.PageData;
import com.bets.util.Tools;
import com.bets.util.UuidUtil;
@Component("calSettelMoney")
public class CalSettleMoney {
	//日志需按照该方式进行处理
	private static  Logger logger=LoggerFactory.getLogger(CalSettleMoney.class);
	@Resource(name="trandetailService")
	private TranDetailManager trandetailService;
	@Resource(name="agentService")
	private AgentManager agentService;
	@Resource(name="rankStatisticsService")
	private RankStatisticsManager rankStatisticsService;
	 @Autowired
	 public void payMoney(){
		 System.out.println("-------------执行了一次------------");
	 }
	 /**
		 * 自动结算
		 * @throws Exception
		 */
//		@RequestMapping(value="autosettlement")
		public void settlement() throws Exception{
//			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
//			TranDetailManager trandetailService=(TranDetailManager)webApplicationContext.getBean("trandetailService");
//			AgentManager agentService=(AgentManager)webApplicationContext.getBean("agentService");
			System.out.println("--------------执行方法--------------------------------");
			trandetailService.updatnoeagent();//更改代理商为空的记录
			List<PageData> orderlist =trandetailService.selectbyjs();//查询未结算账单
			for(int i=0;i<orderlist.size();i++){
				PageData pd =new PageData();
				PageData orderinfo = orderlist.get(i);//订单信息
				String orderid = orderinfo.getString("TRAN_ID");//订单号
				String agentid = orderinfo.getString("AGENT_USER_ID");//代理商ID
				pd.put("AGENT_CODE", agentid);
				PageData agentinfo =agentService.findById(pd);//代理商信息
				double money = Double.parseDouble(orderinfo.getString("TRAN_MONEY"));//充值金额
				if(agentinfo !=null){
					if("1".equals(agentinfo.getString("STATUS"))){
						String level = agentinfo.getString("LEVEL");
						if("1".equals(level)){//一级代理商
							double bl = Double.parseDouble(agentinfo.getString("PROFIT"));//分润比例
							double profit = money * bl * 0.01;//分润
							pd.put("ID",UuidUtil.get32UUID());
							pd.put("TRAN_ID",orderid );
							pd.put("USER_ID",orderinfo.getString("USER_ID") );
							pd.put("USER_NICK_NAME",orderinfo.getString("USER_NICK_NAME"));
							pd.put("TRAN_MONEY",orderinfo.getString("TRAN_MONEY"));
							pd.put("AGENT_ID",agentid );
							pd.put("PROFIT_TYPE", 1);//分润类型 1 玩家充值直接获利 2 下级代理充值获利
							pd.put("AGENT_PROFIT_BL", agentinfo.getString("PROFIT"));
							pd.put("AGENT_PROFIT_MONEY",profit );
							pd.put("TRAN_CONTENT",orderinfo.getString("TRAN_CONTENT") );
							pd.put("STATUS", "0");//未付款给代理商
							pd.put("CREATE_TIME", new Date());
							pd.put("IS_JIESUAN", 1);
							trandetailService.addsettlement(pd);
						}else if("2".equals(level)){//二级代理商
							//二级代理
							double bl = Double.parseDouble(agentinfo.getString("PROFIT"));//分润比例
							double profit = money * bl * 0.01;//分润
							pd.put("ID",UuidUtil.get32UUID());
							pd.put("TRAN_ID",orderid );
							pd.put("USER_ID",orderinfo.getString("USER_ID") );
							pd.put("USER_NICK_NAME",orderinfo.getString("USER_NICK_NAME"));
							pd.put("TRAN_MONEY",orderinfo.getString("TRAN_MONEY"));
							pd.put("AGENT_ID",agentid );
							pd.put("PROFIT_TYPE", 1);
							pd.put("AGENT_PROFIT_BL", agentinfo.getString("PROFIT"));
							pd.put("AGENT_PROFIT_MONEY",profit );
							pd.put("TRAN_CONTENT",orderinfo.getString("TRAN_CONTENT") );
							pd.put("STATUS", "0");//未付款给代理商
							pd.put("CREATE_TIME", new Date());
							pd.put("IS_JIESUAN", 1);
							//二级代理上级代理
							String upagentid =agentinfo.getString("P_ID");//一级代理商
							double upbl = Double.parseDouble(agentinfo.getString("UP_PROFIT"));//基于二级的一级分润比例
							double upprofit = money * upbl * 0.01;//分润
							PageData upd = new PageData();
							upd.put("ID",UuidUtil.get32UUID());
							upd.put("TRAN_ID",orderid );
							upd.put("USER_ID",orderinfo.getString("USER_ID") );
							upd.put("USER_NICK_NAME",orderinfo.getString("USER_NICK_NAME"));
							upd.put("TRAN_MONEY",orderinfo.getString("TRAN_MONEY"));
							upd.put("AGENT_ID",upagentid );
							upd.put("PROFIT_TYPE", 2);
							upd.put("AGENT_PROFIT_BL", agentinfo.getString("UP_PROFIT"));
							upd.put("AGENT_PROFIT_MONEY",upprofit );
							upd.put("TRAN_CONTENT",orderinfo.getString("TRAN_CONTENT") );
							upd.put("STATUS", "0");//未付款给代理商
							upd.put("CREATE_TIME", new Date());
							upd.put("IS_JIESUAN", 1);
							trandetailService.addsettlement1(pd,upd);
						}else if("3".equals(level)){//三级代理商
							//三级代理
							double bl = Double.parseDouble(agentinfo.getString("PROFIT"));//分润比例
							double profit = money * bl * 0.01;//分润
							pd.put("ID",UuidUtil.get32UUID());
							pd.put("TRAN_ID",orderid );
							pd.put("USER_ID",orderinfo.getString("USER_ID") );
							pd.put("USER_NICK_NAME",orderinfo.getString("USER_NICK_NAME"));
							pd.put("TRAN_MONEY",orderinfo.getString("TRAN_MONEY"));
							pd.put("AGENT_ID",agentid );
							pd.put("PROFIT_TYPE", 1);
							pd.put("AGENT_PROFIT_BL", agentinfo.getString("PROFIT"));
							pd.put("AGENT_PROFIT_MONEY",profit );
							pd.put("TRAN_CONTENT",orderinfo.getString("TRAN_CONTENT") );
							pd.put("STATUS", "0");//未付款给代理商
							pd.put("CREATE_TIME", new Date());
							pd.put("IS_JIESUAN", 1);
							//三级代理上级代理
							String upagentid =agentinfo.getString("P_ID");//二级代理id
							double upbl = Double.parseDouble(agentinfo.getString("UP_PROFIT"));//基于二级的一级分润比例
							double upprofit = money * upbl * 0.01;//分润
							PageData upd = new PageData();
							upd.put("ID",UuidUtil.get32UUID());
							upd.put("TRAN_ID",orderid );
							upd.put("USER_ID",orderinfo.getString("USER_ID") );
							upd.put("USER_NICK_NAME",orderinfo.getString("USER_NICK_NAME"));
							upd.put("TRAN_MONEY",orderinfo.getString("TRAN_MONEY"));
							upd.put("AGENT_ID",upagentid );
							upd.put("PROFIT_TYPE", 2);
							upd.put("AGENT_PROFIT_BL", agentinfo.getString("UP_PROFIT"));
							upd.put("AGENT_PROFIT_MONEY",upprofit );
							upd.put("TRAN_CONTENT",orderinfo.getString("TRAN_CONTENT") );
							upd.put("STATUS", "0");//未付款给代理商
							upd.put("CREATE_TIME", new Date());
							//基于 三级代理的一级代理
							PageData tpd = new PageData();
							tpd.put("AGENT_CODE", upagentid);
							PageData topagentinfo =agentService.findById(tpd);//二级代理商信息
							String topagentid =topagentinfo.getString("P_ID");//基于二级代理的一级代理商
							double topbl = Double.parseDouble(topagentinfo.getString("UP_PROFIT"));//基于二级的一级分润比例
							double topprofit = money * topbl * 0.01;//分润
							tpd.put("ID",UuidUtil.get32UUID());
							tpd.put("TRAN_ID",orderid );
							tpd.put("USER_ID",orderinfo.getString("USER_ID") );
							tpd.put("USER_NICK_NAME",orderinfo.getString("USER_NICK_NAME"));
							tpd.put("TRAN_MONEY",orderinfo.getString("TRAN_MONEY"));
							tpd.put("AGENT_ID",topagentid );
							tpd.put("AGENT_PROFIT_BL", topagentinfo.getString("UP_PROFIT"));
							tpd.put("AGENT_PROFIT_MONEY",topprofit );
							tpd.put("PROFIT_TYPE", 2);
							tpd.put("TRAN_CONTENT",orderinfo.getString("TRAN_CONTENT") );
							tpd.put("STATUS", "0");//未付款给代理商
							tpd.put("CREATE_TIME", new Date());
							trandetailService.addsettlement2(pd,upd,tpd);
						}
					}
				}
			}
		}
		
		public void rankstatistics()throws Exception{
			System.out.println("执行第二个方法");
			rankStatisticsService.deletetabledata();
			Date today = new Date();//当前时间
			Date yesterday=new Date(today.getTime() - 24 * 60 * 60 * 1000);//昨天的当前时间
			PageData pd =new PageData();
			String time = Tools.date2Str(today,"yyyy-MM-dd");
			String ytime = Tools.date2Str(yesterday,"yyyy-MM-dd");
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int d = 0;
			if(cal.get(Calendar.DAY_OF_WEEK)==1){
				d = -6;
			}else{
				d = 2-cal.get(Calendar.DAY_OF_WEEK);
			}
			cal.add(Calendar.DAY_OF_WEEK, d);
			//所在周开始日期 本周一
			//System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			String weekstart=new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			cal.add(Calendar.DAY_OF_WEEK, 6);
			String weekend = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			//所在周结束日期 本周日
			//System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			//上周日
			cal.add(Calendar.DAY_OF_WEEK, -7);
			//System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			String lastweeke = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			//上周一
			cal.add(Calendar.DAY_OF_WEEK, -6);
			//System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
			String lastweeks = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
			//String USER_ID="";
			pd.put("weekstart",weekstart+" 00:00:01" );
			pd.put("weekend",weekend+" 23:59:59" );
			pd.put("lastweeks",lastweeks+" 00:00:01" );
			pd.put("lastweeke",lastweeke+" 23:59:59" );
			pd.put("today",time );
			pd.put("yesterday",ytime );
			pd.put("type",1 );//今天数据
			List<PageData> tsocre = rankStatisticsService.getscore(pd);
			insertdate(tsocre,1);
			pd.put("type",2 );//昨天数据
			List<PageData> ysocre = rankStatisticsService.getscore(pd);
			insertdate(ysocre,2);
			pd.put("type",3 );//本周数据
			List<PageData> wsocre = rankStatisticsService.getscore(pd);
			insertdate(wsocre,3);
			pd.put("type",4 );//上周数据
			List<PageData> lsocre = rankStatisticsService.getscore(pd);
			insertdate(lsocre,4);
		}
		
		public void insertdate(List<PageData> date,int RANK_TYPE)throws Exception{
			Date today = new Date();//当前时间
			Date yesterday=new Date(today.getTime() - 24 * 60 * 60 * 1000);//昨天的当前时间
			String time = Tools.date2Str(today,"yyyy-MM-dd");
			String ytime = Tools.date2Str(yesterday,"yyyy-MM-dd");
			for(int i=0;i<date.size();i++){
				PageData pd =new PageData();
				PageData rankinfo=new PageData();
				String USER_ID = date.get(i).getString("USER_ID");
				pd.put("USER_ID",USER_ID );
				pd.put("today",time );
				pd.put("yesterday",ytime );
				pd.put("type",RANK_TYPE );
				pd.put("result",1 );//胜
				PageData vn = rankStatisticsService.getresultinfo(pd);
				pd.put("result",2 );//负
				PageData ln = rankStatisticsService.getresultinfo(pd);
				pd.put("result",3 );//平
				PageData n = rankStatisticsService.getresultinfo(pd);
				rankinfo.put("ID",UuidUtil.get32UUID() );
				rankinfo.put("USER_ID",USER_ID );
				rankinfo.put("PHOTO_URL",date.get(i).getString("LOGO_URL") );
				rankinfo.put("USER_NICK",date.get(i).getString("NICK_NAME") );
				rankinfo.put("SHOW_MESSAGE",vn.get("num")+"胜"+ln.get("num")+"负"+n.get("num")+"平" );
				rankinfo.put("COUNT_SCORE",date.get(i).get("SCORE") );
				rankinfo.put("RANK_TYPE",RANK_TYPE );
				rankinfo.put("CREATOR_TIME",new Date() );
				rankStatisticsService.insertrankdata(rankinfo);
			}
		}
}
