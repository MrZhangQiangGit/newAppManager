package com.bets.controller.game;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bets.controller.base.BaseController;
import com.bets.service.game.AppControllerManager;
import com.bets.util.PageData;
import com.bets.util.WebUtil;
/**
 * app终端版本升级控制
 * @author zhangjie
 *
 */
@Controller
@RequestMapping(value="/m")
public class AppController extends BaseController {

	
	@Resource(name="appcontrollerService")
	private AppControllerManager appManager;

	/**
	 * 版本检测
	 * @param req
	 * @param resp
	 * @throws Exception 
	 */
	@RequestMapping("/checkversion")
	public void checkAppVer(HttpServletRequest req,HttpServletResponse resp) throws Exception {
		
		JSONObject json = new JSONObject() ;
		String reqClient = req.getParameter("clientVer");
		double rclient = Double.parseDouble(reqClient);
		System.out.println(reqClient);
		String type=req.getParameter("type");
		System.out.println(type);
		
		System.out.println("手机端请求版本信息："+reqClient+",终端类型："+type);
		
		PageData pd=new PageData();
		pd.put("TYPE", type);
		PageData rst=appManager.findById(pd);
		String serverVersion=rst.getString("VERSION");
		double sversion = Double.parseDouble(serverVersion);
		if(sversion> rclient){//有新版本
			json.put("UDPTE","1") ;
			json.put("VERSION_NO",serverVersion);
			json.put("APP_URL",rst.getString("URL"));
			json.put("VERSION_MSG", rst.getString("REMARK"));
		}else{//无新版本
			json.put("UDPTE","2") ;
		}
		String rsg=JSON.toJSONString(json);
		System.out.println(rsg);
		WebUtil.sendResponseUTF8(resp, rsg);
	}
}
