<%@ page pageEncoding="UTF-8" %>

<!-- <nav data-am-widget="menu" class="am-menu  am-menu-offcanvas1" data-am-menu-offcanvas> 
    <a href="javascript: void(0)" class="am-menu-toggle">
          <i class="am-menu-toggle-icon am-icon-bars"></i>
    </a>
    <div class="am-offcanvas" >
      <div class="am-offcanvas-bar">
      <ul class="am-menu-nav am-avg-sm-1">
          <li class="am-parent">
            <a href="##" class="" >公司</a>
              <ul class="am-menu-sub am-collapse  am-avg-sm-2 ">
                  <li class="">
                    <a href="##" class="" >公司</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >人物</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >趋势</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >投融资</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >创业公司</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >创业人物</a>
                  </li>
                  <li class="am-menu-nav-channel"><a href="##" class="" title="公司">进入栏目 &raquo;</a></li>
              </ul>
          </li>
          <li class="am-parent">
            <a href="##" class="" >人物</a>
              <ul class="am-menu-sub am-collapse  am-avg-sm-3 ">
                  <li class="">
                    <a href="##" class="" >公司</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >人物</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >趋势</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >投融资</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >创业公司</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >创业人物</a>
                  </li>
              </ul>
          </li>
          <li class="am-parent">
            <a href="#c3" class="" >趋势</a>
              <ul class="am-menu-sub am-collapse  am-avg-sm-4 ">
                  <li class="">
                    <a href="##" class="" >公司</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >人物</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >趋势</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >投融资</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >创业公司</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >创业人物</a>
                  </li>
                  <li class="am-menu-nav-channel"><a href="#c3" class="" title="趋势">泥煤 &raquo;</a></li>
              </ul>
          </li>
          <li class="am-parent">
            <a href="##" class="" >投融资</a>
              <ul class="am-menu-sub am-collapse  am-avg-sm-3 ">
                  <li class="">
                    <a href="##" class="" >公司</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >人物</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >趋势</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >投融资</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >创业公司</a>
                  </li>
                  <li class="">
                    <a href="##" class="" >创业人物</a>
                  </li>
              </ul>
          </li>
          <li class="">
            <a href="##" class="" >创业公司</a>
          </li>
          <li class="">
            <a href="##" class="" >创业人物</a>
          </li>
      </ul>
      </div>
    </div>
  </nav> -->
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