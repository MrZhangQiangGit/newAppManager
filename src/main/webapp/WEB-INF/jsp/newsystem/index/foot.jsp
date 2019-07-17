<%@ page pageEncoding="UTF-8"%>
<div class="am-ag ">
<div class="am-u-sm-12 am-u-sm-centered am-u-md-6 am-u-lg-5 am-padding-0">
<div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default am-btn-group" id="foot">
   <ul class="am-navbar-nav am-cf am-avg-sm-2 am-text-primary foot" style="padding: 0px;">
       <li class="am-btn am-btn-primary <c:if test='${pd.stas==1 }'>am-active</c:if>" name="but" onclick="go('mainApp/index');">
         <a  class="">
             <!-- <img src="http://amazeui.b0.upaiyun.com/assets/i/cpts/navbar/Information.png" alt="消息"/> -->
             <span class="am-icon-leaf" target="mainFrame" ></span>
             <span class="">管 理</span>
         </a>
       </li>
       <li class="am-btn am-btn-primary <c:if test='${pd.stas==2 }'>am-active</c:if>" name='but' target="mainFrame" onclick="go('agentApp/goMe.do');">
         <a  class="">
             <!-- <img src="http://amazeui.b0.upaiyun.com/assets/i/cpts/navbar/map.png" alt="地图"/> -->
             <span class="am-icon-user"></span>
             <span class="">我 的</span>
         </a>
       </li>
   </ul>
 </div>
 </div>
 </div>
 <script type="text/javascript">
	//获取选中的值
 	function go(url){
 		window.location.href=url;
 	}
 </script>