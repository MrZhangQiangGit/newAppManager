<%@ page pageEncoding="UTF-8" %>
<%-- <header class="am-topbar am-topbar-inverse admin-header">
  <div class="am-topbar-brand">
    <strong><big>management</big></strong> <small>${pd.SYSNAME }</small>
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
</header>  --%>

<div class="am-ag ">
<div class="am-u-sm-12 am-u-sm-centered am-u-md-6 am-u-lg-5 am-padding-0">
<header data-am-widget="header" class="am-header am-header-default ">
      <div class="am-header-left am-header-nav">
          <a href="javascript:history.back(-1);" class="">
              <i class="am-header-icon am-icon-angle-left"></i>
              <span class="am-header-nav-title">返回</span>
          </a>
      </div>
      <h2 class="am-header-title">
          <c:if test="${pd.stas==1 }">${pd.SYSNAME }</c:if>
          <c:if test="${pd.stas==2 }">${pd.menuName }</c:if>
      </h2>
      <div class="am-header-right am-header-nav">
          <ul class="">
	          <li class="am-dropdown" data-am-dropdown>
		          <span href="javascript:;" class="am-dropdown-toggle" data-am-dropdown-toggle>
		              <span class="am-header-nav-title ">菜单</span>
		                <i class="am-header-icon am-icon-bars"></i>
		          </span>
		        <!-- <a class="am-dropdown-toggle" data-am-dropdown-toggle href="javascript:;">
		          <span class="am-icon-users"></span> 管理员 <span class="am-icon-caret-down"></span>
		        </a> -->
		        <ul class="am-dropdown-content">
		          <li><a href="mainApp/index"><span class="am-icon-home"></span> 首页</a></li>
		          <li><a href="#"><span class="am-icon-user"></span> 资料修改</a></li>
		          <!-- <li><a href="javascript:;" id="admin-fullscreen"><span class="am-icon-arrows-alt"></span class="admin-fullText">开启全屏</a></li> -->
		          <li><a href="logoutApp"><span class="am-icon-power-off"></span> 退出</a></li>
		        </ul>
	      	</li>
      	</ul>
      </div>
  </header>
      </div>
      
      </div>