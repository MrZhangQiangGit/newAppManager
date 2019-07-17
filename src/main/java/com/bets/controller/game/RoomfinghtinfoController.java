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

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.entity.Page;
import com.bets.service.game.RoomfinghtinfoManager;
import com.bets.util.AppUtil;
import com.bets.util.Jurisdiction;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Tools;

/** 
 * 说明：房间战绩明细表
 * 创建人：bets 
 * 创建时间：2016-09-19
 */
@Controller
@RequestMapping(value="/roomfinghtinfo")
public class RoomfinghtinfoController extends BaseController {
	
	String menuUrl = "roomfinghtinfo/list.do"; //菜单地址(权限用)
	@Resource(name="roomfinghtinfoService")
	private RoomfinghtinfoManager roomfinghtinfoService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Roomfinghtinfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROOMFINGHTINFO_ID", this.get32UUID());	//主键
		pd.put("ROOM_ID", "");	//房间号
		pd.put("USER_ID", "");	//用户ID
		pd.put("NICKNAME", "");	//用户昵称
		pd.put("ORDER_NO", "");	//对局序号
		pd.put("SCORE", "0");	//对局分数
		pd.put("START_TIME", Tools.date2Str(new Date()));	//开始时间
		pd.put("END_TIME", Tools.date2Str(new Date()));	//结束时间
		roomfinghtinfoService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Roomfinghtinfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		roomfinghtinfoService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Roomfinghtinfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		roomfinghtinfoService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Roomfinghtinfo");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String USER_ID = pd.getString("USER_ID");
		pd.put("USER_ID", USER_ID);
		page.setPd(pd);
		List<PageData>	varList = roomfinghtinfoService.list(page);	//列出Roomfinghtinfo列表
		mv.setViewName("game/roomfinghtinfo/roomfinghtinfo_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
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
		pd = roomfinghtinfoService.findById(pd);	//根据ID读取
		mv.setViewName("game/roomfinghtinfo/roomfinghtinfo_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Roomfinghtinfo");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			roomfinghtinfoService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Roomfinghtinfo到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		Tools tools = new Tools();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("房间号");	//1
		titles.add("用户ID");	//2
		titles.add("用户昵称");	//3
		titles.add("对局序号");	//4
		titles.add("对局分数");	//5
		titles.add("开始时间");	//6
		titles.add("结束时间");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = roomfinghtinfoService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("ROOM_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("USER_ID"));	    //2
			vpd.put("var3", varOList.get(i).getString("NICKNAME"));	    //3
			vpd.put("var4", varOList.get(i).getString("ORDER_NO"));	    //4
			vpd.put("var5", varOList.get(i).get("SCORE").toString());	//5
			
			vpd.put("var6", tools.date2Str((Date) varOList.get(i).get("START_TIME")));	    //6
			vpd.put("var7", tools.date2Str((Date) varOList.get(i).get("END_TIME")));	    //7
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
