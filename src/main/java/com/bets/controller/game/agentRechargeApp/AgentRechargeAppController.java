package com.bets.controller.game.agentRechargeApp;

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
import com.bets.service.game.agent.AgentManager;
import com.bets.service.game.agentRecharge.AgentRechargeManager;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;
import com.bets.util.Tools;

import net.sf.json.JSONArray;

/**
 * 代理充值
 * @author 12593
 *
 */
@Controller
@RequestMapping(value="/agentRechargeApp")
public class AgentRechargeAppController extends BaseController{
	//日志输出
	private static Logger logger = LoggerFactory.getLogger(AgentRechargeAppController.class);
	
	@Resource(name="agentrechargeService")
	private AgentRechargeManager agentrechargeService;
	@Resource(name="agentService")
	private AgentManager agentService;
	
	/**
	 * 去代理app充值页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goAgentRecharge")
	public ModelAndView goAgentRecharge()throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = this.getPageData();
		PageData pAgent = agentService.findById(pd);
		if(pAgent != null){
			pd = pAgent;
		}
		pd.put("stas", 2);
		pd.put("menuName", "代理充值");//顶部标题
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/agent/agent_recharge");
		return mv;
	}
	/**
	 * 代理充值
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/agentRecharge")
	public void agentRecharge(HttpServletResponse res)throws Exception{
		PageData pd = this.getPageData();
		
		//业务逻辑
		String msg = "ok";//充值信息,ok为充值成功,no,为房卡不足
		//登陆人游戏ID
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("USERNAME", user.getUSERNAME());
		String number = user.getNUMBER();//得到游戏id
		logger.info("-----"+user.getUSERNAME()+"发起代理充值");
		
		//页面传过来的值
		String gross1 = pd.getString("CHARGE_NUM");//充值房卡数量
		int gross = Integer.parseInt(gross1);//转换为Int
		
		pd.put("USER_ID", pd.getString("AGENT_CODE"));//插入充值记录中的被充值代理id
		pd.put("NICK_NAME", pd.getString("AGENT_NAME"));//插入充值记录中的被充值代理名称
		//查询代理商数据库剩余房卡根据被充值的ID
		pd.put("agent_code1", pd.getString("AGENT_CODE"));
		
		PageData pasa = agentrechargeService.findCardById(pd);
		String roomC = pasa.getString("CARD_NUM");//代理表中的房卡数
		int roomCard = 0;
		if(null!=roomC&&roomC.length()>0){
			roomCard = Integer.parseInt(roomC);//转换为Int  
		}
		int roomCardSub =0;//充值人剩余房卡
		int totalAdd = 0;//充值人售卡总数
		//查询代理表中登陆人数据库房卡数和销售总量根据充值人的id
		pd.put("agent_code2", number);//充值人的ID
		PageData pas = agentrechargeService.findCardById2(pd);//查询m_agent
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
		if(!number.equals(pd.getString("AGENT_CODE"))){
			if("admin".equals(user.getUSERNAME())){//登陆为admin
				int sum = roomCard+gross;//房卡和（代理商）
				pd.put("card_num1", sum);
				agentrechargeService.edit1(pd);//修改被充值代理的房卡
				pd.put("total", totalAdd+gross);
				agentrechargeService.edit3(pd);//修改充值人的售卡数
				String name = user.getUSERNAME();
				pd.put("CREATOR", name);	//创建用户名
				pd.put("CREATOR",user.getNAME() );
				pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
				pd.put("AGENTRECHARGE_ID", this.get32UUID());	//主键
				agentrechargeService.save(pd);//保存充值记录
			}else{//不是admin登陆
				//判断充值房卡是否大于剩余房卡
				if(roomCardSub>=gross){
					int sum = roomCard+gross;//房卡和（代理商）
					pd.put("card_num1", sum);
					agentrechargeService.edit1(pd);//修改被充值代理的房卡
					int bad = roomCardSub-gross;  //房卡差（代理商）
					pd.put("card_num2", bad);
					agentrechargeService.edit2(pd);//修改充值人的房卡数
					pd.put("total", totalAdd+gross);
					agentrechargeService.edit3(pd);//修改充值人的售卡数
					String name = user.getUSERNAME();
					name = user.getNAME()+"("+name+")";
					pd.put("CREATOR", name);	//创建用户名
					pd.put("CREATOR",user.getNAME() );
					pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
					pd.put("AGENTRECHARGE_ID", this.get32UUID());	//主键
					agentrechargeService.save(pd);
				}else{
					msg="no";//房卡不足
				}
			}
		}
		pd.put("msg", msg);
		res.setContentType("application/json");
		res.setHeader("Pragma", "No-cache");
		res.setHeader("Cache-Control", "No-cache");
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = res.getWriter();
		JSONArray jay = JSONArray.fromObject(pd);
		pw.print(jay);
		pw.flush();
		pw.close();
	}
	
	/**
	 * 验证代理是否存在
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/checkAgentId")
	public void checkAgentId(HttpServletResponse res)throws Exception{
		PageData pd  = this.getPageData();
		String msg = "no";
		PageData pa = agentService.findById(pd);
		if(pa !=null){
			pd = pa;
			msg = "ok";
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
	 * 跳转到代理充值记录页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/agentRechargeList")
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
		
		pd.put("total", (int)(long)agentrechargeService.findAgentRechargeTotal(pd).get("total"));//查询总记录数
		pd = paging(pd);//获取分页参数
		List<PageData> userList = agentrechargeService.findAgentRechargeList(pd);//查询分页
		pd.put("list", userList);//列表
		pd.put("AGENT_CODE", number);//代理id
		
		
		pd.put("stas", 2);
		pd.put("menuName", "代理充值记录");
		mv.addObject("pd", pd);
		mv.setViewName("/newgame/agent/agent_recharge_list");
		return mv;
	}
	
	
	/**
	 * 分页工具(根据总记录数,获取分页参数)
	 * @param pd 分页相关数据(包含,页面传过来的值,查询总数)
	 * @return
	 * @throws Exception
	 */
	public PageData paging(PageData pd)throws Exception{
		
		int pageNumber = 8;//每页记录数
		int page;//页数
		int total;//获取总记录数
		int totalPage;//总页数
		int start;//分页起始值
		pd.put("sum", pageNumber);//分页每页记录数
		
		total = (int)pd.get("total");//查询总记录数
		if(total%pageNumber==0){
			totalPage = total/pageNumber; 
		}else {
			totalPage = total/pageNumber+1;
		}
		
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
		
		pd.put("page", page);//页数
		pd.put("total", total);//总记录数
		pd.put("totalPage", totalPage);//总页数
		
		return pd;
	}
	
	/**
	 * 加载更多
	 * @param res
	 * @param req
	 * @throws Exception
	 */
	@RequestMapping(value="loadMoreARList")
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
		
		pd.put("total", (int)(long)agentrechargeService.findAgentRechargeTotal(pd).get("total"));//查询总记录数
		pd = paging(pd);//获取分页参数
		List<PageData> userList = agentrechargeService.findAgentRechargeList(pd);//查询分页
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





