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
					
					<form action="agent/edityq.do" name="Form" id="Form" method="post">
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:150px;text-align: right;padding-top: 13px;" >平移前代理邀请码:</td>
								<td><input onblur="checkagent(this.value,1)" type="text"  name="BAGENT_CODE" id="BAGENT_CODE"  maxlength="256" placeholder="平移前代理邀请码" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:150px;text-align: right;padding-top: 13px;">平移前代理名称:</td>
								<td><input type="text"  name="BAGENT_NAME" id="BAGENT_NAME" readonly value="${pd.AGENT_NAME}" maxlength="256" placeholder="平移前代理名称"  style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:150px;text-align: right;padding-top: 13px;">平移后代理邀请码:</td>
								<td><input type="text" onblur="checkagent(this.value,2)"  name="AAGENT_CODE" id="AAGENT_CODE"  maxlength="64" placeholder="平移后代理邀请码" title="微信号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:150px;text-align: right;padding-top: 13px;">平移后代理名称:</td>
								<td><input type="text"  name="AAGENT_NAME" id="AAGENT_NAME" readonly value="${pd.P_ID}" maxlength="32" placeholder="平移后代理名称" title="输入上级ID" style="width:98%;"/></td>
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
			if($("#BAGENT_CODE").val()==""){
				$("#BAGENT_CODE").tips({
					side:3,
		            msg:'请输入平移前代理邀请码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#BAGENT_CODE").focus();
			return false;
			}
			if($("#AAGENT_CODE").val()==""){
				$("#AAGENT_CODE").tips({
					side:3,
		            msg:'请输入平移后代理邀请码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AAGENT_CODE").focus();
			return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
		//校验邀请码
		function checkagent(code,w){
			//var YqCode=$("#BAGENT_CODE").val();
			$.ajax({
				"url":"agent/findagentbyyq.do",
				"data":{"YqCode":code},
				"type":"GET",
				"dataType":"json",
				"async":"true",
				"success":function(data){
					var msg = data[0].msg
					console.log(msg);
					if(msg=='OK'){
						if(w==1){
							$("#BAGENT_NAME").val(data[0].AGENT_NAME);
						}else if(w==2){
							$("#AAGENT_NAME").val(data[0].AGENT_NAME);
						}
						
					}else{
						if(w==1){
							$("#BAGENT_CODE").tips({
								side:3,
					            msg:'邀请码不正确',
					            bg:'#AE81FF',
					            time:2
					        });
							$("#BAGENT_CODE").val("");
						}else if(w==2){
							$("#AAGENT_CODE").tips({
								side:3,
					            msg:'邀请码不正确',
					            bg:'#AE81FF',
					            time:2
					        });
							$("#AAGENT_CODE").val("");
						}
						
					}
				}
			});
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