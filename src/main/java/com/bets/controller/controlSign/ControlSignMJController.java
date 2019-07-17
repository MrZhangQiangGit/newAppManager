package com.bets.controller.controlSign;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import com.bets.service.game.controlSign.ControlSignManager;
import com.bets.util.AppUtil;
import com.bets.util.Const;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Jurisdiction;
import com.bets.util.Tools;

import net.sf.json.JSONArray;

/** 
 * 说明：开发测试
 * 创建人：bets 
 * 创建时间：2016-12-31
 */
@Controller
@RequestMapping(value="/controlmjsign")
public class ControlSignMJController extends BaseController {
	
	String menuUrl = "controlmjsign/list.do"; //菜单地址(权限用)
	@Resource(name="controlsignService")
	private ControlSignManager controlsignService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增ControlSign");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		Session session = Jurisdiction.getSession();
		User user =(User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		String type = pd.getString("TYPE");
		if("斗地主".equals(type)){
			pd.put("TYPE", "2");
		}else if ("麻将".equals(type)) {
			pd.put("TYPE", "1");
		}
		pd.put("MCONTROLSIGN_ID", this.get32UUID());	//主键
		pd.put("CREATOR", user.getUSERNAME());	//创建人
		pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("STATUS", "1");//状态默认有效
		pd.put("UPDATOR", "");	//修改人
		controlsignService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除ControlSign");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		controlsignService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改ControlSign");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		Session session = Jurisdiction.getSession();
		User user =(User) session.getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		String type = pd.getString("TYPE");
		if("斗地主".equals(type)){
			pd.put("TYPE", "2");
		}else if ("麻将".equals(type)) {
			pd.put("TYPE", "1");
		}
		pd.put("UPDATOR", user.getUSERNAME());	//修改人
		pd.put("UPDATOR_TIME", Tools.date2Str(new Date()));	//修改时间
		controlsignService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表ControlSign");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession(); // session
		User session_user = (User) session.getAttribute(Const.SESSION_USER); // user
		pd.put("loguser", session_user.getUSERNAME());//登录人
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		pd.put("TYPE","1" );
		page.setPd(pd);
		List<PageData>	varList = controlsignService.list(page);	//列出ControlSign列表
		List<PageData> newlist = new ArrayList<>();
		for(int i=0;i<varList.size();i++){
			PageData npd = new PageData();
			npd.put("USERID",varList.get(i).get("USERID"));
			npd.put("TYPE",varList.get(i).get("TYPE"));
			String content = varList.get(i).getString("CONTENT");
			String[] contents = content.split(",");
			npd.put("CONTENT",contents);
			npd.put("CREATOR",varList.get(i).get("CREATOR"));
			npd.put("CREATOR_TIME",varList.get(i).get("CREATOR_TIME"));
			npd.put("UPDATOR",varList.get(i).get("UPDATOR"));
			npd.put("UPDATOR_TIME",varList.get(i).get("UPDATOR_TIME"));
			npd.put("STATUS",varList.get(i).get("STATUS"));
			npd.put("MCONTROLSIGN_ID",varList.get(i).get("MCONTROLSIGN_ID"));
			newlist.add(npd);
		}
		mv.setViewName("game/controlSign/controlsign_list_mj");
		mv.addObject("varList", newlist);
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
		mv.setViewName("game/controlSign/controlsign_edit_mj");
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
		pd = controlsignService.findById(pd);	//根据ID读取
		String content = pd.getString("CONTENT");
		String[] contents = content.split(",");
		pd.put("CONTENTS",contents);
		mv.setViewName("game/controlSign/controlsign_edit_mj_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**
	 * 校验id
	 */
	@RequestMapping("/checkId")
	public void checkId(HttpServletResponse response,HttpServletRequest request) throws Exception {
		PageData pd =new PageData();
		pd= this.getPageData();
		List<PageData> br=controlsignService.findByUserId(pd) ;//根据游戏ID获取数据
		String check="t";
		if (null!=br && br.size()>0) {
			pd.put("check", check);
		}else{
			check="f";
			pd.put("check", check);
		}
	        response.setContentType("application/json");
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");
	        response.setCharacterEncoding("UTF-8");
	        PrintWriter out= null;
	        out = response.getWriter();
	        List<PageData> xx = new ArrayList<>();
	        		xx.add(pd);
	        JSONArray arry = JSONArray.fromObject(xx);
	        out.print(arry);
	        out.flush();
	        out.close();
	}
	
	/**
	 * 插入新纪录将以前记录置为无效
	 */
	@RequestMapping("/updatevalid")
	public void updatevalid(HttpServletResponse response,HttpServletRequest request) throws Exception {
		PageData pd =new PageData();
		pd= this.getPageData();
		pd.put("type","1");
		controlsignService.updatevalid(pd);//根据游戏ID更新状态
		PrintWriter out= null;
        out = response.getWriter();
        JSONArray arry=null;
		out.print(arry);
	    out.flush();
	    out.close();
	}
	
	/**
	 * 所有状态置为无效
	 */
	@RequestMapping("/toinvalid")
	public void toinvalid(HttpServletResponse response,HttpServletRequest request) throws Exception {
		PageData pd = new PageData();
		pd=this.getPageData();
		controlsignService.toinvalid(pd);
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除ControlSign");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			controlsignService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出ControlSign到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("用户游戏ID");	//1
		titles.add("棋牌类型：1 麻将 ，2斗地主");	//2
		titles.add("牌内容，形式如下：1，2，3");	//3
		titles.add("创建人");	//4
		titles.add("创建时间");	//5
		titles.add("修改人");	//6
		titles.add("修改时间");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = controlsignService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("USERID"));	    //1
			vpd.put("var2", varOList.get(i).getString("TYPE"));	    //2
			vpd.put("var3", varOList.get(i).getString("CONTENT"));	    //3
			vpd.put("var4", varOList.get(i).getString("CREATOR"));	    //4
			vpd.put("var5", varOList.get(i).getString("CREATOR_TIME"));	    //5
			vpd.put("var6", varOList.get(i).getString("UPDATOR"));	    //6
			vpd.put("var7", varOList.get(i).getString("UPDATOR_TIME"));	    //7
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
	
	/**总控列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/dictlist")
	public ModelAndView dictlist(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表ControlSign");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd =new PageData();
		pd= this.getPageData();
		pd.put("MDICT_ID", "1");
		List<PageData> varList = controlsignService.dict();	//列出ControlSign列表
		mv.setViewName("game/dict/dict_list_dz");
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	 /**去开发测试开关修改页面
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/todictEdit")
		public ModelAndView todictEdit()throws Exception{
			ModelAndView mv = this.getModelAndView();
			PageData pd =new PageData();
			pd= this.getPageData();
			mv.setViewName("game/dict/dict_edit_dz");
			mv.addObject("msg", "edit");
			mv.addObject("pd",pd);
			return mv;
		}
		/**开发测试开关修改
		 * @param
		 * @throws Exception
		 */
		@RequestMapping(value="/dictedit")
		public ModelAndView dictedit() throws Exception{
			logBefore(logger, Jurisdiction.getUsername()+"修改ControlSign");
			if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
			ModelAndView mv = this.getModelAndView();
			Session session = Jurisdiction.getSession();
			User user =(User) session.getAttribute(Const.SESSION_USER);
			PageData pd = new PageData();
			pd = this.getPageData();
			controlsignService.updict(pd);
			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}
}
