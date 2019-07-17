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
<body id="sds">
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
				<button type="button" onclick="search();" style="margin: 0px;" class="am-btn am-btn-success am-u-sm-4 am-fr am-radius">
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
						  	<span class="am-fl"><strong>${var.NICK_NAME }</strong></span>
					      	<div class="am-fr">
					    		<span>充值人:</span>
					    		<span>${var.CREATOR }</span>
					    	</div>
							<div class="am-fl am-margin-left-lg">${var.USER_ID }</div>
						  </div>
						  <!-- 面板内容 -->
						  <div class="am-panel-bd am-text-xs">
						  	<ul class="am-avg-sm-3">
						  		<li>
							    	<div class="">
							    		<span>充值数量:</span>
							    		<span>${var.CHARGE_NUM }</span>
							    	</div>
						  		</li>
						  		<li>
							    	<div class="">
							    		<span>充值金额:</span>
							    		<span>${var.MONEY }</span>
							    	</div>
						  		</li>
						  		<li>
							    	<div class="">
							    		<i class="am-badge am-badge-danger am-round">
							    		<c:if test="${var.CHARGE_TYPE=='1' }">购买</c:if>
							    		<c:if test="${var.CHARGE_TYPE=='2' }">赠送</c:if>
							    		<c:if test="${var.CHARGE_TYPE=='3' }">赔偿</c:if>
							    		<c:if test="${var.CHARGE_TYPE=='4' }">奖励</c:if>
							    		</i>
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
		</form>
		<div class="am-text-center">
			<span class="am-text-xs am-text-danger">共<mark>${pd.totalPage}</mark>页,<mark>${pd.total }</mark>条数据</span>
		</div>
    </div>
    <div class=" am-u-sm-12  am-u-md-5 am-u-sm-centered am-cf " id="jin">
    	<c:if test="${pd.totalPage>1 }">
			<button type="button" class="am-btn am-btn-success am-radius am-u-sm-12  am-u-sm-centered am-center" id="flip">加载更多</button>
    	</c:if>
    </div>
	
    <footer class="admin-content-footer" id="footered">
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

	/*绑定事件,点击加载更多,底部加载更多*/
    $(function() {
	  var i = 1;//页数
	  var form = $('#Form');
	  var page = ${pd.totalPage};//总页数,刷新次数为总页数减一
	  var keywords = '${pd.keywords}';//关键词
	  console.log(keywords);
	  $('#flip').on('click', function() {
		if(i>=page){
			//加载到最后
			//$("#flip").hide();
		}else{
		    i++;
		    $("#flip").html("<i class='am-icon-spinner am-icon-spin'></i> 加载中");
		    $(this).attr("disabled","true"); //设置变灰按钮 ,禁止重复点击多次请求 
			loadMore(i,keywords);
		    $("#flip").html("加载更多");
			if(i==page){//最后一页
				$("#flip").hide();
			}
		    console.log("页数:"+i);
		}
	  })
	})
	
	//加载更多内容
	function loadMore(page,keywords){
		var totalPage = ${pd.totalPage};//总页数,刷新次数为总页数减一
		var form = $("#Form");
		//ajax添加
		$.ajax({
			url:"userRechargeApp/loadMoreURList.do",
			data:{"page":page,"keywords":keywords},
			dataType:"json",
			async:true,//是否异步,默认true为异步,同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
			success:function(data){
				var list = data[0].list;
				//console.log(list);
				for(var a=0;a<list.length;a++){
					if(list[a].MONEY == undefined){
						list[a].MONEY = "";
					}
					var type = list[a].CHARGE_TYPE;
					if(type == '1'){
						type = "购买";
					}else if(type == '2'){
						type = "赠送";
					}else if(type == '3'){
						type = "赔偿";
					}else if(type == '4'){
						type = "奖励";
					}else{
						type = "";
					}
					var child = '<div class="am-panel am-panel-secondary am-text-sm am-margin-bottom-sm">'+
					  '<!-- 面板头部 -->'+
					  '<div class="am-panel-hd am-cf">'+
					  	'<span class="am-fl"><strong>'+list[a].NICK_NAME +'</strong></span>'+
					  	'<div class="am-fr">'+
			    			'<span>充值人:</span>'+
			    			'<span>'+list[a].CREATOR +'</span>'+
				    	'</div>'+
						'<div class="am-fl am-margin-left-lg">'+list[a].USER_ID +'</div>'+
					  '</div>'+
					  '<!-- 面板内容 -->'+
					  '<div class="am-panel-bd am-text-xs">'+
					  	'<ul class="am-avg-sm-3">'+
					  		'<li>'+
						    	'<div class="">'+
						    		'<span>充值数量:</span>'+
						    		'<span>'+list[a].CHARGE_NUM +'</span>'+
						    	'</div>'+
					  		'</li>'+
					  		'<li>'+
						    	'<div class="">'+
						    		'<span>充值金额:</span>'+
						    		'<span>'+list[a].MONEY +'</span>'+
						    	'</div>'+
					  		'</li>'+
					  		'<li>'+
						    	'<div class="">'+
						    		'<i class="am-badge am-badge-danger am-round">'+type +'</i>'+
						    	'</div>'+
					  		'</li>'+
					  '	</ul>'+
					 ' </div>'+
					'</div>';
					$("#Form").append(child);
					$("#flip").html("加载更多");//加载完毕,回复到初始状态
					$("#flip").removeAttr('disabled');//给按钮解禁
				}
			}
		});
	}
	
	//固定顶部(手机网页不得行)(又可以了)
	$(function(){
		$("#sticky").sticky({
			top:49
		})
	})
	//搜索
	function search(){
		var keywords = $("#keywords").val();
		var url = "userRechargeApp/userRechargeList.do?keywords="+keywords;
		window.location.href=url;
	}
	
</script>
</html>
