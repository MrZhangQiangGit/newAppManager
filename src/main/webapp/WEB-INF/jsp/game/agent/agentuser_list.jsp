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
	<style type="text/css">
		#part1{width:48%; float:left}
		#part2{width:48%; float:left;margin-left:2%}
	</style>
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
					
					<%-- <form action="agent/${msg}.do" name="Form" id="Form" method="post"> --%>
						<div id="zhongxin" style="padding-top: 13px;">
							<div id="part1">	
							
								<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
									<thead>
										<tr>
											<th class="center">代理游戏ID</th>
											<th class="center">代理名称</th>
										</tr>
									</thead>
									<tbody id="agent">
										
									</tbody>
								</table>
								<a class="btn btn-mini btn-success" onclick="uppage(1)">上一页</a>
								<a class="btn btn-mini btn-success" onclick="nextpage(1)">下一页</a>
							</div>
							<div id="part2">
								<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
									<thead>
										<tr>
											<th class="center">玩家游戏ID</th>
											<th class="center">玩家名称</th>
										</tr>
									</thead>
									<tbody id="user">
									</tbody>
								</table>
								<a class="btn btn-mini btn-success" onclick="uppage(2)">上一页</a>
								<a class="btn btn-mini btn-success" onclick="nextpage(2)">下一页</a>
							</div>
							
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					<!-- </form> -->
					<input type="hidden" id="apage" value="0">
					<input type="hidden" id="upage" value="0">
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
		var AGENT_CODE=${pd.id}
		var YQ_CODE=${pd.YQ_CODE}
		$(top.hangge());
		
		$(function(){
			agentlist();
			userlist();
		});
		function agentlist(){
			var apage = $("#apage").val();
			$.ajax({
				"url":"agent/getownagent.do",
				"data":{"CREATOR":AGENT_CODE,"pages":apage},
				"type":"GET",
				"dataType":"json",
				"async":"true",
				"success":function(data){
					$("#agent").html("");
					for(var i=0;i<data.length;i++){
						var str = '<tr>'+
										'<td class="center">'+data[i].AGENT_CODE+' </td>'+
										'<td class="center">'+data[i].AGENT_NAME+'</td>'+
									'</tr>'
						$("#agent").append(str);
					}
				}
			});
		}
		
		function userlist(){
			var upage = $("#upage").val();
			$.ajax({
				"url":"agent/getownuser.do",
				"data":{"YQ_CODE":YQ_CODE,"pages":upage},
				"type":"GET",
				"dataType":"json",
				"async":"true",
				"success":function(data){
					$("#user").html("");
					for(var i=0;i<data.length;i++){
						var str = '<tr>'+
									'<td class="center">'+data[i].USER_ID+'</td>'+
									'<td class="center">'+data[i].NICK_NAME+'</td>'+
									'</tr>';
						$("#user").append(str);
					}
				}
			});
		}
		
		//上一页
		function uppage(n){
			if(n==1){//代理
				var apage = Number($("#apage").val());
				if(apage >0){
					$("#apage").val((apage-1))
				}
				agentlist();
			}else if(n==2){//玩家
				var upage = Number($("#upage").val());
				if(upage >0){
					$("#upage").val((upage-1))
				}
				userlist();
			}
		}
		
		//下一页
		function nextpage(n){
			if(n==1){//代理
				var apage = Number($("#apage").val());
				$("#apage").val((apage+1))
				agentlist();
			}else if(n==2){//玩家
				var upage = Number($("#upage").val());
				$("#upage").val((upage+1))
				userlist();
			}
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