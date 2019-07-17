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
import com.bets.service.game.FightResultManager;
import com.bets.util.AppUtil;
import com.bets.util.Jurisdiction;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Tools;

/** 
 * 说明：房间战绩表
 * 创建人：bets 
 * 创建时间：2016-09-19
 */
@Controller
@RequestMapping(value="/fightresult")
public class FightResultController extends BaseController {
	
	String menuUrl = "fightresult/list.do"; //菜单地址(权限用)
	@Resource(name="fightresultService")
	private FightResultManager fightresultService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增FightResult");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("FIGHTRESULT_ID", this.get32UUID());	//主键
		pd.put("USER_ID", this.get32UUID());	//用户Id
		pd.put("NICK_NAME", pd.getString("NICK_NAME"));	//用户昵称
		pd.put("SCORE", "0");	//分数
		pd.put("START_TIME", Tools.date2Str(new Date()));	//开始时间
		pd.put("END_TIME", Tools.date2Str(new Date()));	//结束时间
		fightresultService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception {
		logBefore(logger, Jurisdiction.getUsername()+"删除FightResult");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String s = pd.getString("STATUS");
			if("0".equals(s)){
				fightresultService.delete(pd);
				out.write("success");
				out.close();
			}
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改FightResult");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		fightresultService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表FightResult");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String START_TIME=pd.getString("lastStart");
		String END_TIME= pd.getString("lastEnd");
		//pd.put("STATUS",  pd.getString("selOpt"));
		if(END_TIME !=null && !"".equals(END_TIME)){
			pd.put("END_TIME",END_TIME+" 23:59:59" );
		}
		if(START_TIME !=null && !"".equals(START_TIME)){
			pd.put("START_TIME",START_TIME+" 00:00:00" );
		}
		
		if(START_TIME != null && END_TIME != null ){
			if(START_TIME.equals(END_TIME)){
				pd.put("today",END_TIME );
				pd.remove("END_TIME");
				pd.remove("START_TIME");
			}
		}
		page.setPd(pd);
		List<PageData>	varList = fightresultService.list(page);	//列出FightResult列表
		pd.put("END_TIME",END_TIME);
		pd.put("START_TIME",START_TIME);
		mv.setViewName("game/fightresult/fightresult_list");
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
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("game/fightresult/fightresult_edit");
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
		pd = fightresultService.findById(pd);	//根据ID读取
		mv.setViewName("game/fightresult/fightresult_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除FightResult");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			fightresultService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出FightResult到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义格式，不显示毫秒
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("房间号");	//1
		titles.add("用户Id");	//2
		titles.add("用户昵称");	//3
		titles.add("分数");	//4
		titles.add("状态");	//5
		titles.add("开始时间");	//6
		titles.add("结束时间");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = fightresultService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("ROOM_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("USER_ID"));	    //2
			vpd.put("var3", varOList.get(i).getString("NICK_NAME"));	    //3
			vpd.put("var4", varOList.get(i).get("SCORE").toString());	//4
			vpd.put("var5", varOList.get(i).getString("STATUS"));	    //5
			vpd.put("var6", df.format(varOList.get(i).get("START_TIME")));	    //6
			vpd.put("var7", df.format(varOList.get(i).get("END_TIME")));	    //7
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
