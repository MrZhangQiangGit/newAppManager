package com.bets.controller.game.recharge;

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
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.entity.Page;
import com.bets.entity.system.User;
import com.bets.service.game.recharge.RechargeManager;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Tools;

import net.sf.json.JSONArray;

/** 
 * 说明：玩家冲卡
 * 创建人：bets 
 * 创建时间：2016-11-08
 */
@Controller
@RequestMapping(value="/recharge")
public class RechargeController extends BaseController {
	
	String menuUrl = "recharge/list.do"; //菜单地址(权限用)
	@Resource(name="rechargeService")
	private RechargeManager rechargeService;
	
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
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
		List<PageData>	varList = rechargeService.list(page);	//列出Recharge列表
		pd.put("lastEnd",lastEnd );
		pd.put("lastStart",lastStart);
		mv.setViewName("game/recharge/recharge_list");
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
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String num = user.getNUMBER();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> list = rechargeService.listAlluser(pd);
		JSONArray jsonList = JSONArray.fromObject(list);
		mv.setViewName("game/recharge/recharge_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		mv.addObject("num", num);
		mv.addObject("list", jsonList);
		return mv;
	}	
	
	@RequestMapping("/findObjectByid")
	public void findObjectByid(HttpServletResponse response,String id) throws Exception {
		
		PageData pd = rechargeService.findMun(id);
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
		PageData p = rechargeService.findByname(pd);//查询sys_user
		String number = p.getString("NUMBER");
		
		//页面传过来的值
		String gross1 = pd.getString("CHARGE_NUM");//充值房卡数量
		int gross = Integer.parseInt(gross1);//转换为Int
		
		//查询玩家中数据库剩余房卡根据被充值的ID
		PageData pasa = rechargeService.findCardById(pd);
		int surplusCard = (int)pasa.get("ROOM_CARD");//玩家剩余的房卡 pasa
		
		int roomCardSub =0;
		int totalAdd = 0;
		
		//查询代理表中登陆人数据库房卡数和销售总量根据充值人的id
			pd.put("agent_code", number);//充值人的ID
			PageData pas = rechargeService.findMunById2(pd);//查询m_agent
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
		
			if("admin".equals(user.getUSERNAME())){//登陆为admin
				String sum = (surplusCard+gross)+"";//房卡和（玩家）
				pd.put("ROOM_CARD", sum);
				rechargeService.editAppUser(pd);
				String totalSum = (totalAdd+gross)+"";//销售总量和（代理商）
				pd.put("sale_total", totalSum);
				rechargeService.editAgent3(pd);
				pd.put("CREATOR", user.getUSERNAME());	//创建用户名
				pd.put("CHARGE_NAME", user.getNAME());
				pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
				pd.put("RECHARGE_ID", this.get32UUID());	//主键
				rechargeService.save(pd);
				mv.addObject("msg","success");
				mv.setViewName("save_result");
			}else{//不是admin登陆
				if(roomCardSub>=gross){
					String sum = (surplusCard+gross)+"";//房卡和（玩家）
					pd.put("ROOM_CARD", sum);
					rechargeService.editAppUser(pd);
					String bad = (roomCardSub-gross)+"";  //房卡差（代理商）
					pd.put("card_num", bad);
					String totalSum = (totalAdd+gross)+"";//销售总量和（代理商）
					pd.put("sale_total", totalSum);
					rechargeService.editAgent2(pd);
					pd.put("CREATOR", user.getUSERNAME());	//创建用户名
					pd.put("CHARGE_NAME", user.getNAME());
					pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
					pd.put("RECHARGE_ID", this.get32UUID());	//主键
					rechargeService.save(pd);
					mv.addObject("msg","success");
					mv.setViewName("save_result");
				}
			}
			return mv;
		}
	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
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
		dataMap.put("titles", titles);
		List<PageData> varOList = rechargeService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("USER_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("NICK_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("CHARGE_NUM"));	    //3
			vpd.put("var4", varOList.get(i).getString("CREATOR"));	    //4
			vpd.put("var5", Tools.date2Str((Date)varOList.get(i).get("CREATOR_TIME")));	//5
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
}
