package com.bets.controller.example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.entity.Page;
import com.bets.service.example.treetest.TreeTestManager;
import com.bets.util.AppUtil;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;
import com.bets.util.Jurisdiction;
import com.bets.util.Tools;

/** 
 * 说明：树形结构测试
 * 创建人：bets
 * 创建时间：2016-09-09
 */
@Controller
@RequestMapping(value="/treetest")
public class TreeTestController extends BaseController {
	
	String menuUrl = "treetest/list.do"; //菜单地址(权限用)
	@Resource(name="treetestService")
	private TreeTestManager treetestService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增TreeTest");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TREETEST_ID", this.get32UUID());	//主键
		treetestService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String TREETEST_ID) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除TreeTest");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} 					//校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("TREETEST_ID", TREETEST_ID);
		String errInfo = "success";
		if(treetestService.listByParentId(TREETEST_ID).size() > 0){		//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			treetestService.delete(pd);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改TreeTest");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		treetestService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表TreeTest");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 	//校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");								//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String TREETEST_ID = null == pd.get("TREETEST_ID")?"":pd.get("TREETEST_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			TREETEST_ID = pd.get("id").toString();
		}
		pd.put("TREETEST_ID", TREETEST_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = treetestService.list(page);			//列出TreeTest列表
		mv.setViewName("example/treetest/treetest_list");
		mv.addObject("pd", treetestService.findById(pd));				//传入上级所有信息
		mv.addObject("TREETEST_ID", TREETEST_ID);			//上级ID
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());								//按钮权限
		return mv;
	}

	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listTree")
	public ModelAndView listTree(Model model,String TREETEST_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			JSONArray arr = JSONArray.fromObject(treetestService.listTree("0"));
			String json = arr.toString();
			json = json.replaceAll("TREETEST_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subTreeTest", "nodes").replaceAll("hasTreeTest", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("TREETEST_ID",TREETEST_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("example/treetest/treetest/treetest_tree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
		String TREETEST_ID = null == pd.get("TREETEST_ID")?"":pd.get("TREETEST_ID").toString();
		pd.put("TREETEST_ID", TREETEST_ID);					//上级ID
		mv.addObject("pds",treetestService.findById(pd));				//传入上级所有信息
		mv.addObject("TREETEST_ID", TREETEST_ID);			//传入ID，作为子级ID用
		mv.setViewName("example/treetest/treetest_edit");
		mv.addObject("msg", "save");
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
		String TREETEST_ID = pd.getString("TREETEST_ID");
		pd = treetestService.findById(pd);							//根据ID读取		
		mv.addObject("pd", pd);													//放入视图容器
		pd.put("TREETEST_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",treetestService.findById(pd));				//传入上级所有信息
		mv.addObject("TREETEST_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("TREETEST_ID",TREETEST_ID);					//复原本ID
		pd = treetestService.findById(pd);							//根据ID读取
		mv.setViewName("example/treetest/treetest_edit");
		mv.addObject("msg", "edit");
		return mv;
	}	
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出TreeTest到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("编码");	//1
		titles.add("描述");	//2
		dataMap.put("titles", titles);
		List<PageData> varOList = treetestService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("REMARK"));	    //2
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
