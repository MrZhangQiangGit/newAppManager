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
	<script type="text/javascript">
		function check(){
			var c = document.getElementById("NICK_NAME");
			c.innerHTML="";
			var list = ${list};
			for(var i=0;i<list.length;i++){
				var id = list[i].AGENT_CODE;
				var name = list[i].AGENT_NAME;
				if(id == $("#USER_ID").val()){
			          $("#NICK_NAME").val(name);
				}
			}
		}
	</script>
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
					
					<form action="agentrecharge/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="AGENTRECHARGE_ID" id="AGENTRECHARGE_ID" value="${pd.AGENTRECHARGE_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户游戏ID:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" value="${pd.AGENT_CODE}" maxlength="64"  onblur="check();" placeholder="这里输入用户游戏ID" title="用户游戏ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">用户昵称:</td>
								<td><input type="text" name="NICK_NAME" id="NICK_NAME" value="${pd.AGENT_NAME}"  maxlength="64" placeholder="这里输入昵称" title="昵称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">冲卡类型:</td>
								<td>
									<select name="CHARGE_TYPE" id="CHARGE_TYPE" style="width:98%;">
										<option value="1">购买</option>
										<option value="2">赠送</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">冲卡数量:</td>
								<td><input type="text" name="CHARGE_NUM" id="CHARGE_NUM" value="${pd.CHARGE_NUM}" onblur="checks(this.value);" maxlength="64" placeholder="这里输入冲卡数量" title="冲卡数量" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">金额:</td>
								<td><input type="text" name="MONEY" id="MONEY" value="${pd.MONEY}" maxlength="32" placeholder="这里输入金额" title="金额" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="256" placeholder="这里输入备注" title="备注" style="width:98%;"/></td>
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
		
		function checks(value){
			var id = ${num};
			if(id!=888888){//不是admin
				$.ajax({
					"url":"recharge/findObjectByid.do",
					"data":{"id":id},
					"type":"GET",
					"dataType":"json",
					"async":"true",
					"success":function(data){
						console.log(data[0].CARD_NUM);
						if(data[0].CARD_NUM<parseInt(value)){
							$("#CHARGE_NUM").tips({
								side:3,
					            msg:'房卡不足',
					            bg:'#AE81FF',
					            time:2
					        });
						$("#CHARGE_NUM").val("");
						}
					},
					"error":function(data){
						$("#CHARGE_NUM").tips({
							side:3,
				            msg:'你不是代理商',
				            bg:'#AE81FF',
				            time:2
				        });
					}
				});
			}
		}
		
		//保存
		function save(){
			var list = ${list};
			for(var i=0;i<list.length;i++){
				list[i] = list[i].AGENT_CODE;
			}
			if(list.indexOf($("#USER_ID").val())<=-1){
				$("#USER_ID").tips({
					side:3,
		            msg:'此游戏ID不是代理商',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_ID").focus();
			return false;
			}
			if($("#USER_ID").val()==""){
				$("#USER_ID").tips({
					side:3,
		            msg:'请输入用户游戏ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USER_ID").focus();
			return false;
			}
			if($("#NICK_NAME").val()==""){
				$("#NICK_NAME").tips({
					side:3,
		            msg:'请输入用户昵称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NICK_NAME").focus();
			return false;
			}
			if($("#CHARGE_NUM").val()==""){
				$("#CHARGE_NUM").tips({
					side:3,
		            msg:'请输入冲卡数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CHARGE_NUM").focus();
			return false;
			}
			if(!(/(^[1-9]\d{0,8}$)/.test($("#CHARGE_NUM").val()))){
				$("#CHARGE_NUM").tips({
					side:3,
		            msg:'请正确输入冲卡数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CHARGE_NUM").focus();
			return false;
			}
			var money = /^([1-9][\d]{0,9}|0)(\.[\d]{1,2})?$/;
			if($("#MONEY").val()!=""){
				if(!money.test($("#MONEY").val())){
					$("#MONEY").tips({
						side:3,
			            msg:'请正确输入金额',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#MONEY").focus();
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