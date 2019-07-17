package com.bets.controller.game;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
import com.bets.service.game.UserManager;
import com.bets.service.game.agent.AgentManager;
import com.bets.util.AppUtil;
import com.bets.util.Const;
import com.bets.util.Jurisdiction;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Tools;

/** 
 * 说明：麻将用户查询
 * 创建人：bets 
 * 创建时间：2016-09-18
 */
@Controller
@RequestMapping(value="/majuser")
public class MajUserController extends BaseController {
	
	String menuUrl = "majuser/list.do"; //菜单地址(权限用)
	@Resource(name="majUserService")
	private UserManager userService;
	@Resource(name="agentService")
	private AgentManager agentService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增MajUser");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MAJUSER_ID", this.get32UUID());	//主键
		pd.put("USER_ID", this.get32UUID());	//用户Id
		pd.put("CREAT_TIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("OPT_TIME", Tools.date2Str(new Date()));	//操作时间
		pd.put("ROOM_CARD", 0);
		userService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除MajUser");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		userService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改MajUser");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("OPT_TIME", Tools.date2Str(new Date()));	//操作时间
		userService.edit(pd);
		PageData p = agentService.findByEdit(pd);
		if(p != null){
			agentService.editName(pd);
		}
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
		logBefore(logger, Jurisdiction.getUsername()+"列表MajUser");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		String id =pd.getString("AGENT_CODE");
		if(id !=null && !"".equals(id) ){
			pd.put("AGENT_CODE",id);//代理商id
		}else{
			pd.put("AGENT_CODE",user.getNUMBER() );//代理商id
		}
		
		PageData agentinfo = agentService.findById(pd);
		pd.put("YQ",agentinfo.getString("YQ_CODE") );
		String province = pd.getString("selOpt");
		String keywords = pd.getString("keywords");				//关键词检索条件
		String lastStart = pd.getString("lastStart");	//开始时间
		String lastEnd = pd.getString("lastEnd");		//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 23:59:59");
		} 
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		if (null != province && !"".equals(province)) {
			pd.put("PROVINCE", province);
		}
		if(lastStart != null && lastEnd != null ){
			if(lastStart.equals(lastEnd)){
				pd.put("today",lastEnd );
				pd.remove("lastStart");
				pd.remove("lastEnd");
			}
		}
		page.setPd(pd);
		List<PageData>	varList = userService.list(page);	//列出MajUser列表
		List<PageData> listProvince =  userService.listProvince();
		//List<PageData> list = new ArrayList<PageData>();
		/*for(PageData p : varList){
			String stu = p.getString("SEX");
			if("0".equals(stu)){
				p.put("SEX", "男");
			}else if("1".equals(stu)){
				p.put("SEX", "女");
			}
			list.add(p);
		}*/
		pd.put("lastStart",lastStart);
		pd.put("lastEnd",lastEnd);
		mv.setViewName("game/majuser/majuser_list");
		mv.addObject("varList", varList);
		mv.addObject("listProvince", listProvince);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("game/majuser/majuser_add");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
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
		pd = userService.findById(pd);	//根据ID读取
		mv.setViewName("game/majuser/majuser_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除MajUser");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			userService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出MajUser到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("微信用户opeid");	//1
		titles.add("昵称");	//2
		titles.add("用户Id");	//3
		titles.add("状态");	//4
		titles.add("性别");	//5
		titles.add("省份");	//6
		titles.add("城市");	//7
		titles.add("手机号码");	//8
		titles.add("房卡余额数");	//9
		titles.add("创建时间");	//10
		titles.add("操作时间");	//11
		dataMap.put("titles", titles);
		List<PageData> varOList = userService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("OPENID"));	    //1
			vpd.put("var2", varOList.get(i).getString("NICK_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("USER_ID"));	    //3
			vpd.put("var4", varOList.get(i).getString("STATUS"));	    //4
			vpd.put("var5", varOList.get(i).getString("SEX"));	    //5
			vpd.put("var6", varOList.get(i).getString("PROVINCE"));	    //6
			vpd.put("var7", varOList.get(i).getString("CITY"));	    //7
			vpd.put("var8", varOList.get(i).getString("PHONE"));	    //8
			vpd.put("var9", varOList.get(i).get("ROOM_CARD").toString());	//9
			Date var10 = (Date) varOList.get(i).get("CREAT_TIME");
			Date var11 = (Date) varOList.get(i).get("OPT_TIME");
			vpd.put("var10",Tools.date2Str(var10));	    //10
			vpd.put("var11", Tools.date2Str(var11));	    //11
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
