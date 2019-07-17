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
	<%@ include file="../newsystem/index/top.jsp"%>
</head>
<body>
<!-- header start 头部开始 -->
<%@ include file="../newsystem/index/head.jsp"%>
<!-- header end 头部结束 -->

<div class="am-cf admin-main">
  <!-- sidebar start 侧边栏开始 -->
	 <%@ include file="../newsystem/index/left.jsp" %> 
	<%-- <jsp:include page="../newsystem/index/left.jsp"></jsp:include> --%>
  <!-- sidebar end 侧边栏结束 -->

  <!-- content start 主体内容开始 -->
	<jsp:include page="../newsystem/index/default2.jsp"></jsp:include>
  <!-- <div class="admin-content">
    <div class="admin-content-body">

    </div>

    <footer class="admin-content-footer">
      <hr>
      <p class="am-padding-left">Copyright ©  2018</p>
    </footer>
  </div> -->
  <!-- content end 主体内容结束 -->

</div>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>

<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="amazeUI/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="amazeUI/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="amazeUI/js/amazeui.min.js"></script>
<script src="amazeUI/js/app.js"></script>
</body>
</html>
