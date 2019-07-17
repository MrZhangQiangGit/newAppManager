package com.bets.controller.game;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.entity.Page;
import com.bets.service.game.RoomManager;
import com.bets.service.game.RoomNumberManager;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;
import com.bets.util.Tools;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value="/roomNumber")
public class RoomNumberController extends BaseController{
	
	String menuUrl = "roomNumber/list.do"; //菜单地址(权限用)
	@Resource(name="roomNumberService")
	private RoomNumberManager roomNumberService;
	
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		System.out.println("房间号");
		logBefore(logger, Jurisdiction.getUsername()+"列表RoomNumber");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		//List<PageData>	varList = roomService.list(page);	//列出MajRoom列表
		mv.setViewName("game/room/roomNumber_list");
		//mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	
	/**先判断房间是否存在
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/judge")
	public void judge(HttpServletResponse response,String ROOM_ID1) throws Exception{
		System.out.println("判断----------------------------------------------------");
		int count=roomNumberService.judge(ROOM_ID1);
		PrintWriter out = response.getWriter();
		if(count==0){
			out.println("0");
		}else if(count>0){
			out.println("1");
		}
	}
	
	/**重置房间状态
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/reset")
	public void edit(HttpServletResponse response,String ROOM_ID1) throws Exception{
		System.out.println("重置");
		roomNumberService.reset(ROOM_ID1);
		response.setCharacterEncoding("utf-8");//输出语句使用的编码
		PrintWriter out = response.getWriter();
        out.println("成功");
	}

	
}
