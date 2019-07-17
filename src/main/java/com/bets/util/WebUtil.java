package com.bets.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author jack
 *
 */
public class WebUtil {
	/**
	 * 将信息发送到移动客户端
	 * @param response
	 * @param msg
	 * @throws Exception
	 */
	public static void sendResponseUTF8(HttpServletResponse response, String msg)
	  {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			writer.write(msg);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(writer!=null){
				writer.close();
			}
		}
	}
}
