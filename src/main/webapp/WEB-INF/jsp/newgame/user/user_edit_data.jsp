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
		<form action="userApp/${pd.msg }.do" method="post" id="form" class="am-form am-form-horizontal">
			<div class="am-g">
				<div class="am-u-sm-12 am-u-sm-centered am-u-md-6 am-u-lg-5 am-padding-xs">
					<input hidden type="hidden" id="MAJUSER_ID" name="MAJUSER_ID" value="${pd.MAJUSER_ID }"/>
					<table class="am-table am-bordered">
						<tr>
							<td>游戏ID</td>
							<td><input <c:if test="${pd.USER_ID != '' }"> readonly="readonly"</c:if> type="text" id="USER_ID" name="USER_ID" value="${pd.USER_ID }"/></td>
						</tr>
						<tr>
							<td>玩家昵称</td>
							<td><input id="NICK_NAME" type="text" name="NICK_NAME" value="${pd.NICK_NAME }"/></td>
						</tr>
						<tr>
							<td>玩家状态</td>
							<td>
								<select id="STATUS" name="STATUS">
									<option <c:if test="${pd.STATUS==0 }">selected="selected"</c:if> value="0">正常</option>
									<option <c:if test="${pd.STATUS==1 }">selected="selected"</c:if> value="1">禁用</option>
								</select>
							
							</td>
						</tr>
						<tr>
							<td>性别</td>
							<td>
								<select id="SEX" name="SEX">
									<option <c:if test="${pd.SEX==0 }">selected="selected"</c:if> value="0">男</option>
									<option <c:if test="${pd.SEX==1 }">selected="selected"</c:if> value="1">女</option>
								</select>
							
							</td>
						</tr>
						<tr>
							<td>城市</td>
							<td><input type="text" id="CITY" name="CITY" value="${pd.CITY }"/></td>
						</tr>
						<tr>
							<td>省份</td>
							<td><input type="text" id="PROVINCE" name="PROVINCE" value="${pd.PROVINCE }"/></td>
						</tr>
						<tr>
							<td>手机号</td>
							<td><input type="text" id="PHONE_NO" name="PHONE_NO" value="${pd.PHONE_NO }"/></td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="am-g am-cf">
									<div class="am-center am-padding-horizontal">
										<input style="width: 40%;" type="button" class="am-btn am-btn-primary am-radius am-fl pwdbtn" value="提  交" onclick="editData();">
										<input style="width: 40%;" type="button" class="am-btn am-btn-warning am-radius am-fr pwdbtn" value="取  消" onclick="cancel();">
									</div>
								</div>
							</td>
						</tr>
					</table>
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
	function editData(){
		var name = $("#NICK_NAME").val();
		if(name==""){
			$('#NICK_NAME').popover({
				content:'不能为空!',
				theme:'danger sm'
			}).popover('open');
			return false;
		}
		/*
			ajax提交表单,
		*/
		var url = '${msg}';
		console.log("提交路径:"+url);
		$.ajax({
			url:'userApp/'+'${msg}'+'.do',
			data:$("#form").serialize(),//表单序列化
			dataType:'json',
			type:'post',
			success:function(data){
				alert("修改成功!");
				location.reload();//刷新页面
			},
			error:function(data){
				alert("修改失败!");
				//location.reload();
			}
		});
	}
	//取消修改并返回
	function cancel(){
		window.location.href="userApp/lowerUserList.do";
	}
</script>
</html>
