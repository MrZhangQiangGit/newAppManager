<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!doctype html>
<html class="no-js fixed-layout">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>代理管理</title>
  <meta name="description" content="这是一个 agent 页面">
  <meta name="keywords" content="index">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="icon" type="image/png" href="../amazeUI/i/examples/landing.png">
  <link rel="apple-touch-icon-precomposed" href="../amazeUI/i/app-icon72x72@2x.png">
  <meta name="apple-mobile-web-app-title" content="Amaze UI" />
  <link rel="stylesheet" href="../amazeUI/css/amazeui.min.css"/>
  <link rel="stylesheet" href="../amazeUI/css/admin.css">
  <link rel="stylesheet" href="../amazeUI/css/top.css">
</head>
<body ag-app="mainApp" ag-controller="mainController">
<!--[if lte IE 9]>
<p class="browsehappy">你正在使用<strong>过时</strong>的浏览器，Amaze UI 暂不支持。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>
  以获得更好的体验！</p>
<![endif]-->
<!-- header start 头部开始 -->
 <header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand">
    <strong><big>management</big></strong> <small>游戏后台管理</small>
  </div>
  <button class="am-topbar-btn am-topbar-toggle am-btn am-btn-sm am-btn-success am-show-sm-only" data-am-collapse="{target: '#topbar-collapse'}"><span class="am-sr-only">导航切换</span> <span class="am-icon-bars"></span></button>
  <div class="am-collapse am-topbar-collapse" id="topbar-collapse">
    <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
      <!-- <li><a href="javascript:;"><span class="am-icon-envelope-o"></span> 收件箱 <span class="am-badge am-badge-warning">5</span></a></li> -->
      <li class="am-dropdown" data-am-dropdown>
        <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
          <span class="am-icon-users"></span> 管理员 <span class="am-icon-caret-down"></span>
        </a>
        <ul class="am-dropdown-content">
          <li><a href="#"><span class="am-icon-user"></span> 资料</a></li>
          <li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
          <li><a href="../logoutApp"><span class="am-icon-power-off"></span> 退出</a></li>
        </ul>
      </li>
      <li class="am-hide-sm-only"><a href="javascript:;" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span> <span class="admin-fullText">开启全屏</span></a></li>
    </ul>
  </div>
</header> 
<!-- header end 头部结束 -->

<!-- <embed width="100%" class="am-topbar am-topbar-inverse admin-header " src="../public/top.html"> -->
<!-- <iframe id="top" width="100%"  class="am-topbar am-topbar-inverse admin-header " src="../public/top.html"></iframe> -->

<div class="am-cf admin-main">
  <!-- sidebar start 侧边栏开始 -->
  <div class="admin-sidebar am-offcanvas" id="admin-offcanvas">
    <div class="am-offcanvas-bar admin-offcanvas-bar">
      <ul class="am-list admin-sidebar-list" id="coll">
        <li><a href="../app/new_test_file.html" data-am-collapse="{parent:'#coll'}"><span class="am-icon-home"></span> 首页</a></li>
        <li class="am-panel">
          <a class="am-cf" data-am-collapse="{parent:'#coll',target: '#collapse-nav'}">
	          <span class="am-icon-file"></span> 
	      	      系统管理
	      	  <span class="am-icon-angle-right am-fr am-margin-right"></span>
	      </a>
	      
          <ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav"><!-- 加入.am-in;折叠列表默认展开 -->
            <li><a href="../system/admin-user.html" class="am-cf"><span class="am-icon-check"></span> 个人资料<span class="am-icon-star am-fr am-margin-right admin-icon-yellow"></span></a></li>
            <li><a href="../system/admin-help.html"><span class="am-icon-puzzle-piece"></span> 帮助页</a></li>
            <li><a href="../system/admin-gallery.html"><span class="am-icon-th"></span> 相册页面<span class="am-badge am-badge-secondary am-margin-right am-fr">24</span></a></li>
            <li><a href="../system/admin-log.html"><span class="am-icon-calendar"></span> 系统日志</a></li>
            <li><a href="../system/admin-404.html"><span class="am-icon-bug"></span> 404</a></li>
          </ul>
        </li>
        <li class="am-panel">
        	<a class="am-cf" data-am-collapse="{parent:'#coll', target:'#collapse-nav2'}">
        		<span class="am-icon-group"></span>
        		 用户管理
        		<sapn class="am-icon-angle-right am-fr am-margin-right"></sapn>
        	</a>
        	<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav2">
        		<li><a href=""><span class="am-icon-male"></span> 系统用户</a></li>
        		<li><a href="../system/agent.html"><span class="am-icon-user"></span> 代理管理</a></li>
        		<li><a href=""><span class="am-icon-child"></span> 玩家信息</a></li>
        	</ul>
        </li>
        <li class="am-panel">
        	<a class="am-cf" data-am-collapse="{parent:'#coll',target:'#collapse-nav3'}">
        		<span class="am-icon-group"></span>
        		 平台管理
        		<sapn class="am-icon-angle-right am-fr am-margin-right"></sapn>
        	</a>
        	<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav3">
        		<li><a href=""><span class="am-icon-male"></span> 公告管理</a></li>
        		<li><a href=""><span class="am-icon-user"></span> 房间战绩</a></li>
        		<li><a href=""><span class="am-icon-child"></span> 游戏重置</a></li>
        		<li><a href=""><span class="am-icon-child"></span> 反馈信息</a></li>
        	</ul>
        </li>
        <li class="am-panel">
        	<a class="am-cf" data-am-collapse="{parent:'#coll',target:'#collapse-nav4'}">
        		<span class="am-icon-group"></span>
        		 财务管理
        		<sapn class="am-icon-angle-right am-fr am-margin-right"></sapn>
        	</a>
        	<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav4">
        		<li><a href=""><span class="am-icon-male"></span> 代理充值</a></li>
        		<li><a href=""><span class="am-icon-user"></span> 玩家充值</a></li>
        		<li><a href=""><span class="am-icon-child"></span> 线上充值</a></li>
        		<li><a href=""><span class="am-icon-child"></span> 结算管理</a></li>
        		<li><a href=""><span class="am-icon-child"></span> 提现管理</a></li>
        	</ul>
        </li>
        <li class="am-panel">
        	<a class="am-cf" data-am-collapse="{parent:'#coll',target:'#collapse-nav5'}">
        		<span class="am-icon-sign-out"></span>
        		 商城管理
        		<sapn class="am-icon-angle-right am-fr am-margin-right"></sapn>
        	</a>
        	<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav5">
        		<li><a href=""><span class="am-icon-male"></span> 商品价格管理</a></li>
        		<li><a href=""><span class="am-icon-user"></span> 商品属性管理</a></li>
        	</ul>
        </li>
        <li class="am-panel">
        	<a class="am-cf" data-am-collapse="{parent:'#coll',target:'#collapse-nav6'}">
        		<span class="am-icon-pencil-square-o"></span>
        		统计管理
        		<sapn class="am-icon-angle-right am-fr am-margin-right"></sapn>
        	</a>
        	<ul class="am-list am-collapse admin-sidebar-sub" id="collapse-nav6">
        		<li><a href=""><span class="am-icon-male"></span> 总金额统计</a></li>
        		<li><a href=""><span class="am-icon-user"></span> 线下重置月报表</a></li>
        		<li><a href=""><span class="am-icon-child"></span> 线上充值月报表</a></li>
        		<li><a href=""><span class="am-icon-child"></span> 在线人数</a></li>
        		<li><a href=""><span class="am-icon-child"></span> 下载数量</a></li>
        	</ul>
        </li>
      </ul>

      <div class="am-panel am-panel-default admin-sidebar-panel">
        <div class="am-panel-bd">
          <p><span class="am-icon-bookmark"></span> 公告</p>
          <p>时光静好，与君语；细水流年，与君同。—— Amaze UI</p>
        </div>
      </div>

      <div class="am-panel am-panel-default admin-sidebar-panel">
        <div class="am-panel-bd">
          <p><span class="am-icon-tag"></span> wiki</p>
          <p>Welcome to the Amaze UI wiki!</p>
        </div>
      </div>
    </div>
  </div>
  <!-- sidebar end 侧边栏结束 -->

  <!-- content start 主体内容开始 -->
  <div class="admin-content">
    <div class="admin-content-body">
      <div class="am-cf am-padding">
        <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">代理管理</strong> / <small>代理资料</small></div>
      </div>
		
      <div class="am-g">
        <div class="am-u-sm-12 ">
          <table class="am-table am-table-bd am-table-bordered  am-table-striped am-table-hover ">
            <thead>
            <tr>
              <th>ID</th>
              <th>用户名</th>
              <th>剩余房卡</th>
              <th>销售房卡</th>
              <th>管理</th>
            </tr>
            </thead>
            <tbody id="tb" ag-repeat="ag in agent">
            <tr>
            	<td>{{ag.AGENT_CODE}}</td>
            	<td>{{ag.AGENT_NAME}}</td>
            	<td><a href="#">{{ag.CARD_NUM}}</a></td> 
            	<td><span class="am-badge am-badge-success">{{ag.SALE_TOTAL}}</span></td>
              <td>
                <div class="am-dropdown" data-am-dropdown>
                  <button class="am-btn am-btn-secondary am-btn-xs am-dropdown-toggle" data-am-dropdown-toggle><span class="am-icon-cog"></span> <span class="am-icon-caret-down"></span></button>
                  <ul class="am-dropdown-content">
                    <li><a class="am-btn am-btn-success" href="#">修改</a></li>
                    <li><a href="#">查看</a></li>
                    <li><a href="#">删除</a></li>
                  </ul>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
	  
	  <ul data-am-widget="pagination" class="am-pagination am-pagination-select">
	      <li class="am-pagination-prev ">
	        <a href="#" class="">上一页</a>
	      </li>
	        <li class="am-pagination-select">
	          <select>
	              <option value="#" class="">1
	                / {{totalPage}}
	              </option>
	              <option value="#" class="">2
	                / {{totalPage}}
	              </option>
	              <option value="#" class="">3
	                / {{totalPage}}
	              </option>
	          </select>
	        </li>
	      <li class="am-pagination-next ">
	        <a href="#" class="">下一页</a>
	      </li>
  	  </ul>
	  
    </div>

    <footer class="admin-content-footer">
      <hr>
      <p class="am-padding-left">Copyright ©  2018</p>
    </footer>
  </div>
  <!-- content end 主体内容结束 -->

</div>

<a href="#" class="am-icon-btn am-icon-th-list am-show-sm-only admin-menu" data-am-offcanvas="{target: '#admin-offcanvas'}"></a>
</body>
<!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="../amazeUI/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->

<!--[if (gte IE 9)|!(IE)]><!-->
<script src="../amazeUI/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="../amazeUI/js/amazeui.min.js"></script>
<script src="../htmlResources/js/angular.min.js"></script>
<script src="../amazeUI/js/app.js"></script>
<script type="text/javascript">
	/* var app = angular.module('mainApp',[]);
	app.controller('mainController',function($http,$scope){
		console.log("start");
		$scope.agent=null;//代理列表
		$scope.page = null;//当前页数
		$scope.agt = null;//代理信息
		$scope.totalPage = null;//总页数
		$scope.total = null;//总记录数
		var agentUrl = "../agentApp/getAgentList.do";//访问地址
		$http.get(agentUrl).success(function(data){
			$scope.agent = data[0].agentList;
			$scope.page = data[0].page;
			$scope.agt = data[0].agent;
			$scope.totalPage = data[0].totalPage;
			$scope.total = data[0].total;
			console.log(data[0].agentList);
		});
	}); */
	
	$(function(){
		getAgentList()
	});
	function getAgentList(){
		$('#tb').html("");//清空tbody
		$.ajax({
			url:"../agentApp/getAgentList.do",
			data:{},
			type:"get",
			dataType:"json",
			success:function(data){
				$.each(data[0].agentList,function(i,item){
					console.log(item);
					$('#tb').append(
							"<tr>"+
			            	"<td>"+item.AGENT_CODE+"</td>"+
			            	"<td>"+item.AGENT_NAME+"</td>"+
			            	"<td>"+item.CARD_NUM+"</td>"+
			            	"<td>"+item.SALE_TOTAL+"</td>"+
			            	"<td><div class='am-dropdown' data-am-dropdown>"+
			            	"<button class='am-btn am-btn-secondary am-btn-xs am-dropdown-toggle' data-am-dropdown-toggle><span class='am-icon-cog'></span> <span class='am-icon-caret-down'></span></button>"+
			            	"<ul class='am-dropdown-content'>"+
			            	"<li><a class='am-btn am-btn-success' href='#'>修改</a></li>"+
			            	"<li><a href='#'>查看</a></li>"+
			            	"<li><a href='#'>删除</a></li>"+
			            	"</ul>"+
			            	"</div></td>"+
			            	"</tr>"
							);
				});
			}
		});
	}
</script>
</html>






