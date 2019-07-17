package com.bets.controller.game.serverManager;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.entity.Page;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/serverManager")
public class serverManagerController extends BaseController{
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("game/serverManager/serverManager");
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	@RequestMapping(value="/eidt")
	public ModelAndView eidt(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		String cmdstring = "/usr/local/tomcatGame/bin/startup.sh";
		 Process proc = Runtime.getRuntime().exec(cmdstring);
		 proc.waitFor(); //阻塞，直到上述命令执行完
//		mv.setViewName("game/serverManager/serverManager");
//		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	/**
	 * 关闭服务器
	 * @param cardNo
	 * @throws Exception 
	 */
	@RequestMapping("/stopserver")
	public void checkagent(HttpServletResponse response,HttpServletRequest request) throws Exception {
	    response.setContentType("application/json");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        String cmdstring = "/usr/local/tomcatGame/bin/killtomcat.sh";
		 Process proc = Runtime.getRuntime().exec(cmdstring);
		 proc.waitFor(); //阻塞，直到上述命令执行完
		 PageData pd = new PageData();
		 pd.put("status", 1);
		 JSONArray arry = JSONArray.fromObject(pd);
        PrintWriter out= null;
        out = response.getWriter();
        out.print(arry);
        out.flush();
        out.close();
	}
	/**
	 * 启动服务器
	 * @param cardNo
	 * @throws Exception 
	 */
	@RequestMapping("/startserver")
	public void startserver(HttpServletResponse response,HttpServletRequest request) throws Exception {
	    response.setContentType("application/json");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        String cmdstring = "/usr/local/tomcatGame/bin/startup.sh";
		 Process proc = Runtime.getRuntime().exec(cmdstring);
		 proc.waitFor(); //阻塞，直到上述命令执行完
		 PageData pd = new PageData();
		 pd.put("status", 1);
		 JSONArray arry = JSONArray.fromObject(pd);
        PrintWriter out= null;
        out = response.getWriter();
        out.print(arry);
        out.flush();
        out.close();
	}
}
