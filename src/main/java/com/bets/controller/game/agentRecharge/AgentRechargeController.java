package com.bets.controller.game.agentRecharge;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
import com.bets.util.AppUtil;
import com.bets.util.Const;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Tools;

import net.sf.json.JSONArray;

import com.bets.util.Jurisdiction;
import com.bets.service.game.agent.AgentManager;
import com.bets.service.game.agentRecharge.AgentRechargeManager;

/** 
 * 说明：代理冲卡
 * 创建人：bets 
 * 创建时间：2016-12-17
 */
@Controller
@RequestMapping(value="/agentrecharge")
public class AgentRechargeController extends BaseController {
	
	String menuUrl = "agentrecharge/list.do"; //菜单地址(权限用)
	@Resource(name="agentrechargeService")
	private AgentRechargeManager agentrechargeService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//登陆人游戏ID
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("USERNAME", user.getUSERNAME());
		String number = user.getNUMBER();//得到游戏id
		
		//页面传过来的值
		String gross1 = pd.getString("CHARGE_NUM");//充值房卡数量
		int gross = Integer.parseInt(gross1);//转换为Int
		
		//查询代理商数据库剩余房卡根据被充值的ID
		pd.put("agent_code1", pd.getString("USER_ID"));
		PageData pasa = agentrechargeService.findCardById(pd);
		String roomC = pasa.getString("CARD_NUM");//代理表中的房卡数
		int roomCard = 0;
		if(null!=roomC&&roomC.length()>0){
			roomCard = Integer.parseInt(roomC);//转换为Int  
		}
		
		int roomCardSub =0;
		int totalAdd = 0;
		
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
		
		/**
		 * pd     中有（USERNAME，USER_ID，CHARGE_NUM，AGENT_CODE）
		 * p      查询sys_user
		 * pas    查询m_agent  充值人
		 * pasa   查询m_app_user  得到充值（玩家）
		 */
		
		if(!number.equals(pd.getString("USER_ID"))){
			if("admin".equals(user.getUSERNAME())){//登陆为admin
				int sum = roomCard+gross;//房卡和（代理商）
				pd.put("card_num1", sum);
				agentrechargeService.edit1(pd);
				pd.put("total", totalAdd+gross);
				agentrechargeService.edit3(pd);
				String name = user.getUSERNAME();
				//name = user.getNAME()+"("+name+")";
				pd.put("CREATOR", name);	//创建用户名
				pd.put("CHARGE_NAME",user.getNAME() );
				pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
				pd.put("AGENTRECHARGE_ID", this.get32UUID());	//主键
				agentrechargeService.save(pd);
				mv.addObject("msg","success");
				mv.setViewName("save_result");
			}else{//不是admin登陆
				if(roomCardSub>=gross){
					int sum = roomCard+gross;//房卡和（代理商）
					pd.put("card_num1", sum);
					agentrechargeService.edit1(pd);
					int bad = roomCardSub-gross;  //房卡差（代理商）
					pd.put("card_num2", bad);
					agentrechargeService.edit2(pd);
					pd.put("total", totalAdd+gross);
					agentrechargeService.edit3(pd);
					String name = user.getUSERNAME();
					name = user.getNAME()+"("+name+")";
					pd.put("CREATOR", name);	//创建用户名
					pd.put("CHARGE_NAME",user.getNAME() );
					pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
					pd.put("AGENTRECHARGE_ID", this.get32UUID());	//主键
					agentrechargeService.save(pd);
					mv.addObject("msg","success");
					mv.setViewName("save_result");
				}
			}
		}
		return mv;
	}
	
	/**去补卡页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditAdd")
	public ModelAndView goEditAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String num = user.getNUMBER();
		String name = user.getUSERNAME();
		name = user.getNAME()+"("+name+")";
		pd.put("CREATOR", name);	//创建用户名
		pd = agentrechargeService.findById(pd);
		mv.setViewName("game/agentRecharge/agentrecharge_editadd");
		mv.addObject("msg", "editAdd");
		mv.addObject("pd", pd);
		mv.addObject("num", num);
		return mv;
	}
	
	
	/**补卡
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/editAdd")
	public ModelAndView editAdd() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//登陆人游戏ID
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String name = user.getUSERNAME();
		name = user.getNAME()+"("+name+")";
		pd.put("CREATOR", name);	//创建用户名
		String number = user.getNUMBER();//得到游戏id
		
		//页面传过来的值
		String gross1 = pd.getString("CHARGE_NUM");//充值房卡数量
		int gross = Integer.parseInt(gross1);//转换为Int
		
		//查询代理商数据库剩余房卡根据被充值的ID
		pd.put("agent_code1", pd.getString("USER_ID"));
		PageData pasa = agentrechargeService.findCardById(pd);
		if(null!=pasa){          //代理商把自己创建的代理商删除了  就为NULL
			String roomC = pasa.getString("CARD_NUM");//代理表中的房卡数
			int roomCard = 0;
			if(null!=roomC&&roomC.length()>0){
				roomCard = Integer.parseInt(roomC);//转换为Int  
			}
		
		
		int roomCardSub =0;
		int totalAdd = 0;
		
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
			
			String num = pd.getString("NUM");//代理商冲卡记录表  得到冲过的房卡
			int nums = Integer.parseInt(num);
		
		/**
		 * pd     中有（USERNAME，USER_ID，CHARGE_NUM，AGENT_CODE）
		 * p      查询sys_user
		 * pas    查询m_agent  充值人
		 * pasa   查询m_app_user  得到充值（玩家）
		 */
		
		
			if("admin".equals(user.getUSERNAME())){//登陆为admin
				int sum = roomCard+gross;//房卡和（代理商）
				pd.put("card_num1", sum);
				agentrechargeService.edit1(pd);
				pd.put("total", totalAdd+gross);
				agentrechargeService.edit3(pd);
				pd.put("NUMS", nums+gross);
				agentrechargeService.edit4(pd);
				mv.addObject("msg","success");
				mv.setViewName("save_result");
			}else{//不是admin登陆
				if(roomCardSub>=gross){
					int sum = roomCard+gross;//房卡和（代理商）
					pd.put("card_num1", sum);
					agentrechargeService.edit1(pd);
					int bad = roomCardSub-gross;  //房卡差（代理商）
					pd.put("card_num2", bad);
					agentrechargeService.edit2(pd);
					pd.put("total", totalAdd+gross);
					agentrechargeService.edit3(pd);
					pd.put("NUMS", nums+gross);
					agentrechargeService.edit4(pd);
					mv.addObject("msg","success");
					mv.setViewName("save_result");
				}
			}
		}
		return mv;
	}
	
	/**去退卡页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEditBack")
	public ModelAndView goEditBack()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String num = user.getNUMBER();
		String name = user.getUSERNAME();
		name = user.getNAME()+"("+name+")";
		pd.put("CREATOR", name);	//创建用户名
		pd = agentrechargeService.findById(pd);
		mv.setViewName("game/agentRecharge/agentrecharge_editback");
		mv.addObject("msg", "editBack");
		mv.addObject("pd", pd);
		mv.addObject("num", num);
		return mv;
	}
	
	
	/**退卡
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/editBack")
	public ModelAndView editBack() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//登陆人游戏ID
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("USERNAME", user.getUSERNAME());
		String number = user.getNUMBER();//得到游戏id
		
		//页面传过来的值
		String gross1 = pd.getString("CHARGE_NUM");//充值房卡数量
		int gross = Integer.parseInt(gross1);//转换为Int
		
		//查询代理商数据库剩余房卡根据被充值的ID
		pd.put("agent_code1", pd.getString("USER_ID"));
		PageData pasa = agentrechargeService.findCardById(pd);
		if(pasa != null){
			String roomC = pasa.getString("CARD_NUM");//代理表中的房卡数
			int roomCard = 0;
			if(null!=roomC&&roomC.length()>0){
				roomCard = Integer.parseInt(roomC);//转换为Int  
			}
			
			int roomCardSub =0;
			int totalAdd = 0;
			
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
				
				String num = pd.getString("NUM");//代理商冲卡记录表  得到冲过的房卡
				int nums = Integer.parseInt(num);
			
			/**
			 * pd     中有（USERNAME，USER_ID，CHARGE_NUM，AGENT_CODE）
			 * p      查询sys_user
			 * pas    查询m_agent  充值人
			 * pasa   查询m_app_user  得到充值（玩家）
			 */
			
			
				if("admin".equals(user.getUSERNAME())){//登陆为admin
					if(nums>=gross){//充值的房卡记录 >= 要退卡的房卡数
						if(roomCard>=gross){//被退卡的代理商房卡数 >= 页面要退卡的房卡数
							int sum = roomCard-gross;//房卡和（代理商）
							pd.put("card_num1", sum);
							agentrechargeService.edit1(pd);
							pd.put("total", totalAdd-gross);
							agentrechargeService.edit3(pd);
							pd.put("NUMS", nums-gross);
							agentrechargeService.edit4(pd);
						}else {
							int sum = roomCard-roomCard;//房卡和（代理商）
							pd.put("card_num1", sum);
							agentrechargeService.edit1(pd);
							pd.put("total", totalAdd-roomCard);
							agentrechargeService.edit3(pd);
							pd.put("NUMS", nums-roomCard);//代理商的房卡数 <=页面要退卡的房卡数  且<= 冲卡记录数  自动把代理商的房卡减为0
							agentrechargeService.edit4(pd);
						}
						mv.addObject("msg","success");
						mv.setViewName("save_result");
					}
				}else{//不是admin登陆
					if(nums>=gross){//充值的房卡记录 >= 要退卡的房卡数
						if(roomCard>=gross){//被退卡的代理商房卡数 >= 页面要退卡的房卡数
							int sum = roomCard-gross;//房卡和（代理商）
							pd.put("card_num1", sum);
							agentrechargeService.edit1(pd);
							int bad = roomCardSub+gross;  //房卡差（代理商）
							pd.put("card_num2", bad);
							agentrechargeService.edit2(pd);
							pd.put("total", totalAdd-gross);
							agentrechargeService.edit3(pd);
							pd.put("NUMS", nums-gross);
							agentrechargeService.edit4(pd);
						}else{
							int sum = roomCard-roomCard;//房卡和（代理商）
							pd.put("card_num1", sum);
							agentrechargeService.edit1(pd);
							int bad = roomCardSub+roomCard;  //房卡差（代理商）
							pd.put("card_num2", bad);
							agentrechargeService.edit2(pd);
							pd.put("total", totalAdd-roomCard);
							agentrechargeService.edit3(pd);
							pd.put("NUMS", nums-roomCard);//代理商的房卡数 <=页面要退卡的房卡数  且<= 冲卡记录数  自动把代理商的房卡减为0
							agentrechargeService.edit4(pd);
						}
						mv.addObject("msg","success");
						mv.setViewName("save_result");
					}
				}
		}
		return mv;
	}
	
	@RequestMapping("/findObjectByid")
	public void findObjectByid(HttpServletResponse response,String id,String aid) throws Exception {
		
		PageData pd = agentrechargeService.findMun(id);
		PageData npd = agentrechargeService.findchargeMun(aid);
		pd.put("CHARGE_NUM", npd.get("CHARGE_NUM"));
		if (null!=pd && pd.size()>0) {
			 //设置页面不缓存
	        response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out= null;
	        out = response.getWriter();
	        JSONArray arry = JSONArray.fromObject(pd);
	        out.print(arry);
	        out.flush();
	        out.close();
		}
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改AgentRecharge");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		agentrechargeService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表AgentRecharge");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart=pd.getString("lastStart");
		String lastEnd= pd.getString("lastEnd");
		if(lastEnd !=null && !"".equals(lastEnd)){
			pd.put("lastEnd",lastEnd+" 23:59:59" );
		}
		if(lastStart !=null && !"".equals(lastStart)){
			pd.put("lastStart",lastStart+" 00:00:00" );
		}
		
		if(lastStart != null && lastEnd != null ){
			if(lastStart.equals(lastEnd)){
				pd.put("today",lastEnd );
				pd.remove("lastEnd");
				pd.remove("lastStart");
			}
		}
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("CREATOR", user.getUSERNAME());
		page.setPd(pd);
		List<PageData>	varList = agentrechargeService.list(page);	//列出AgentRecharge列表
		pd.put("lastEnd",lastEnd );
		pd.put("lastStart",lastStart);
		mv.setViewName("game/agentRecharge/agentrecharge_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(Page page)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String num = user.getNUMBER();
		pd.put("CREATOR", user.getUSERNAME());
		List<PageData> list = agentrechargeService.findAgentByUser(pd);
		JSONArray jsonList = JSONArray.fromObject(list);
		mv.setViewName("game/agentRecharge/agentrecharge_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("num", num);
		mv.addObject("list", jsonList);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = agentrechargeService.findById(pd);	//根据ID读取
		mv.setViewName("game/agentRecharge/agentrecharge_edit");
		mv.addObject("msg", "edit");
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
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			agentrechargeService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出AgentRecharge到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户游戏ID");	//1
		titles.add("用户昵称");	//2
		titles.add("冲卡数量");	//3
		titles.add("冲卡人");	//4
		titles.add("冲卡时间");	//5
		titles.add("金额");	//6
		dataMap.put("titles", titles);
		List<PageData> varOList = agentrechargeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("USER_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("NICK_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("CHARGE_NUM"));	    //3
			vpd.put("var4", varOList.get(i).getString("CREATOR"));	    //4
			vpd.put("var5", varOList.get(i).getString("CREATOR_TIME"));	    //5
			vpd.put("var6", varOList.get(i).getString("MONEY"));	    //6
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/buycard")
	public ModelAndView buycard(Page page) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"列表AgentRecharge");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart=pd.getString("lastStart");
		String lastEnd= pd.getString("lastEnd");
		if(lastEnd !=null && !"".equals(lastEnd)){
			pd.put("lastEnd",lastEnd+" 23:59:59" );
		}
		if(lastStart !=null && !"".equals(lastStart)){
			pd.put("lastStart",lastStart+" 00:00:00" );
		}
		
		if(lastStart != null && lastEnd != null ){
			if(lastStart.equals(lastEnd)){
				pd.put("today",lastEnd );
				pd.remove("lastEnd");
				pd.remove("lastStart");
			}
		}
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("USER_ID", user.getNUMBER());
		page.setPd(pd);
		List<PageData>	varList = agentrechargeService.findbuycardlistPage(page);	//列出AgentRecharge列表
		pd.put("lastEnd",lastEnd );
		pd.put("lastStart",lastStart);
		mv.setViewName("game/agentRecharge/agentbuycard_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
}
