package com.bets.controller.system.login;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bets.controller.base.BaseController;
import com.bets.entity.system.Menu;
import com.bets.entity.system.Role;
import com.bets.entity.system.User;
import com.bets.service.fhoa.datajur.DatajurManager;
import com.bets.service.game.agent.AgentManager;
import com.bets.service.game.cardStatistics.CardStatisticsManager;
import com.bets.service.game.moneyStatistics.MoneyStatisticsManager;
import com.bets.service.system.appuser.AppuserManager;
import com.bets.service.system.buttonrights.ButtonrightsManager;
import com.bets.service.system.fhbutton.FhbuttonManager;
import com.bets.service.system.menu.MenuManager;
import com.bets.service.system.role.RoleManager;
import com.bets.service.system.user.UserManager;
import com.bets.util.AppUtil;
import com.bets.util.Const;
import com.bets.util.DateUtil;
import com.bets.util.Jurisdiction;
import com.bets.util.PageData;
import com.bets.util.RightsHelper;
import com.bets.util.Tools;
/**
 * 总入口
 * @author bets
 * 修改日期：2015/11/2
 */
/**
 * @author Administrator
 *
 */
@Controller
public class LoginController extends BaseController {

	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="buttonrightsService")
	private ButtonrightsManager buttonrightsService;
	@Resource(name="fhbuttonService")
	private FhbuttonManager fhbuttonService;
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	@Resource(name="datajurService")
	private DatajurManager datajurService;
	@Resource(name="cardstatisticsService")
	private CardStatisticsManager cardstatisticsService;
	@Resource(name="moneystatisticsService")
	private MoneyStatisticsManager moneystatisticsService;
	@Resource(name="agentService")
	private AgentManager agentService;
	
	/**访问登录页
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
//		mv.setViewName("jsp/system/index/login.jsp");
		mv.setViewName("html/html/logins/login.html");
		mv.addObject("pd",pd);
		
		return mv;
//		return "html/html/logins/login.html";
	}
	
	/**请求登录，验证用户
	 * @return
	 * @throws Exception
	 */
	//@RequestMapping(value="/login_login" ,produces="application/json;charset=UTF-8")
	@ResponseBody
	public Object login(HttpServletRequest request)throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString("KEYDATA").split(",");
		if(null != KEYDATA && KEYDATA.length == 3){
			Session session = Jurisdiction.getSession();
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
			String code = KEYDATA[2];
			if(null == code || "".equals(code)){//判断效验码
				errInfo = "nullcode"; 			//效验码为空
			}else{
				String USERNAME = KEYDATA[0];	//登录过来的用户名
				String PASSWORD  = KEYDATA[1];	//登录过来的密码
				pd.put("USERNAME", USERNAME);
				if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){		//判断登录验证码
					String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();	//密码加密
					pd.put("PASSWORD", passwd);
					pd = userService.getUserByNameAndPwd(pd);	//根据用户名和密码去读取用户信息
					if(pd != null){
						pd.put("LAST_LOGIN",DateUtil.getTime().toString());
						userService.updateLastLogin(pd);
						User user = new User();
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						user.setNUMBER(pd.getString("NUMBER"));
						session.setAttribute(Const.SESSION_USER, user);			//把用户信息放session中
						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//清除登录验证码的session
						//shiro加入身份验证
						Subject subject = SecurityUtils.getSubject(); 
					    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD); 
					    try { 
					        subject.login(token); 
					    } catch (AuthenticationException e) { 
					    	errInfo = "身份验证失败！";
					    }
					}else{
						errInfo = "usererror"; 				//用户名或密码有误
						logBefore(logger, USERNAME+"登录系统密码或用户名错误");
					}
				}else{
					errInfo = "codeerror";				 	//验证码输入有误
				}
				if(Tools.isEmpty(errInfo)){
					errInfo = "success";					//验证成功
					logBefore(logger, USERNAME+"登录系统");
				}
			}
		}else{
			errInfo = "error";	//缺少参数
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**访问系统首页
	 * @param changeMenu：切换菜单参数
	 * @return
	 */
	@RequestMapping(value="/main/{changeMenu}")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user != null) {
				User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
				if(null == userr){
					user = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USERROL, user);						//存入session	
				}else{
					user = userr;
				}
				String USERNAME = user.getUSERNAME();
				Role role = user.getRole();													//获取用户角色
				String roleRights = role!=null ? role.getRIGHTS() : "";						//角色权限(菜单权限)
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); 	//将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);						//放入用户名到session
				this.setAttributeToAllDEPARTMENT_ID(session, USERNAME);						//把用户的组织机构权限放到session里面
				List<Menu> allmenuList = new ArrayList<Menu>();
				allmenuList = this.getAttributeMenu(session, USERNAME, roleRights);			//菜单缓存
				List<Menu> menuList = new ArrayList<Menu>();
				menuList = this.changeMenuF(allmenuList, session, USERNAME, changeMenu);	//切换菜单
				if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));//按钮权限放到session中
				}
				System.out.println("用户按钮权限："+Jurisdiction.getHC().toString());
				this.getRemortIP(USERNAME);	//更新登录IP
				mv.setViewName("system/index/main");
				mv.addObject("user", user);
				mv.addObject("menuList", menuList);
			}else {
				mv.setViewName("system/index/login");//session失效后跳转登录页面
			}
		} catch(Exception e){
			mv.setViewName("system/index/login");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**访问newApp系统首页
	 * @param changeMenu：切换菜单参数
	 * @return
	 */
//	@RequestMapping(value="/mainApp/{changeMenu}")
	public ModelAndView login_app_index(@PathVariable("changeMenu") String changeMenu){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user != null) {
				User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
				if(null == userr){
					user = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USERROL, user);						//存入session	
				}else{
					user = userr;
				}
				String USERNAME = user.getUSERNAME();
				Role role = user.getRole();													//获取用户角色
				String roleRights = role!=null ? role.getRIGHTS() : "";						//角色权限(菜单权限)
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); 	//将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);						//放入用户名到session
				this.setAttributeToAllDEPARTMENT_ID(session, USERNAME);						//把用户的组织机构权限放到session里面
				List<Menu> allmenuList = new ArrayList<Menu>();
				allmenuList = this.getAttributeMenu(session, USERNAME, roleRights);			//菜单缓存
				List<Menu> menuList = new ArrayList<Menu>();
				menuList = this.changeMenuF(allmenuList, session, USERNAME, changeMenu);	//切换菜单
				if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));//按钮权限放到session中
				}
				System.out.println("用户按钮权限："+Jurisdiction.getHC().toString());
				this.getRemortIP(USERNAME);	//更新登录IP
				mv.setViewName("html/html/menu/main_menu.html");
				mv.addObject("user", user);
				mv.addObject("menuList", menuList);
			}else {
				mv.setViewName("html/html/logins/login.html");//session失效后跳转登录页面
			}
		} catch(Exception e){
			mv.setViewName("html/html/logins/login.html");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.addObject("pd",pd);
		return mv;
	}
	
	
	/**菜单缓存
	 * @param session
	 * @param USERNAME
	 * @param roleRights
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getAttributeMenu(Session session, String USERNAME, String roleRights) throws Exception{
		List<Menu> allmenuList = new ArrayList<Menu>();
		if(null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)){	
			allmenuList = menuService.listAllMenuQx("0");							//获取所有菜单
			if(Tools.notEmpty(roleRights)){
				allmenuList = this.readMenu(allmenuList, roleRights);				//根据角色权限获取本权限的菜单列表
			}
			session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);//菜单权限放入session中
		}else{
			allmenuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList);
		}
		return allmenuList;
	}
	
	/**根据角色权限获取本权限的菜单列表(递归处理)
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList,String roleRights){
		for(int i=0;i<menuList.size();i++){
			menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
			if(menuList.get(i).isHasMenu()){		//判断是否有此菜单权限
				this.readMenu(menuList.get(i).getSubMenu(), roleRights);//是：继续排查其子菜单
			}
		}
		return menuList;
	}
	
	/**切换菜单处理
	 * @param allmenuList
	 * @param session
	 * @param USERNAME
	 * @param changeMenu
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> changeMenuF(List<Menu> allmenuList, Session session, String USERNAME, String changeMenu){
		List<Menu> menuList = new ArrayList<Menu>();
		//System.out.println("--------changeMenu---------"+session.getAttribute("changeMenu"));
		if(null == session.getAttribute(USERNAME + Const.SESSION_menuList) || ("yes".equals(changeMenu))){
			List<Menu> menuList2 = new ArrayList<Menu>();
			for(int i=0;i<allmenuList.size();i++){//拆分菜单
				Menu menu = allmenuList.get(i);
				menuList2.add(menu);
			}
			menuList = menuList2;
			session.setAttribute(USERNAME + Const.SESSION_menuList, menuList2);
			/*//注释添加上面菜单处理
			 * List<Menu> menuList1 = new ArrayList<Menu>();
			List<Menu> menuList2 = new ArrayList<Menu>();
			for(int i=0;i<allmenuList.size();i++){//拆分菜单
				Menu menu = allmenuList.get(i);
				if("1".equals(menu.getMENU_TYPE())){
					menuList1.add(menu);
				}else{
					menuList2.add(menu);
				}
			}
			session.removeAttribute(USERNAME + Const.SESSION_menuList);
			if("2".equals(session.getAttribute("changeMenu"))){
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList1);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "1");
				menuList = menuList1;
			}else{
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList2);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "2");
				menuList = menuList2;
			}*/
		}else{
			menuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_menuList);
		}
		return menuList;
	}
	
	/**把用户的组织机构权限放到session里面
	 * @param session
	 * @param USERNAME
	 * @return
	 * @throws Exception 
	 */
	public void setAttributeToAllDEPARTMENT_ID(Session session, String USERNAME) throws Exception{
		String DEPARTMENT_IDS = "0",DEPARTMENT_ID = "0";
		if(!"admin".equals(USERNAME)){
			PageData pd = datajurService.getDEPARTMENT_IDS(USERNAME);
			DEPARTMENT_IDS = null == pd?"无权":pd.getString("DEPARTMENT_IDS");
			DEPARTMENT_ID = null == pd?"无权":pd.getString("DEPARTMENT_ID");
		}
		session.setAttribute(Const.DEPARTMENT_IDS, DEPARTMENT_IDS);	//把用户的组织机构权限集合放到session里面
		session.setAttribute(Const.DEPARTMENT_ID, DEPARTMENT_ID);	//把用户的最高组织机构权限放到session里面
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
//	@RequestMapping(value="/tab")
	public String tab(){
		return "system/index/tab";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login_default")
	public ModelAndView defaultPage() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Session session = Jurisdiction.getSession();
		User user=(User)session.getAttribute(Const.SESSION_USER);
		pd.put("CREATOR", user.getUSERNAME());	//创建用户名
		pd.put("one",  Tools.date2Str(new Date(),"yyyy-01"));
		pd.put("two",  Tools.date2Str(new Date(),"yyyy-02"));
		pd.put("three",  Tools.date2Str(new Date(),"yyyy-03"));
		pd.put("four",  Tools.date2Str(new Date(),"yyyy-04"));
		pd.put("five",  Tools.date2Str(new Date(),"yyyy-05"));
		pd.put("six",  Tools.date2Str(new Date(),"yyyy-06"));
		pd.put("seven",  Tools.date2Str(new Date(),"yyyy-07"));
		pd.put("eight",  Tools.date2Str(new Date(),"yyyy-08"));
		pd.put("nine",  Tools.date2Str(new Date(),"yyyy-09"));
		pd.put("ten",  Tools.date2Str(new Date(),"yyyy-10"));
		pd.put("eleven",  Tools.date2Str(new Date(),"yyyy-11"));
		pd.put("twevle",  Tools.date2Str(new Date(),"yyyy-12"));
		//售卡
		//1月
		PageData card1 = cardstatisticsService.findByJanuary(pd);
		int one;
		if(card1 == null){
			one = 0;
		}else{
			double c1 = (double)card1.get("January");
			one = (new Double(Math.rint(c1))).intValue();
		}
		PageData card1_1 = cardstatisticsService.findByJanuary2(pd);
		int one_1;
		if(card1_1 == null){
			one_1 = 0;
		}else{
			double c1 = (double)card1_1.get("January2");
			one_1 = (new Double(Math.rint(c1))).intValue();
		}
		//2月
		PageData card2 = cardstatisticsService.findByFebruary(pd);
		int two;
		if(card2 == null){
			two = 0;
		}else{
			double c1 = (double)card2.get("February");
			two = (new Double(Math.rint(c1))).intValue();
		}
		PageData card2_1 = cardstatisticsService.findByFebruary2(pd);
		int two_1;
		if(card2_1 == null){
			two_1 = 0;
		}else{
			double c1 = (double)card2_1.get("February2");
			two_1 = (new Double(Math.rint(c1))).intValue();
		}
		//3月
		PageData card3 = cardstatisticsService.findByMarch(pd);
		int three;
		if(card3 == null){
			three = 0;
		}else{
			double c1 = (double)card3.get("March");
			three = (new Double(Math.rint(c1))).intValue();
		}
		PageData card3_1 = cardstatisticsService.findByMarch2(pd);
		int three_1;
		if(card3_1 == null){
			three_1 = 0;
		}else{
			double c1 = (double)card3_1.get("March2");
			three_1 = (new Double(Math.rint(c1))).intValue();
		}
		//4月
		PageData card4 = cardstatisticsService.findByApril(pd);
		int four;
		if(card4 == null){
			four = 0;
		}else{
			double c1 = (double)card4.get("April");
			four = (new Double(Math.rint(c1))).intValue();
		}
		PageData card4_1 = cardstatisticsService.findByApril2(pd);
		int four_1;
		if(card4_1 == null){
			four_1 = 0;
		}else{
			double c1 = (double)card4_1.get("April2");
			four_1 = (new Double(Math.rint(c1))).intValue();
		}
		//5月
		PageData card5 = cardstatisticsService.findByMay(pd);
		int five;
		if(card5 == null){
			five = 0;
		}else{
			double c1 = (double)card5.get("May");
			five = (new Double(Math.rint(c1))).intValue();
		}
		PageData card5_1 = cardstatisticsService.findByMay2(pd);
		int five_1;
		if(card5_1 == null){
			five_1 = 0;
		}else{
			double c1 = (double)card5_1.get("May2");
			five_1 = (new Double(Math.rint(c1))).intValue();
		}
		//6月
		PageData card6 = cardstatisticsService.findByJune(pd);
		int six;
		if(card6 == null){
			six = 0;
		}else{
			double c1 = (double)card6.get("June");
			six = (new Double(Math.rint(c1))).intValue();
		}
		PageData card6_1 = cardstatisticsService.findByJune2(pd);
		int six_1;
		if(card6_1 == null){
			six_1 = 0;
		}else{
			double c1 = (double)card6_1.get("June2");
			six_1 = (new Double(Math.rint(c1))).intValue();
		}
		//7月
		PageData card7 = cardstatisticsService.findByJuly(pd);
		int seven;
		if(card7 == null){
			seven = 0;
		}else{
			double c1 = (double)card7.get("July");
			seven = (new Double(Math.rint(c1))).intValue();
		}
		PageData card7_1 = cardstatisticsService.findByJuly2(pd);
		int seven_1;
		if(card7_1 == null){
			seven_1 = 0;
		}else{
			double c1 = (double)card7_1.get("July2");
			seven_1 = (new Double(Math.rint(c1))).intValue();
		}
		//8月
		PageData card8 = cardstatisticsService.findByAugust(pd);
		int eight;
		if(card8 == null){
			eight = 0;
		}else{
			double c1 = (double)card8.get("August");
			eight = (new Double(Math.rint(c1))).intValue();
		}
		PageData card8_1 = cardstatisticsService.findByAugust2(pd);
		int eight_1;
		if(card8_1 == null){
			eight_1 = 0;
		}else{
			double c1 = (double)card8_1.get("August2");
			eight_1 = (new Double(Math.rint(c1))).intValue();
		}
		//9月
		PageData card9 = cardstatisticsService.findBySeptember(pd);
		int nine;
		if(card9 == null){
			nine = 0;
		}else{
			double c1 = (double)card9.get("September");
			nine = (new Double(Math.rint(c1))).intValue();
		}
		PageData card9_1 = cardstatisticsService.findBySeptember2(pd);
		int nine_1;
		if(card9_1 == null){
			nine_1 = 0;
		}else{
			double c1 = (double)card9_1.get("September2");
			nine_1 = (new Double(Math.rint(c1))).intValue();
		}
		//10月
		PageData card10 = cardstatisticsService.findByOctober(pd);
		int ten;
		if(card10 == null){
			ten = 0;
		}else{
			double c1 = (double)card10.get("October");
			ten = (new Double(Math.rint(c1))).intValue();
		}
		PageData card10_1 = cardstatisticsService.findByOctober2(pd);
		int ten_1;
		if(card10_1 == null){
			ten_1 = 0;
		}else{
			double c1 = (double)card10_1.get("October2");
			ten_1 = (new Double(Math.rint(c1))).intValue();
		}
		//11月
		PageData card11 = cardstatisticsService.findByNovember(pd);
		int eleven;
		if(card11 == null){
			eleven = 0;
		}else{
			double c1 = (double)card11.get("November");
			eleven = (new Double(Math.rint(c1))).intValue();
		}
		PageData card11_1 = cardstatisticsService.findByNovember2(pd);
		int eleven_1;
		if(card11_1 == null){
			eleven_1 = 0;
		}else{
			double c1 = (double)card11_1.get("November2");
			eleven_1 = (new Double(Math.rint(c1))).intValue();
		}
		//12月
		PageData card12 = cardstatisticsService.findByDecember(pd);
		int twevle;
		if(card12 == null){
			twevle = 0;
		}else{
			double c1 = (double)card12.get("December");
			twevle = (new Double(Math.rint(c1))).intValue();
		}
		PageData card12_1 = cardstatisticsService.findByDecember2(pd);
		int twevle_1;
		if(card12_1 == null){
			twevle_1 = 0;
		}else{
			double c1 = (double)card12_1.get("December2");
			twevle_1 = (new Double(Math.rint(c1))).intValue();
		}
		
		String strXML = "";
		
		strXML += "<graph caption='对比表' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
		strXML += "<set name='1' value='"+(one+one_1)+"' color='AFD8F8'/>";
		strXML += "<set name='2' value='"+(two+two_1)+"' color='F6BD0F'/>";
		strXML += "<set name='3' value='"+(three+three_1)+"' color='8BBA00'/>";
		strXML += "<set name='4' value='"+(four+four_1)+"' color='FF8E46'/>";
		strXML += "<set name='5' value='"+(five+five_1)+"' color='008E8E'/>";
		strXML += "<set name='6' value='"+(six+six_1)+"' color='D64646'/>";
		strXML += "<set name='7' value='"+(seven+seven_1)+"' color='8E468E'/>";
		strXML += "<set name='8' value='"+(eight+eight_1)+"' color='588526'/>";
		strXML += "<set name='9' value='"+(nine+nine_1)+"' color='B3AA00'/>";
		strXML += "<set name='10' value='"+(ten+ten_1)+"' color='008ED6'/>";
		strXML += "<set name='11' value='"+(eleven+eleven_1)+"' color='9D080D'/>";
		strXML += "<set name='12' value='"+(twevle+twevle_1)+"' color='A186BE'/>";
		strXML += "</graph>";
		
		
		//金额
		//1月
		PageData money1 = moneystatisticsService.findByJanuary(pd);
		int one1;
		if(money1 == null){
			one1 = 0;
		}else{
			double c1 = (double)money1.get("January");
			one1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money1_1 = moneystatisticsService.findByJanuary2(pd);
		int one1_1;
		if(money1_1 == null){
			one1_1 = 0;
		}else{
			double c1 = (double)money1_1.get("January2");
			one1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//2月
		PageData money2 = moneystatisticsService.findByFebruary(pd);
		int two1;
		if(money2 == null){
			two1 = 0;
		}else{
			double c1 = (double)money2.get("February");
			two1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money2_1 = moneystatisticsService.findByFebruary2(pd);
		int two1_1;
		if(money2_1 == null){
			two1_1 = 0;
		}else{
			double c1 = (double)money2_1.get("February2");
			two1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//3月
		PageData money3 = moneystatisticsService.findByMarch(pd);
		int three1;
		if(money3 == null){
			three1 = 0;
		}else{
			double c1 = (double)money3.get("March");
			three1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money3_1 = moneystatisticsService.findByMarch2(pd);
		int three1_1;
		if(money3_1 == null){
			three1_1 = 0;
		}else{
			double c1 = (double)money3_1.get("March2");
			three1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//4月
		PageData money4 = moneystatisticsService.findByApril(pd);
		int four1;
		if(money4 == null){
			four1 = 0;
		}else{
			double c1 = (double)money4.get("April");
			four1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money4_1 = moneystatisticsService.findByApril2(pd);
		int four1_1;
		if(money4_1 == null){
			four1_1 = 0;
		}else{
			double c1 = (double)money4_1.get("April2");
			four1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//5月
		PageData money5 = moneystatisticsService.findByMay(pd);
		int five1;
		if(money5 == null){
			five1 = 0;
		}else{
			double c1 = (double)money5.get("May");
			five1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money5_1 = moneystatisticsService.findByMay2(pd);
		int five1_1;
		if(money5_1 == null){
			five1_1 = 0;
		}else{
			double c1 = (double)money5_1.get("May2");
			five1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//6月
		PageData money6 = moneystatisticsService.findByJune(pd);
		int six1;
		if(money6 == null){
			six1 = 0;
		}else{
			double c1 = (double)money6.get("June");
			six1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money6_1 = moneystatisticsService.findByJune2(pd);
		int six1_1;
		if(money6_1 == null){
			six1_1 = 0;
		}else{
			double c1 = (double)money6_1.get("June2");
			six1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//7月
		PageData money7 = moneystatisticsService.findByJuly(pd);
		int seven1;
		if(money7 == null){
			seven1 = 0;
		}else{
			double c1 = (double)money7.get("July");
			seven1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money7_1 = moneystatisticsService.findByJuly2(pd);
		int seven1_1;
		if(money7_1 == null){
			seven1_1 = 0;
		}else{
			double c1 = (double)money7_1.get("July2");
			seven1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//8月
		PageData money8 = moneystatisticsService.findByAugust(pd);
		int eight1;
		if(money8 == null){
			eight1 = 0;
		}else{
			double c1 = (double)money8.get("August");
			eight1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money8_1 = moneystatisticsService.findByAugust2(pd);
		int eight1_1;
		if(money8_1 == null){
			eight1_1 = 0;
		}else{
			double c1 = (double)money8_1.get("August2");
			eight1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//9月
		PageData money9 = moneystatisticsService.findBySeptember(pd);
		int nine1;
		if(money9 == null){
			nine1 = 0;
		}else{
			double c1 = (double)money9.get("September");
			nine1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money9_1 = moneystatisticsService.findBySeptember2(pd);
		int nine1_1;
		if(money9_1 == null){
			nine1_1 = 0;
		}else{
			double c1 = (double)money9_1.get("September2");
			nine1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//10月
		PageData money10 = moneystatisticsService.findByOctober(pd);
		int ten1;
		if(money10 == null){
			ten1 = 0;
		}else{
			double c1 = (double)money10.get("October");
			ten1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money10_1 = moneystatisticsService.findByOctober2(pd);
		int ten1_1;
		if(money10_1 == null){
			ten1_1 = 0;
		}else{
			double c1 = (double)money10_1.get("October2");
			ten1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//11月
		PageData money11 = moneystatisticsService.findByNovember(pd);
		int eleven1;
		if(money11 == null){
			eleven1 = 0;
		}else{
			double c1 = (double)money11.get("November");
			eleven1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money11_1 = moneystatisticsService.findByNovember2(pd);
		int eleven1_1;
		if(money11_1 == null){
			eleven1_1 = 0;
		}else{
			double c1 = (double)money11_1.get("November2");
			eleven1_1 = (new Double(Math.rint(c1))).intValue();
		}
		//12月
		PageData money12 = moneystatisticsService.findByDecember(pd);
		int twevle1;
		if(money12 == null){
			twevle1 = 0;
		}else{
			double c1 = (double)money12.get("December");
			twevle1 = (new Double(Math.rint(c1))).intValue();
		}
		PageData money12_1 = moneystatisticsService.findByDecember2(pd);
		int twevle1_1;
		if(money12_1 == null){
			twevle1_1 = 0;
		}else{
			double c1 = (double)money12_1.get("December2");
			twevle1_1 = (new Double(Math.rint(c1))).intValue();
		}
		
		String strXMLS = "";
		
		strXMLS += "<graph caption='对比表' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>";
		strXMLS += "<set name='1' value='"+(one1+one1_1)+"' color='AFD8F8'/>";
		strXMLS += "<set name='2' value='"+(two1+two1_1)+"' color='F6BD0F'/>";
		strXMLS += "<set name='3' value='"+(three1+three1_1)+"' color='8BBA00'/>";
		strXMLS += "<set name='4' value='"+(four1+four1_1)+"' color='FF8E46'/>";
		strXMLS += "<set name='5' value='"+(five1+five1_1)+"' color='008E8E'/>";
		strXMLS += "<set name='6' value='"+(six1+six1_1)+"' color='D64646'/>";
		strXMLS += "<set name='7' value='"+(seven1+seven1_1)+"' color='8E468E'/>";
		strXMLS += "<set name='8' value='"+(eight1+eight1_1)+"' color='588526'/>";
		strXMLS += "<set name='9' value='"+(nine1+nine1_1)+"' color='B3AA00'/>";
		strXMLS += "<set name='10' value='"+(ten1+ten1_1)+"' color='008ED6'/>";
		strXMLS += "<set name='11' value='"+(eleven1+eleven1_1)+"' color='9D080D'/>";
		strXMLS += "<set name='12' value='"+(twevle1+twevle1_1)+"' color='A186BE'/>";
		strXMLS += "</graph>";
		
		
		String time = Tools.date2Str(new Date(),"yyyy-MM-dd");
		PageData agent =new PageData();
		agent.put("USER_ID", user.getNUMBER());	//修改人
		agent = agentService.findByEdit(agent);
		agent.put("TIME", time);
		PageData num = agentService.getagentinfo(agent);
		agent.put("Belongagent",num.get("agent") );
		agent.put("Belongplayer",num.get("player") );
		agent.put("logname", user.getUSERNAME());
		Date now = new Date();//sdf.parse(today);
		Date befor = new Date(now.getTime() - 60 * 60 * 1000);
		agent.put("now", now);
		agent.put("befor", befor);
		agent.put("online",1 );
		PageData online=agentService.getonlineuser(agent);
		agent.put("waituser",online.get("num") );
		agent.put("online",2 );
		online=agentService.getonlineuser(agent);
		agent.put("onlineuser",online.get("num") );
		mv.addObject("agent",agent);
		mv.addObject("pd",pd);
		mv.addObject("strXML",strXML);
		mv.addObject("strXMLS",strXMLS);
		mv.setViewName("system/index/default");
		return mv;
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout(){
		String USERNAME = Jurisdiction.getUsername();	//当前登录的用户名
		logBefore(logger, USERNAME+"退出系统");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Session session = Jurisdiction.getSession();	//以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		session.removeAttribute("DEPARTMENT_IDS");
		session.removeAttribute("DEPARTMENT_ID");
		//shiro销毁登录
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		pd = this.getPageData();
		pd.put("msg", pd.getString("msg"));
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.setViewName("system/index/login");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**获取用户权限
	 * @param session
	 * @return
	 */
	public Map<String, String> getUQX(String USERNAME){
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			pd.put("ROLE_ID", userService.findByUsername(pd).get("ROLE_ID").toString());//获取角色ID
			pd = roleService.findObjectById(pd);										//获取角色信息														
			map.put("adds", pd.getString("ADD_QX"));	//增
			map.put("dels", pd.getString("DEL_QX"));	//删
			map.put("edits", pd.getString("EDIT_QX"));	//改
			map.put("chas", pd.getString("CHA_QX"));	//查
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if("admin".equals(USERNAME)){
				buttonQXnamelist = fhbuttonService.listAll(pd);					//admin用户拥有所有按钮权限
			}else{
				buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd);	//此角色拥有的按钮权限标识列表
			}
			for(int i=0;i<buttonQXnamelist.size();i++){
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"),"1");		//按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		return map;
	}
	
	/** 更新登录用户的IP
	 * @param USERNAME
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {  
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		if (ip.split(",").length > 1) {
			ip = ip.split(",")[0];
		}
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}  
	
}
