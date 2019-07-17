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
					    	<ul class="am-fr am-margin-0" >
						          <li class="am-dropdown" data-am-dropdown><!-- data-am-dropdown -->
							        <span href="javascript:;" class="am-dropdown-toggle" data-am-dropdown-toggle><!-- data-am-dropdown-toggle -->
						              <span class="am-header-nav-title ">更多</span>
						              <i class="am-header-icon am-icon-bars"></i>
							        </span>
							        <ul class="am-dropdown-content am-margin-0 am-padding-0 am-avg-sm-1">
							          <li onclick="goEidtData('${var.MAJUSER_ID}');" class="am-btn am-margin-0 am-padding-0 am-radius">
							          	<a href="javascript:;">
							          	<span class="am-icon-pencil-square-o am-text-success"></span>
							          	<span>资料修改</span>
							          	</a>
							          </li>
							          <li class="am-divider"></li>
							          <li class="am-btn  am-margin-0 am-padding-0 am-radius">
							          	<a href="javascript:;">
							          		<span class="am-icon-users am-text-secondary"></span>
							          		<span>查看玩家</span>
							          	</a>
							          </li>
							          <li class="am-divider"></li>
							          <li onclick="goUserRecharge('${var.USER_ID}');" class="am-btn  am-margin-0 am-padding-0 am-radius">
							          	<a href="javascript:;">
							          		<span class="am-icon-paypal am-text-warning"></span>
							          		<span>充值</span>
							          	</a>
							          </li>
							        </ul>
						      	</li>
					      	</ul>
						  	<span class="am-fr">${var.USER_ID }</span>
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
							    		<span>${var.ROOM_CARD }</span>
							    	</div>
						  		</li>
						  		<li>
							    	<div class="">
							    		<span>邀请码:</span>
							    		<span>${var.YQ_CODE }</span>
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
			<%-- <div class="am-text-center">
				<ul class="am-avg-sm-4 am-margin-xs">
					<li>
						<input type="button" class="am-btn am-btn-success am-radius" onclick="flip(-1);" value="上一页"/>
					</li>
					<li class="am-ag">
						<input id="page" name="page" type="number" class="am-u-10 am-text-center" height="37px;" value="${pd.page }" />
					</li>
					<li>
						<input type="button" class="am-btn am-btn-success am-radius" value="下一页"/> <!-- onclick="flip(1);" -->
					</li>
					<li>
						<input type="button" class="am-btn am-btn-success am-radius" onclick="flip(0);" value="跳转"/>
					</li>
				</ul>
				<span class="am-text-xs am-text-danger">共<mark>${pd.totalPage}</mark>页,<mark>${pd.total }</mark>条数据</span>
			</div> --%>
			
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
	
//滚动条到页面底部加载更多案例   
$(function(){
	$("#sds").scroll(function(){  
	      console.log("swdjisn");
		 var scrollTop = $(this).scrollTop();    //滚动条距离顶部的高度  
		 var scrollHeight = $(document).height();   //当前页面的总高度  
		 var clientHeight = $(this).height();    //当前可视的页面高度  
		 console.log(scrollTop+"*-*-"+scrollHeight+"/*/-/-"+clientHeight);
		 // console.log("top:"+scrollTop+",doc:"+scrollHeight+",client:"+clientHeight);  
		 if(scrollTop + clientHeight >= scrollHeight){   //距离顶部+当前高度 >=文档总高度 即代表滑动到底部   
		     //滚动条到达底部  
		     alert(3);
		 }else if(scrollTop<=0){  
		    //滚动条到达顶部  
		     alert(4); 
		 //滚动条距离顶部的高度小于等于0 TODO  
		 }  
	})
});
$(document).ready(function(){  
    $("#form").unbind("scroll").bind("scroll", function(e){  
        var sum = this.scrollHeight;  
        if (sum <= $(this).scrollTop() + $(this).height()) {  
            //$(".main").append($(".child").clone());  
            console.log("测试@");
        }  
    });  
});  
	
	$(function() {
	  $('#jin').on('inview.scrollspy.amui', function() {
	    console.log('进入视口');
	  }).on('outview.scrollspy.amui', function() {
	    console.log('离开视口');
	  });
	});


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
		    //$("#flip").html("加载更多");
			if(i==page){//最后一页
				$("#flip").hide();
			}
		    console.log("页数:"+i);
		}
	  })
	})
	
	//绑定下拉事件
	$(function(){
		var form = $('#Form');
		form.on("click",".am-dropdown",function(e){
			//console.log("wojinlaile ");
			$(this).dropdown('toggle');
			//console.log("wojinlaile2");
			return false;
		});
	});
	
	/* window.onload = function(){
		var form = $('#Form');
		form.on("click",".am-dropdown",function(e){
			console.log("wojinlaile ");
			$(this).dropdown('toggle');
			return false;
		});
	} */
	//加载更多内容
	function loadMore(page,keywords){
		var totalPage = ${pd.totalPage};//总页数,刷新次数为总页数减一
		var form = $("#Form");
		//ajax添加
		$.ajax({
			url:"userApp/loadMoreUserList.do",
			data:{"page":page,"keywords":keywords},
			dataType:"json",
			async:true,//是否异步,默认true为异步,同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
			success:function(data){
				var list = data[0].list;
				//console.log(list);
				for(var a=0;a<list.length;a++){
					if(list[a].YQ_CODE == undefined){
						list[a].YQ_CODE = "";
					}
					var child = '<div class="am-panel am-panel-secondary am-text-sm am-margin-bottom-sm">'+
					  '<!-- 面板头部 -->'+
					  '<div class="am-panel-hd am-cf">'+
					  	'<span class="am-fl"><strong>'+list[a].NICK_NAME +'</strong></span>'+
				    	'<ul class="am-fr am-margin-0" >'+
					          '<li class="am-dropdown" data-am-dropdown>'+
						        '<span class="am-dropdown-toggle" data-am-dropdown-toggle>'+
					              '<span class="am-header-nav-title ">更多</span>'+
					              '<i class="am-header-icon am-icon-bars"></i>'+
						        '</span>'+
						        '<ul class="am-dropdown-content am-margin-0 am-padding-0 am-avg-sm-1">'+
						          '<li onclick="goEidtData(\''+list[a].MAJUSER_ID+'\');" class="am-btn am-margin-0 am-padding-0 am-radius">'+
						          	'<a href="javascript:;">'+
						          	'<span class="am-icon-pencil-square-o am-text-success"></span>'+
						          	'<span>资料修改</span>'+
						          	'</a>'+
						          '</li>'+
						          '<li class="am-divider"></li>'+
						          '<li class="am-btn  am-margin-0 am-padding-0 am-radius">'+
						          	'<a href="javascript:;">'+
						          		'<span class="am-icon-users am-text-secondary"></span>'+
						          		'<span>查看玩家</span>'+
						          	'</a>'+
						          '</li>'+
						          '<li class="am-divider"></li>'+
						          '<li onclick="goUserRecharge(\''+list[a].USER_ID+'\');" class="am-btn  am-margin-0 am-padding-0 am-radius">'+
						          	'<a href="javascript:;">'+
						          		'<span class="am-icon-paypal am-text-warning"></span>'+
						          		'<span>充值</span>'+
						          	'</a>'+
						          '</li>'+
						        '</ul>'+
					      	'</li>'+
				      	'</ul>'+
					  	'<span class="am-fr">'+list[a].USER_ID +'</span>'+
					  '</div>'+
					  <!-- 面板内容 -->'+
					  '<div class="am-panel-bd am-text-xs">'+
					  	'<ul class="am-avg-sm-3">'+
					  		'<li>'+
						    	'<div class="">'+
						    		/* '<c:if test="${var.LEVEL=='0' }"><i class="am-badge am-badge-danger am-round">总代理</i></c:if>'+
						    		'<c:if test="${var.LEVEL=='1' }"><i class="am-badge am-badge-primary am-round">一级代理</i></c:if>'+
						    		'<c:if test="${var.LEVEL=='2' }"><i class="am-badge am-badge-secondary am-round">二级代理</i></c:if>'+
						    		'<c:if test="${var.LEVEL=='3' }"><i class="am-badge am-badge-success am-round">三级代理</i></c:if>'+
						    		'<c:if test="${var.LEVEL=='vip' }"><i class="am-badge am-badge-warning am-round">vip</i></c:if>'+ */
						    	'</div>'+
					  		'</li>'+
					  		'<li>'+
						    	'<div class="">'+
						    		'<span>剩余房卡:</span>'+
						    		'<span>'+list[a].ROOM_CARD +'</span>'+
						    	'</div>'+
					  		'</li>'+
					  		'<li>'+
						    	'<div class="">'+
						    		'<span>邀请码:</span>'+
						    		'<span>'+list[a].YQ_CODE +'</span>'+
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
	//去修改资料页面
	function goEidtData(id){
		window.location.href = "userApp/goEditUser.do?MAJUSER_ID="+id;
	}
	//去充值页面
	function goUserRecharge(id){				
		window.location.href = "userRechargeApp/goUserRecharge.do?USER_ID="+id;
	}
	//搜索
	function search(){
		var keywords = $("#keywords").val();
		var url = "userApp/lowerUserList.do?keywords="+keywords;
		window.location.href=url;
	}
	
</script>
</html>
