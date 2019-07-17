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
import com.bets.service.game.RoomRuleManager;
import com.bets.util.AppUtil;
import com.bets.util.Jurisdiction;
import com.bets.util.ObjectExcelView;
import com.bets.util.PageData;

/** 
 * 说明：房间规则表
 * 创建人：bets 
 * 创建时间：2016-09-19
 */
@Controller
@RequestMapping(value="/roomrule")
public class RoomRuleController extends BaseController {
	
	String menuUrl = "roomrule/list.do"; //菜单地址(权限用)
	@Resource(name="roomruleService")
	private RoomRuleManager roomruleService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增RoomRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ROOMRULE_ID", this.get32UUID());	//主键
		pd.put("ROOM_ID", "");	//房间号
		pd.put("RULE", "");	//房间规则
		pd.put("NUM", "");	//局数
		pd.put("TOP", "");	//封顶
		pd.put("ZMSF", "");	//自摸算法
		pd.put("DGSF", "");	//点杠算法
		pd.put("HSZ", "");	//换三张
		pd.put("YJJD", "");	//幺九将对
		pd.put("MQZZ", "");	//门清中张
		pd.put("TDH", "");	//天地胡
		roomruleService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除RoomRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		roomruleService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改RoomRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		roomruleService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表RoomRule");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String ROOM_ID = pd.getString("ROOM_ID");
		pd.put("ROOM_ID", ROOM_ID);
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = roomruleService.list(page);	//列出RoomRule列表
		mv.setViewName("game/roomRule/rule");
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
		mv.setViewName("game/roomRule/roomrule_edit");
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
		pd = roomruleService.findById(pd);	//根据ID读取
		mv.setViewName("game/roomRule/roomrule_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除RoomRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			roomruleService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出RoomRule到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("房间号");	//1
		titles.add("房间规则");	//2
		titles.add("局数");	//3
		titles.add("封顶");	//4
		titles.add("自摸算法");	//5
		titles.add("点杠算法");	//6
		titles.add("换三张");	//7
		titles.add("幺九将对");	//8
		titles.add("门清中张");	//9
		titles.add("天地胡");	//10
		dataMap.put("titles", titles);
		List<PageData> varOList = roomruleService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("ROOM_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("RULE"));	    //2
			vpd.put("var3", varOList.get(i).getString("NUM"));	    //3
			vpd.put("var4", varOList.get(i).getString("TOP"));	    //4
			vpd.put("var5", varOList.get(i).getString("ZMSF"));	    //5
			vpd.put("var6", varOList.get(i).getString("DGSF"));	    //6
			vpd.put("var7", varOList.get(i).getString("HSZ"));	    //7
			vpd.put("var8", varOList.get(i).getString("YJJD"));	    //8
			vpd.put("var9", varOList.get(i).getString("MQZZ"));	    //9
			vpd.put("var10", varOList.get(i).getString("TDH"));	    //10
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
