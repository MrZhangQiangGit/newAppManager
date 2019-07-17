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

<div class="admin-content">
    <div class="admin-content-body">
		<!-- hbody start -->
		<form action="" class="am-form am-form-horizontal">
			<div class="am-g">
				<div class="am-u-sm-12 am-u-sm-centered am-u-md-7 am-u-lg-5 am-margin">
					<legend class="am-text-danger">忘记密码请联系管理员</legend>
					<div class="am-form-group">
						<div class="am-g">
							<div class="am-u-sm-4">原密码:</div>
							<div class="am-u-sm-8"><input id="rpwd" type="password" placeholder="输入旧的密码"></div>
						</div>
					</div>
					<div class="am-form-group">
						<div class="am-g">
							<div class="am-u-sm-4">新密码:</div>
							<div class="am-u-sm-8"><input id="pwd" type="password" placeholder="请输入新密码"></div>
						</div>
					</div>
					<div class="am-form-group">
						<div class="am-g">
							<div class="am-u-sm-4">确认密码:</div>
							<div class="am-u-sm-8"><input id="tpwd" type="password" placeholder="再次输入新密码"></div>
						</div>
					</div>
					<div class="am-form-inline">
						<div class="am-g am-cf">
							<div class="am-center am-padding-horizontal">
								<input style="width: 40%;" type="button" class="am-btn am-btn-primary am-radius am-fl pwdbtn" value="确  定" onclick="editPwd();">
								<input style="width: 40%;" type="button" class="am-btn am-btn-warning am-radius am-fr pwdbtn" value="取  消" onclick="cancel();">
							</div>
						</div>
					</div>
				</div>
			</div>
		</form>
		<!-- hbody end -->
		<!-- <div class="" style="height: 100%;"> -->
			<!-- <iframe name="mainFrame" id="mainFrame" frameborder="0" src="tab.do" style="margin:0 auto;width:100%;height:100%;"></iframe> -->
		<!-- </div> -->
    </div>
	
    <footer class="admin-content-footer">
      <hr>
      <p class="am-padding-left">Copyright ©  2018</p>
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
	//确定修改
	function editPwd(){
		var rpwd = $("#rpwd").val();
		var pwd = $("#pwd").val();
		var tpwd = $("#tpwd").val();
		if(rpwd==""){
			$('#rpwd').popover({
				content:'请输入旧密码!',
				theme:'danger sm'
			}).popover('open');
			return false;
		}
		if(pwd==""){
			$('#pwd').popover({
				content:'请输入新密码!',
				theme:'danger sm'
			}).popover('open');
			return false;
		}
		if(tpwd==""){
			$('#tpwd').popover({
				content:'请输入确定密码!',
				theme:'danger sm'
			}).popover('open');
			return false;
		}
		if(pwd != tpwd){
			$('#pwd').popover({
				content:'两次密码不一致!',
				theme:'danger sm'
			}).popover('open');
			return false;
		}
		$.ajax({
			url:'agentApp/editPwd.do',
			data:{"rpwd":rpwd,"pwd":pwd},
			dataType:'json',
			type:'post',
			success:function(data){
				console.log(data[0].msg);
				if(data[0].msg=='no'){
					$("#rpwd").popover({
						content:'原密码错误!',
						theme:'danger sm'
					}).popover('open');
					return false;
				}
				if(data[0].msg=='ok'){
					alert("修改成功!");
					$("#rpwd").val("");
					$("#pwd").val("");
					$("#tpwd").val("");
				}
			}
		});
		
	}
	//取消修改
	function cancel(){
		$("#rpwd").val("");
		$("#pwd").val("");
		$("#tpwd").val("");
	}
</script>
</html>
