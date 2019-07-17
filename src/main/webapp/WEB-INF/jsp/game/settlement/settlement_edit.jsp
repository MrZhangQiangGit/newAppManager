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
					
					<form action="settlement/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">交易流水id:</td>
								<td><input type="text" name="TRAN_ID" id="TRAN_ID" value="${pd.TRAN_ID}" maxlength="255" placeholder="这里输入交易流水id" title="交易流水id" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">玩家游戏id:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="255" placeholder="这里输入玩家游戏id" title="玩家游戏id" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">游戏昵称:</td>
								<td><input type="text" name="USER_NICK_NAME" id="USER_NICK_NAME" value="${pd.USER_NICK_NAME}" maxlength="255" placeholder="这里输入游戏昵称" title="游戏昵称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">交易金额:</td>
								<td><input type="text" name="TRAN_MONEY" id="TRAN_MONEY" value="${pd.TRAN_MONEY}" maxlength="255" placeholder="这里输入交易金额" title="交易金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">代理商id:</td>
								<td><input type="text" name="AGENT_ID" id="AGENT_ID" value="${pd.AGENT_ID}" maxlength="255" placeholder="这里输入代理商id" title="代理商id" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">分润比例:</td>
								<td><input type="text" name="AGENT_PROFIT_BL" id="AGENT_PROFIT_BL" value="${pd.AGENT_PROFIT_BL}" maxlength="255" placeholder="这里输入分润比例" title="分润比例" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">分润金额:</td>
								<td><input type="text" name="AGENT_PROFIT_MONEY" id="AGENT_PROFIT_MONEY" value="${pd.AGENT_PROFIT_MONEY}" maxlength="255" placeholder="这里输入分润金额" title="分润金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">交易内容:</td>
								<td><input type="text" name="TRAN_CONTENT" id="TRAN_CONTENT" value="${pd.TRAN_CONTENT}" maxlength="255" placeholder="这里输入交易内容" title="交易内容" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">是否付款:</td>
								<td><input type="text" name="STATUS" id="STATUS" value="${pd.STATUS}" maxlength="255" placeholder="这里输入是否付款" title="是否付款" style="width:98%;"/></td>
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
			if($("#TRAN_ID").val()==""){
				$("#TRAN_ID").tips({
					side:3,
		            msg:'请输入交易流水id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TRAN_ID").focus();
			return false;
			}
			if($("#USER_ID").val()==""){
				$("#USER_ID").tips({
					side:3,
		            msg:'请输入玩家游戏id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_ID").focus();
			return false;
			}
			if($("#USER_NICK_NAME").val()==""){
				$("#USER_NICK_NAME").tips({
					side:3,
		            msg:'请输入游戏昵称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_NICK_NAME").focus();
			return false;
			}
			if($("#TRAN_MONEY").val()==""){
				$("#TRAN_MONEY").tips({
					side:3,
		            msg:'请输入交易金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TRAN_MONEY").focus();
			return false;
			}
			if($("#AGENT_ID").val()==""){
				$("#AGENT_ID").tips({
					side:3,
		            msg:'请输入代理商id',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AGENT_ID").focus();
			return false;
			}
			if($("#AGENT_PROFIT_BL").val()==""){
				$("#AGENT_PROFIT_BL").tips({
					side:3,
		            msg:'请输入分润比例',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AGENT_PROFIT_BL").focus();
			return false;
			}
			if($("#AGENT_PROFIT_MONEY").val()==""){
				$("#AGENT_PROFIT_MONEY").tips({
					side:3,
		            msg:'请输入分润金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AGENT_PROFIT_MONEY").focus();
			return false;
			}
			if($("#TRAN_CONTENT").val()==""){
				$("#TRAN_CONTENT").tips({
					side:3,
		            msg:'请输入交易内容',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TRAN_CONTENT").focus();
			return false;
			}
			if($("#STATUS").val()==""){
				$("#STATUS").tips({
					side:3,
		            msg:'请输入是否付款',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#STATUS").focus();
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