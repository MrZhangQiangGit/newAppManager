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
    <div class="admin-content-body am-padding-xs am-u-sm-12 am-u-md-6 am-u-lg-5 am-u-md-centered">
		<!-- hbody start -->
		<form action="" method="post" id="Form" class="am-form am-form-horizontal">
			<!-- 搜索框 -->
			<div class="am-margin-xs am-ag am-cf" data-am-sticky id="sticky">
				<div class="am-fl am-u-sm-8" style="padding: 0px;">
					<input id="keywords" name="keywords" type="text" value="${pd.keywords }" placeholder="输入关键字搜索" class="am-radius" />
				</div>
				<button type="button" onclick="flip(0);" style="margin: 0px;" class="am-btn am-btn-success am-u-sm-4 am-fr am-radius">
					<i class="am-icon-search am-icon-secondary"></i><span>搜索</span>
				</button>
			</div>
			<!-- 面板 -->
			<c:choose>
				<c:when test="${not empty pd.list }">
					<c:forEach items="${pd.list }" var="var" varStatus="vs">
						<div class="am-panel am-panel-secondary am-text-sm am-margin-bottom-sm">
						  <!-- 面板头部 -->
						  <div class="am-panel-hd am-cf">
						  	<span class="am-fl"><strong>${var.AGENT_NAME }</strong></span>
					    	<ul class="am-fr am-margin-0" >
						          <li class="am-dropdown" data-am-dropdown>
							        <span href="javascript:;" class="am-dropdown-toggle" data-am-dropdown-toggle>
						              <span class="am-header-nav-title ">更多</span>
						              <i class="am-header-icon am-icon-bars"></i>
							        </span>
							        <ul class="am-dropdown-content am-margin-0 am-padding-0 am-avg-sm-1">
							          <li onclick="goEidtData('${var.AGENT_CODE}');" class="am-btn am-margin-0 am-padding-0 am-radius">
							          	<a href="javascript:;">
							          	<span class="am-icon-pencil-square-o am-text-success"></span>
							          	<span>资料修改</span>
							          	</a>
							          </li>
							          <li class="am-divider"></li>
							          <li onclick="goLowerUser('${var.AGENT_CODE}');" class="am-btn  am-margin-0 am-padding-0 am-radius">
							          	<a href="javascript:;">
							          		<span class="am-icon-users am-text-secondary"></span>
							          		<span>查看玩家</span>
							          	</a>
							          </li>
							          <li class="am-divider"></li>
							          <li onclick="goRecharge('${var.AGENT_CODE}');" class="am-btn  am-margin-0 am-padding-0 am-radius">
							          	<a href="javascript:;">
							          		<span class="am-icon-paypal am-text-warning"></span>
							          		<span>充值</span>
							          	</a>
							          </li>
							        </ul>
						      	</li>
					      	</ul>
						  	<span class="am-fr">${var.AGENT_CODE }</span>
						  </div>
						  <!-- 面板内容 -->
						  <div class="am-panel-bd am-text-xs">
						  	<ul class="am-avg-sm-3">
						  		<li>
							    	<div class="">
							    		<c:if test="${var.LEVEL=='0' }"><i class="am-badge am-badge-danger am-round">总代理</i></c:if>
							    		<c:if test="${var.LEVEL=='1' }"><i class="am-badge am-badge-primary am-round">一级代理</i></c:if>
							    		<c:if test="${var.LEVEL=='2' }"><i class="am-badge am-badge-secondary am-round">二级代理</i></c:if>
							    		<c:if test="${var.LEVEL=='3' }"><i class="am-badge am-badge-success am-round">三级代理</i></c:if>
							    		<c:if test="${var.LEVEL=='vip' }"><i class="am-badge am-badge-warning am-round">vip</i></c:if>
							    	</div>
						  		</li>
						  		<li>
							    	<div class="">
							    		<span>剩余房卡:</span>
							    		<span>${var.CARD_NUM }</span>
							    	</div>
						  		</li>
						  		<li>
							    	<div class="">
							    		<span>销售房卡:</span>
							    		<span>${var.SALE_TOTAL }</span>
							    	</div>
						  		</li>
						  	</ul>
						  </div>
						</div>		
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="am-panel am-panel-warning">
					    <div class="am-panel-bd">暂无相关信息</div>
					</div>
				</c:otherwise>
			</c:choose>
			<!-- 分页 -->
			<div class="am-text-center">
				<ul class="am-avg-sm-4 am-margin-xs">
					<li>
						<input type="button" class="am-btn am-btn-success am-radius" onclick="flip(-1);" value="上一页"/>
					</li>
					<li class="am-ag">
						<input id="page" name="page" type="number" class="am-u-10 am-text-center" height="37px;" value="${pd.page }" />
					</li>
					<li>
						<input type="button" class="am-btn am-btn-success am-radius" onclick="flip(1);" value="下一页"/>
					</li>
					<li>
						<input type="button" class="am-btn am-btn-success am-radius" onclick="flip(0);" value="跳转"/>
					</li>
				</ul>
				<span class="am-text-xs am-text-danger">共<mark>${pd.totalPage}</mark>页,<mark>${pd.total }</mark>条数据</span>
			</div>
			
		</form>
    </div>
	
    <footer class="admin-content-footer">
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
<%-- <%@ include file="foot.jsp"%>  --%>
  

</body>
<script src="amazeUI/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="amazeUI/js/amazeui.min.js"></script>
<script src="amazeUI/js/app.js"></script>
<script type="text/javascript">
	$(function(){
		$("#sticky").sticky({
			top:49
		});
	});
	//翻页-1上一页,1下一页,0跳转
	function flip(status){
		var keywords = $("#keywords").val();
		var page = parseInt($("#page").val());
		if(status==-1){
			page = page-1;
		}else if(status==1){
			page = page+1;
		}
		console.log("关键词:"+keywords+"  页数:"+page);
		//$("#Form").submit();
		var url = "/agentApp/agentList.do?keywords="+keywords+"&page="+page;
		console.log(url);
		window.location.href="agentApp/agentList.do?keywords="+keywords+"&page="+page;
	}
	//去修改代理资料
	function goEidtData(id){
		window.location.href = "agentApp/goEditAgentData.do?AGENT_CODE="+id+"&type=1";
	}
	//查看代理下属玩家
	function goLowerUser(id){
		window.location.href = "userApp/lowerUserList.do?cid="+id;
	}
	//代理充值
	function goRecharge(id){
		window.location.href = "agentRechargeApp/goAgentRecharge.do?AGENT_CODE="+id;
	}
</script>
</html>
