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
					
					<form action="agentrecharge/${msg}.do" name="Form" id="Form" method="post">
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<input type="hidden" name="NUM" id="NUM" value="${pd.CHARGE_NUM}"/>
							<input type="hidden" name="AGENTRECHARGE_ID" id="AGENTRECHARGE_ID" value="${pd.AGENTRECHARGE_ID}"/>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">代理游戏ID:</td>
								<td><input type="text" name="USER_ID" id="USER_ID" readonly value="${pd.USER_ID}" maxlength="64" placeholder="这里输入代理商ID" title="代理商ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">代理名称:</td>
								<td><input type="text" name="NICK_NAME" id="NICK_NAME" readonly value="${pd.NICK_NAME}" maxlength="256" placeholder="这里输入代理商名称" title="代理商名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">补卡数:</td>
								<td><input type="text" name="CHARGE_NUM" id="CHARGE_NUM" maxlength="32" onblur="checks(this.value);" placeholder="这里输入充值房卡数" title="充值房卡数" style="width:98%;"/></td>
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
		
		
		function save(){
			if($("#CHARGE_NUM").val()==""){
				$("#CHARGE_NUM").tips({
					side:3,
		            msg:'请输入冲卡数量',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CARD_NUM").focus();
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
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		
		//下拉框
	 	if(!ace.vars['touch']) {
			$('.chosen-select').chosen({allow_single_deselect:true}); 
			$(window)
			.off('resize.chosen')
			.on('resize.chosen', function() {
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			}).trigger('resize.chosen');
			$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
				if(event_name != 'sidebar_collapsed') return;
				$('.chosen-select').each(function() {
					 var $this = $(this);
					 $this.next().css({'width': $this.parent().width()});
				});
			});
			$('#chosen-multiple-style .btn').on('click', function(e){
				var target = $(this).find('input[type=radio]');
				var which = parseInt(target.val());
				if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
				 else $('#form-field-select-4').removeClass('tag-input-style');
			});
		} 
		</script>
</body>
</html>