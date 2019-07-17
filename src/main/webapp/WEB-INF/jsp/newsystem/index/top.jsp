<%@ page pageEncoding="UTF-8" %>
<meta charset="utf-8">
  <title>
	<c:if test="${pd.stas==1 }">${pd.SYSNAME }</c:if>
    <c:if test="${pd.stas==2 }">${pd.menuName }</c:if>
  </title>
  <!-- 把 content 属性关联到 HTTP 头部。 -->
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <!-- 描述 -->
  <meta name="description" content="这是一个 index 页面">
  <!-- 定义关键字 -->
  <meta name="keywords" content="index">
  
  <!-- <meta http-equiv="Content-Type" content="text/html; charset=gb2312"> -->
  <!-- 响应式页面 -->
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- 默认webkit内核渲染 -->
  <meta name="renderer" content="webkit">
  <!-- 禁止百度自动转码 -->
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <!-- 浏览器title显示的图标 -->
  <meta name="apple-mobile-web-app-title" content="Amaze UI" />
  <link rel="icon" type="image/png" href="amazeUI/i/examples/bts.png">
  
  <link rel="apple-touch-icon-precomposed" href="amazeUI/i/app-icon72x72@2x.png">
  <link rel="stylesheet" href="amazeUI/css/amazeui.min.css"/>
  <link rel="stylesheet" href="amazeUI/css/admin.css">
  <link rel="stylesheet" href="amazeUI/css/top.css">
  <link rel="stylesheet" href="amazeUI/css/app.css">