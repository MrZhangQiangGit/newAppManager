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
		<form action="agentApp/${pd.msg }.do" method="post" id="form" class="am-form am-form-horizontal">
			<div class="am-g">
				<div class="am-u-sm-12 am-u-sm-centered am-u-md-6 am-u-lg-5 am-padding-xs">
					<table class="am-table am-bordered">
						<tr>
							<td>代理ID</td>
							<td><input readonly="readonly" type="text" id="AGENT_CODE" name="AGENT_CODE" value="${pd.AGENT_CODE }"/></td>
						</tr>
						<tr>
							<td>代理名称</td>
							<td><input id="AGENT_NAME" type="text" name="AGENT_NAME" value="${pd.AGENT_NAME }"/></td>
						</tr>
						<tr>
							<td>代理状态</td>
							<td>
								<select id="STATUS" name="STATUS" <c:if test="${pd.AGENT_CODE == pd.NUMBER }"> disabled="disabled"</c:if> >						
									<option <c:if test="${pd.STATUS==1 }">selected="selected"</c:if> value="1">有效</option>
									<option <c:if test="${pd.STATUS==2 }">selected="selected"</c:if> value="2">无效</option>
								</select>
							
							</td>
						</tr>
						<tr>
							<td>上级代理</td>
							<td><input disabled type="text" id="P_ID" name="P_ID" value="${pd.P_ID }"/></td>
						</tr>
						<tr>
							<td>性别</td>
							<td>
								<select id="SEX" name="SEX">
									<option <c:if test="${pd.SEX==1 }">selected="selected"</c:if> value="1">男</option>
									<option <c:if test="${pd.SEX==2 }">selected="selected"</c:if> value="2">女</option>
									<option <c:if test="${pd.SEX==3 }">selected="selected"</c:if> value="3">未知</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>手机号</td>
							<td><input type="text" id="PHONE_NO" name="PHONE_NO" value="${pd.PHONE_NO }"/></td>
						</tr>
						<tr>
							<td>推荐人ID</td>
							<td><input type="text" id="RECOMMAND_ID" name="RECOMMAND_ID" value="${pd.RECOMMAND_ID }"/></td>
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
		var name = $("#AGENT_NAME").val();
		if(name==""){
			$('#AGENT_NAME').popover({
				content:'不能为空!',
				theme:'danger sm'
			}).popover('open');
			return false;
		}
		/*
			ajax提交表单,
		*/
		var url = '${pd.msg}';
		console.log("提交路径:"+url);
		$.ajax({
			url:'agentApp/'+'${pd.msg}'+'.do',
			data:$("#form").serialize(),
			dataType:'json',
			type:'post',
			success:function(data){
				alert("修改成功!");
				location.reload();//刷新页面
			},
			error:function(data){
				alert("修改失败!");
				location.reload();
			}
		});
	}
	//取消修改并返回
	function cancel(){
		var type = ${pd.type};
		if(type == "1"){
			window.location.href="agentApp/agentList.do";
		}else{
			window.location.href="agentApp/goMe.do";			
		}
	}
</script>
</html>
