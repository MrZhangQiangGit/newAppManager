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
					
					<form action="majroom/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="MAJROOM_ID" id="MAJROOM_ID" value="${pd.MAJROOM_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户Id:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="32" placeholder="这里输入用户Id" title="用户Id" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">操作类型:</td>
<%-- 								<td><input type="text" name="OPT_TYPE" id="OPT_TYPE" value="${pd.OPT_TYPE}" maxlength="2" placeholder="这里输入操作类型" title="操作类型" style="width:98%;"/></td> --%>
										<td><select name=OPT_TYPE>
										<c:choose>
											<c:when test="${pd.OPT_TYPE =='1'}">
												<option value="1" selected="selected">房卡充值</option>
												<option value="2">房卡开房</option>	
											</c:when>
											<c:otherwise>
												<option value="1" >房卡充值</option>
												<option value="2" selected="selected">房卡开房</option>
											</c:otherwise>
										</c:choose>
									</select> 
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">房卡数量:</td>
								<td><input type="number" name="CARD_NUM" id="CARD_NUM" value="${pd.CARD_NUM}" min="0" maxlength="32" placeholder="这里输入房卡数量" title="房卡数量" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">交易金额:</td>
								<td><input type="number" name="TRANMONEY" id="TRANMONEY" value="${pd.TRANMONEY}" min="0" maxlength="32" placeholder="这里输入交易金额" title="交易金额" style="width:98%;"/></td>
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
			if($("#USER_ID").val()==""){
				$("#USER_ID").tips({
					side:3,
		            msg:'请输入用户Id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_ID").focus();
			return false;
			}
			if($("#OPT_TYPE").val()==""){
				$("#OPT_TYPE").tips({
					side:3,
		            msg:'请输入操作类型',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#OPT_TYPE").focus();
			return false;
			}
			if($("#CARD_NUM").val()==""){
				$("#CARD_NUM").tips({
					side:3,
		            msg:'请输入房卡数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CARD_NUM").focus();
			return false;
			}
			if($("#TRANMONEY").val()==""){
				$("#TRANMONEY").tips({
					side:3,
		            msg:'请输入交易金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TRANMONEY").focus();
			return false;
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