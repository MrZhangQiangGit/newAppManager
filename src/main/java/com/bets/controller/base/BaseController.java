package com.bets.controller.base;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.bets.entity.Page;
import com.bets.util.Logger;
import com.bets.util.PageData;
import com.bets.util.UuidUtil;

/**
 * 修改时间：2015、12、11
 */
public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = 6357869213649815390L;
	
	/** new PageData对象
	 * @return
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	
	/**得到ModelAndView
	 * @return
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**得到request对象
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	/**得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}
	
	/**得到分页列表的信息
	 * @return
	 */
	public Page getPage(){
		return new Page();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.info("--------------start---------------");
		logger.info(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.info("------------end-------------------");
	}
	
	/**
	 * 分页工具(根据总记录数,获取分页参数)
	 * @param pd 分页相关数据(包含,页面传过来的值,查询总数)
	 * @return
	 * @throws Exception
	 */
	public PageData paging(PageData pd)throws Exception{
		
		int pageNumber = 8;//每页记录数
		int page;//页数
		int total;//获取总记录数
		int totalPage;//总页数
		int start;//分页起始值
		pd.put("sum", pageNumber);//分页每页记录数
		
		total = (int)pd.get("total");//查询总记录数
		if(total%pageNumber==0){
			totalPage = total/pageNumber; 
		}else {
			totalPage = total/pageNumber+1;
		}
		
		String p = pd.getString("page");//获取页面传来的页数
		if(p==null || "".equals(p)) {
			page = 1;
		} else {
			page = Integer.parseInt(p);
			if(page<=0){//当页数为负数时跳转最大页
				page = totalPage;
			}
		}
		//分别判断页数是否大于最大页,最大页则跳转到1页,判断页数是否小于0.小于则跳转到最大页
		if(page>totalPage){start = 0;page = 1; }else start = (page-1)*pageNumber;
		pd.put("start", start);
		
		pd.put("page", page);//页数
		pd.put("total", total);//总记录数
		pd.put("totalPage", totalPage);//总页数
		
		return pd;
	}
	
}
