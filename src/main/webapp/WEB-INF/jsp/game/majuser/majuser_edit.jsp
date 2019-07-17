<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
					
					<form action="majuser/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="MAJUSER_ID" id="MAJUSER_ID" value="${pd.MAJUSER_ID}"/>
						<input type="hidden" name="CREAT_TIME" id="CREAT_TIME" value="${pd.CREAT_TIME}"/>
						<input type="hidden" name="USER_ID" id="USER_ID" value="${pd.USER_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
						
							<tr>
							<td style="width:75px;text-align: right;padding-top: 13px;">微信id:</td>
							<td><input type="text" readonly name="OPENID" id="OPENID" value="${pd.OPENID}" maxlength="32" placeholder="这里输入微信id" title="微信id" style="width:98%;"/></td></tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">昵称:</td>
								<td><input type="text" name="NICK_NAME" id="NICK_NAME" value="${pd.NICK_NAME}" maxlength="32" placeholder="这里输入昵称" title="昵称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
<%-- 								<td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="6" placeholder="这里输入状态" title="状态" style="width:98%;"/></td> --%>
								<td><select name="STATUS" style="vertical-align:top;width:98%;height:30px;">
										<c:choose>
											<c:when test="${pd.STATUS =='0'}">
												<option value="0" selected="selected">正常</option>
												<option value="1">禁用</option>	
											</c:when>
											<c:otherwise>
												<option value="0" >正常</option>
												<option value="1" selected="selected">禁用</option>
											</c:otherwise>
										</c:choose>
									</select> 
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">性别:</td>
								<td>
									<select name="SEX" id="SEX" data-placeholder="这里选择性别" style="vertical-align:top;width:98%;height:30px;">
											<c:choose>
											<c:when test="${pd.SEX =='1'}">
												<option value="1" selected="selected">女</option>
												<option value="0">男</option>	
											</c:when>
											<c:otherwise>
												<option value="1" >女</option>
												<option value="0" selected="selected">男</option>
											</c:otherwise>
										</c:choose>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">省份:</td>
								<td><input type="text" name="PROVINCE" id="PROVINCE" value="${pd.PROVINCE}" maxlength="64" placeholder="这里输入省份" title="省份" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">城市:</td>
								<td><input type="text" name="CITY" id="CITY" value="${pd.CITY}" maxlength="64" placeholder="这里输入城市" title="城市" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">手机号码:</td>
								<td><input type="text" name="PHONE" id="PHONE" value="${pd.PHONE}" maxlength="20" placeholder="这里输入手机号码" title="手机号码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
						</table>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>
<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#STATUS").val()==""){
				$("#STATUS").tips({
					side:3,
		            msg:'请选择状态',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATUS").focus();
			return false;
			}
			var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if($("#PHONE").val() !="" && $("#PHONE").val() !=null){
				if(!myreg.test($("#PHONE").val())){
					$("#PHONE").tips({
						side:3,
			            msg:'手机号码有误，请重新输入',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#PHONE").focus();
				return false;
				}
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>