package com.bets.controller.game.mfreecfg;

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
import com.bets.util.AppUtil;
import com.bets.util.Const;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Jurisdiction;
import com.bets.util.Tools;
import com.bets.service.game.mfreecfg.MfreecfgManager;

/** 
 * 说明：免费开放设置
 * 创建人：bets 
 * 创建时间：2016-11-08
 */
@Controller
@RequestMapping(value="/mfreecfg")
public class MfreecfgController extends BaseController {
	
	String menuUrl = "mfreecfg/list.do"; //菜单地址(权限用)
	@Resource(name="mfreecfgService")
	private MfreecfgManager mfreecfgService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("CREATOR", user.getUSERNAME());	//创建用户名
		pd.put("CREATOR_TIME", Tools.date2Str(new Date()));	//创建时间
		pd.put("MFREECFG_ID", this.get32UUID());	//主键
		mfreecfgService.save(pd);
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
		PageData pd = new PageData();
		pd = this.getPageData();
		mfreecfgService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("UPDATOR", user.getUSERNAME());	//修改人
		pd.put("UPDATOR_TIME", Tools.date2Str(new Date()));	//修改时间
		mfreecfgService.edit(pd);
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
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastEnd = pd.getString("lastEnd");               //结束日期
		if(null != lastEnd && !"".equals(lastEnd)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Date lastDate = sdf.parse(lastEnd);
			lastEnd = sdf.format(new Date(lastDate.getTime() + 24 * 60 * 60 * 1000));
			pd.put("lastEnd", lastEnd);
		}
		page.setPd(pd);
		List<PageData>	varList = mfreecfgService.list(page);	//列出Mfreecfg列表
		List<PageData> list = new ArrayList<PageData>();
		for(PageData p : varList){
			String stu = p.getString("STATUS");
			if("0".equals(stu)){
				p.put("STATUS", "无效");
			}else if("1".equals(stu)){
				p.put("STATUS", "有效");
			}
			list.add(p);
		}
		mv.setViewName("game/mfreecfg/mfreecfg_list");
		mv.addObject("varList", list);
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
		mv.setViewName("game/mfreecfg/mfreecfg_edit");
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
		pd = mfreecfgService.findById(pd);	//根据ID读取
		mv.setViewName("game/mfreecfg/mfreecfg_edit");
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
			mfreecfgService.deleteAll(ArrayDATA_IDS);
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
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("开始时间");	//1
		titles.add("结束时间");	//2
		titles.add("状态");	//3
		titles.add("创建人");	//4
		titles.add("创建时间");	//5
		titles.add("修改人");	//6
		titles.add("修改时间");	//7
		dataMap.put("titles", titles);
		List<PageData> varOList = mfreecfgService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("START_TIME"));	    //1
			vpd.put("var2", varOList.get(i).getString("END_TIME"));	    //2
			vpd.put("var3", varOList.get(i).getString("STATUS"));	    //3
			vpd.put("var4", varOList.get(i).getString("CREATOR"));	    //4
			vpd.put("var5", Tools.date2Str((Date)varOList.get(i).get("CREATOR_TIME")));	//5
			vpd.put("var6", varOList.get(i).getString("UPDATOR"));	    //6
			if(varOList.get(i).get("UPDATOR_TIME")!=null){
				vpd.put("var7", Tools.date2Str((Date)varOList.get(i).get("UPDATOR_TIME")));//7
			}else{
				vpd.put("var7", "");
			}
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
