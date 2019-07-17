package com.bets.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
/**
 * 
* 类名称：MyExceptionResolver.java
* 类描述： 
* @author bets
* 作者单位： 
* @version 1.0
 */
public class MyExceptionResolver implements HandlerExceptionResolver{
	private static Logger  log = LoggerFactory.getLogger(MyExceptionResolver.class);
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		System.out.println("==============异常开始=============");
		log.error("====异常:"+ex+"====");
		log.error("具体异常::"+ex.getMessage(),ex);
		ex.printStackTrace();//打印异常
		System.out.println("==============异常结束=============");
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("exception", ex.toString().replaceAll("\n", "<br/>"));
		return mv;
	}

}
