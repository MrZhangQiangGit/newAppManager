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
	<%@ include file="../../newsystem/index/top.jsp"%>
</head>
<body>
<!-- header start 头部开始 -->
 <%@ include file="../../newsystem/index/head.jsp"%> 
<!-- header end 头部结束 -->

<div class="admin-content am-ag">
    <div class="admin-content-body am-u-sm-centered am-u-md-6 am-u-lg-5 am-padding-0">
		<!-- hbody start  -->
		<!-- 折叠面板组 -->
		<div class="am-panel-group am-kai am-margin-xs " id="userInfo">
			<!-- 用户基本信息 -->
			<hr>
			<div class="am-panel am-panel-secondary " >
				<div id="udata" class="am-panel-hd am-cf" data-am-collapse="{parent:'#userInfo',target:'#userData'}">
					<i class="am-icon-user-secret am-fl am-text-primary"></i>
					<h3 class="am-panel-title am-fl" >我的信息</h3>
					<i id="uicon" class="am-icon-angle-down am-primary am-fr am-text-secondary"></i>
				</div>
				<div class="am-panel-bd am-collaple am-in am-margin-xs" id="userData">
					<ul class="am-list am-list-border am-list-striped am-list-static">
						<li class="am-cf">
							<span class="am-fl">昵称:</span>
							<span class="am-fr">${pd.AGENT_NAME }</span>
						</li>
						<li class="am-cf">
							<span class="am-fl">ID:</span>
							<span class="am-fr">${pd.AGENT_CODE }</span>
						</li>
						<li class="am-cf">
							<span class="am-fl">销售金额:</span>
							<span class="am-fr">${pd.CARD_NUM }</span>
						</li>
						<li class="am-cf">
							<span class="am-fl">库存:</span>
							<span class="am-fr">${pd.SALE_TOTAL }</span>
						</li>
					</ul>
					<footer class="am-panel-footer am-primary am-cf" onclick="goEidtData('${pd.AGENT_CODE}');">
						<i class="am-icon-angle-right am-text-danger am-fr"></i>
						<span class="am-fr">修改资料</span>
					</footer>
				</div>
			</div>
			<!-- 修改密码 -->
			<div class="am-panel am-panel-default" >
				<div class="am-panel-hd am-cf" onclick="goEditPwd();"> <!-- data-am-collapse="{parent:'#userInfo',target:'#pwd'}" -->
					<i class="am-icon-expeditedssl am-fl am-text-primary"></i>
					<h3 class="am-panel-title am-fl" >修改密码</h3>
					<i class="am-icon-angle-right am-primary am-fr am-text-primary"></i>
				</div>
				<!-- <div class="am-panel-bd am-collapse" id="pwd">
					<ul>
						<li>昵称:</li>
						<li>ID:</li>
						<li>销售金额:</li>
						<li>库存:</li>
					</ul>
				</div> -->
			</div>
		</div>
		<!-- hbody end  -->
    </div>
	
    <footer class="admin-content-footer">
      <hr>
      <p class="am-padding-left">Copyright ©  2018</p>
    </footer>
  </div>

<!-- 底部 Navbar -->
<%@ include file="../../newsystem/index/foot.jsp"%> 
  

</body>
<script src="amazeUI/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="amazeUI/js/amazeui.min.js"></script>
<script src="amazeUI/js/app.js"></script>
<script type="text/javascript">
/*打开和关闭折叠菜单,切换右边箭头方向*/
$(function(){
	$('#userData').on('open.collapse.amui', function() {
		  //console.log('折叠菜单打开了！');
		  $("#uicon").addClass("am-icon-angle-down");
		  $("#uicon").removeClass("am-icon-angle-right");
		}).on('close.collapse.amui', function() {
		  //console.log('折叠菜单关闭鸟！');
		  $("#uicon").addClass("am-icon-angle-right");
		  $("#uicon").removeClass("am-icon-angle-down");
		});
});
function goEditPwd(){
	window.location.href="agentApp/goEditPwd.do";
}
//去修改代理资料
function goEidtData(id){
	window.location.href = "agentApp/goEditAgentData.do?AGENT_CODE="+id+"&type=2";
}
</script>
</html>
