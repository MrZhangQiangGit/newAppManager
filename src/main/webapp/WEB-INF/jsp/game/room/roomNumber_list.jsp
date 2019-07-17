<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib	prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="row">
						<div class="col-xs-12">
						<div class="page-header position-relative"><!-- 这个效果是页面上的虚线 -->
						</div>
						<div>
							房间号：<input type="text" id="roomNumber"/>&nbsp;<input type="button" value="重置" onclick="chongZhi()"/><sapn id="roomNumber_span"></sapn>
						</div>
						 <p>
					     <div style="color:red">请注意，重置房间号会立即解散该房间游戏玩家，游戏立即结束</div>
					     <p>
					     <div style="color:red">用于维护，请勿随便使用</div>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		
		
		//重置。dataType是返回值的类型，controller类中的输出类型必须与其一直，否则会执行error。除非没有使用输出。
		function chongZhi(){
			$("#roomNumber_span").text("");	//每次重置先清空上次的内容
			var roomId=$("#roomNumber").val();
			var reg=/^[0-9]+$/;
			if(!reg.exec(roomId)){
				alert("请输入数字");
				return;
			}
			var a=null;
			if(roomId==""){
				alert("房间号不能为空");
			}else{
				//先判断房间号是否存在，如果存在状态是否已经为结束
				$.ajax({
					url:"roomNumber/judge.do",
					type:"post",
					dataType:"text",
					data:{"ROOM_ID1":roomId},
					success:function(result1){
						if(result1==0){
							alert("该房间号不存在");
						}else if(result1==1){
							a=confirm("确定要重置"+roomId+"号房间状态吗?");
							console.log(a);
							if(a){
								$.ajax({
									url:"roomNumber/reset.do",
									type:"get",
									dataType:"text",
									data:{"ROOM_ID1":roomId},
									success:function(result){
										console.log("2"+result);
										$("#roomNumber").val("");
										$("#roomNumber_span").text(roomId+"号房间重置完毕");	
									},
									error:function(){
										console.log("失败");
									}
								});
							}
						}	
					},
					error:function(){
						console.log("查询失败");
					}
				}); 
			}
		}	
		


	</script>


</body>
</html>