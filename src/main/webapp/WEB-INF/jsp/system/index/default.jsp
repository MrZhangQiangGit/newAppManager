<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="../index/top.jsp"%>
<!-- 百度echarts -->
<script src="plugins/echarts/echarts.min.js"></script>

</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="hr hr-18 dotted hr-double"></div>
					<div class="row">
						<div class="col-xs-12">

							<!-- <div class="alert alert-block alert-success">
								<button type="button" class="close" data-dismiss="alert">
									<i class="ace-icon fa fa-times"></i>
								</button>
								<i class="ace-icon fa fa-check green"></i>
								欢迎使用 FH Admin 系统&nbsp;&nbsp;
								<strong class="green">
									&nbsp;QQ:313596790
									<a href="http://www.fhadmin.org" target="_blank"><small>(&nbsp;www.fhadmin.org&nbsp;)</small></a>
								</strong>
							</div> -->
						<c:if test="${agent.AGENT_CODE == 888888}">
						<div style="width:90%;height:50px;margin: 10px auto;background-color: gold;line-height: 50px">
							<div style="font-size: 20px;padding-left: 10px;color:#fff"><b>等待人数:</b><a style="color:red">${agent.waituser }</a>&nbsp;&nbsp;&nbsp;<b>游戏中人数:</b><a style="color:red">${agent.onlineuser }</a></div>
						</div>
						</c:if>
						<div style="width:90%;height:200px;margin: 0 auto;background-color: #fff">
							<div style="width:49%;height:200px;background-color: #f1f1f1;float:left">
								<dl >
									<dt style="background-color: #c3f2ca;height:30px;font-size: 18px;color:#fff;line-height: 30px;padding-left:10px">个人信息</dt>
									<dd style="padding-left:10px">登录名:${agent.logname }</dd>
									<dd style="padding-left:10px">昵称:${agent.AGENT_NAME }</dd>
									<dd style="padding-left:10px">公会号:${agent.YQ_CODE }</dd>
									<c:if test="${agent.LEVEL==0 }"><dd style="padding-left:10px">类型:总代理</dd></c:if>
									<c:if test="${agent.LEVEL==1 }"><dd style="padding-left:10px">类型:一级代理</dd></c:if>
									<c:if test="${agent.LEVEL==2 }"><dd style="padding-left:10px">类型:二级代理</dd></c:if>
									<c:if test="${agent.LEVEL==3 }"><dd style="padding-left:10px">类型:三级代理</dd></c:if>
									<dd style="padding-left:10px">已提现金额:${agent.CASH_MONEY }</dd>
									<dd style="padding-left:10px">余额:${agent.SURPLUS_MONEY }</dd>
									<dd style="padding-left:10px">总金额:${agent.TOTAL_MONEY }</dd>
									<dd style="padding-left:10px">现有房卡:${agent.CARD_NUM }</dd>
									
								</dl>
							</div>
							<div style="width:49%;height:200px;background-color: #f1f1f1;float:left;margin-left:2%">
								<dl >
									<dt  style="background-color: #c3b0f2;height:30px;font-size: 18px;color:#fff;line-height: 30px;padding-left:10px">人员信息</dt>
									<dd style="padding-left:10px">下属代理:${agent.Belongagent }</dd>
									<dd style="padding-left:10px">直属玩家:${agent.Belongplayer }</dd>
								</dl>
							</div>
						</div>
						<!-- 柱状图 -->
						<c:if test="${agent.AGENT_CODE == 888888}">
						<div class="center">
							<div  style="float:left;margin-top:60px;">
									<table border="0" width="50%">
										<tr><th><span style="font-size:28px;line-height:50px;margin-left:220px;">售卡统计</span></th></tr>
										<tr>
											<td><jsp:include
													page="../../FusionChartsHTMLRenderer.jsp" flush="true">
													<jsp:param name="chartSWF" value="static/FusionCharts/Column2D.swf" />
													<jsp:param name="strURL" value="" />
													<jsp:param name="strXML" value="${strXML}" />
													<jsp:param name="chartId" value="myNext" />
													<jsp:param name="chartWidth" value="550" />
													<jsp:param name="chartHeight" value="400" />
													<jsp:param name="debugMode" value="false" />
												</jsp:include></td>
										</tr>
									</table>
								</div>
								<div style="float:right;margin-top:60px;">
									<table border="0" width="50%">
										<tr><th><span style="font-size:28px;line-height:50px;margin-left:220px;">金额统计</span></th></tr>
										<tr>
											<td><jsp:include
													page="../../FusionChartsHTMLRenderer.jsp" flush="true">
													<jsp:param name="chartSWF" value="static/FusionCharts/Column2D.swf" />
													<jsp:param name="strURL" value="" />
													<jsp:param name="strXML" value="${strXMLS}" />
													<jsp:param name="chartId" value="myNext" />
													<jsp:param name="chartWidth" value="550" />
													<jsp:param name="chartHeight" value="400" />
													<jsp:param name="debugMode" value="false" />
												</jsp:include></td>
										</tr>
									</table>
								</div>
							
						</div>
						</c:if>
						
							
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->


		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<script type="text/javascript">
		$(top.hangge());
	</script>
<script type="text/javascript" src="static/ace/js/jquery.js"></script>
</body>
</html>