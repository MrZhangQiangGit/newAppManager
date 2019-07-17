<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html class="no-js fixed-layout">
<base href="<%=basePath%>">
<head>
	<!-- jsp文件头和头部 -->
	<%@ include file="top.jsp"%>
	<style type="text/css">
	</style>
</head>
<body class="">
<!-- header start 头部开始 -->
 <%@ include file="head.jsp"%> 

<!-- header end 头部结束 -->

<div class="admin-content am-ag">
    <div class="admin-content-body am-u-sm-centered am-u-md-6 am-u-lg-5 am-padding-0">
		<!-- hbody 身体 -->
		<div class="menu am-text-xs ">
			<div class="am-panel am-panel-warning am-margin-xs">
				<div class="am-panel-hd">公告</div>
				<div class="am-panel-bd">
					<p class="am-margin-bottom-0">
						${pd.notice }
					</p>
				</div>
			</div>
			<div class="am-center am-cf am-padding-sm">
				<ul class="am-avg-sm-4 am-text-center">
					<li class="am-padding-sm">
						<div class="menubtn" onclick="menuToList('agentApp/agentList.do');">
							<span class="am-icon-btn am-icon-graduation-cap am-danger am-icon-sm "></span><br>
							<span>我的代理</span>
						</div>
					</li>
					<li class="am-padding-sm">
						<div class="menubtn" onclick="menuToList('userApp/lowerUserList.do');">
							<span class="am-icon-btn am-icon-user am-secondary am-icon-sm "></span><br>
							我的玩家
						</div>
					</li>
					<li class="am-padding-sm">
						<div class="menubtn" onclick="menuToList('agentRechargeApp/goAgentRecharge.do');">
							<span class="am-icon-btn am-icon-diamond am-success am-icon-sm "></span><br>
							代理充值
						</div>
					</li>
					<li class="am-padding-sm">
						<div class="menubtn"  onclick="menuToList('userRechargeApp/goUserRecharge.do');">
							<span class="am-icon-btn am-icon-fax am-warning am-icon-sm "></span><br>
							玩家充值
						</div>
					</li>
					<li class="am-padding-sm">
						<div class="menubtn" onclick="menuToList('agentRechargeApp/agentRechargeList.do');">
							<span class="am-icon-btn am-icon-envira am-danger am-icon-sm "></span><br>
							<span>代理充值记录</span>
						</div>
					</li>
					<li class="am-padding-sm">
						<div class="menubtn" onclick="menuToList('userRechargeApp/userRechargeList.do');">
							<span class="am-icon-btn am-icon-viadeo am-secondary am-icon-sm "></span><br>
							玩家充值记录
						</div>
					</li>
					<li class="am-padding-sm">
						<div class="menubtn" onclick="menuToList('roomApp/userConsumeList.do');">
							<span class="am-icon-btn am-icon-codiepie am-success am-icon-sm "></span><br>
							玩家消费记录
						</div>
					</li>
					<li class="am-padding-sm" onclick="menuToList('userFight/userFightList.do');">
						<div class="menubtn">
							<span class="am-icon-btn am-icon-paper-plane am-warning am-icon-sm "></span><br>
							玩家战绩
						</div>
					</li>
				</ul>
				
			</div>
			<!-- <hr data-am-widget="divider" style="" class="am-divider am-divider-dashed"> -->
			<!-- <div class="am-ag am-center am-padding-sm am-cf am-text-center">
				<div class="am-u-sm-3 menubtn">
					<span class="am-icon-btn am-icon-graduation-cap am-danger am-icon-sm "></span><br>
					<span>我的代理</span>
				</div>
				<div class="am-u-sm-3 menubtn">
					<span class="am-icon-btn am-icon-user am-secondary am-icon-sm "></span><br>
					我的玩家
				</div>
				<div class="am-u-sm-3 menubtn">
					<span class="am-icon-btn am-icon-graduation-cap am-success am-icon-sm "></span><br>
					代理充值
				</div>
				<div class="am-u-sm-3 menubtn">
					<span class="am-icon-btn am-icon-graduation-cap am-warning am-icon-sm "></span><br>
					玩家充值
				</div>
			</div> -->
		</div>
		<!-- 身体结束 -->
		<!-- <div class="" style="height: 100%;"> -->
			<!-- <iframe name="mainFrame" id="mainFrame" frameborder="0" src="tab.do" style="margin:0 auto;width:100%;height:100%;"></iframe> -->
		<!-- </div> -->
    </div>
	
    <footer class="admin-content-footer am-u-sm-centered am-u-md-7 am-u-lg-5 am-padding-0">
      <hr>
      <p class="am-padding-left">Copyright ©  2018</p>
      <div data-am-widget="gotop" class="am-gotop am-gotop-fixed" >
	    <a href="#top" title="回到顶部">
	        <span class="am-gotop-title">回到顶部</span>
	          <i class="am-gotop-icon am-icon-chevron-up"></i>
	    </a>
	  </div>
    </footer>
  </div>

<!-- 底部 Navbar -->
<%@ include file="foot.jsp"%> 
  

</body>
<script src="amazeUI/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="amazeUI/js/amazeui.min.js"></script>
<script src="amazeUI/js/app.js"></script>
<script type="text/javascript">
	//跳转菜单
	function menuToList(menuUrl){
		window.location.href=menuUrl;
	}
	
	
</script>
</html>
