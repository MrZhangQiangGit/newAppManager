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
					
					<form action="withdrawal/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="WITHDRAWAL_ID" id="WITHDRAWAL_ID" value="${pd.WITHDRAWAL_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">代理ID:</td>
								<td><input type="text" name="AGENT_CODE" id="AGENT_CODE" readonly="readonly" value="${pd.AGENT_CODE}" maxlength="255" placeholder="这里输入代理ID" title="代理ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">代理名:</td>
								<td><input type="text" name="AGENT_NAME" id="AGENT_NAME" readonly="readonly" value="${pd.AGENT_NAME}" maxlength="255" placeholder="这里输入代理名" title="代理名" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">提现金额:</td>
								<td><input type="number" name="MONEY" id="MONEY" value="${pd.MONEY}" maxlength="255" placeholder="这里输入提现金额" title="提现金额" style="width:98%;"/></td>
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
			if($("#AGENT_CODE").val()==""){
				$("#AGENT_CODE").tips({
					side:3,
		            msg:'请输入代理ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AGENT_CODE").focus();
			return false;
			}
			if($("#AGENT_NAME").val()==""){
				$("#AGENT_NAME").tips({
					side:3,
		            msg:'请输入代理名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AGENT_NAME").focus();
			return false;
			}
			if($("#MONEY").val()==""){
				$("#MONEY").tips({
					side:3,
		            msg:'请输入提现金额',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MONEY").focus();
			return false;
			}
			var id = $("#AGENT_CODE").val();
			$.ajax({
				"url":"agent/checkagent.do",
				"data":{"AGENT_CODE":id},
				"type":"GET",
				"dataType":"json",
				"async":"true",
				"success":function(data){
					var MONEY= parseFloat($("#MONEY").val());
					var smoney=parseFloat(data[0].SURPLUS_MONEY) ;
					console.log(smoney+"  --<--  "+MONEY+"  -----------  "+ (smoney < MONEY));
					if(smoney < MONEY){
						$("#MONEY").tips({
							side:3,
				            msg:'余额不足',
				            bg:'#AE81FF',
				            time:2
				        });
						$("#MONEY").val("");
					}else{
						$("#Form").submit();
						$("#zhongxin").hide();
						$("#zhongxin2").show();
					}
				}
			});
			
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
		function checkagent(){
			var id = $("#AGENT_CODE").val();
				$.ajax({
					"url":"agent/checkagent.do",
					"data":{"AGENT_CODE":id},
					"type":"GET",
					"dataType":"json",
					"async":"true",
					"success":function(data){
						var MONEY= $("#MONEY").val();
						console.log(data[0].SURPLUS_MONEY );
						if(data[0].SURPLUS_MONEY < MONEY){
							$("#MONEY").tips({
								side:3,
					            msg:'余额不足',
					            bg:'#AE81FF',
					            time:2
					        });
						$("#MONEY").val("");
						}
					}
				});
		}
		</script>
</body>
</html>