<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<link rel="stylesheet" href="static/ace/css/chosen.css" />
<!-- jsp文件头和头部 -->
<%@ include file="../../system/index/top.jsp"%>
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
									<div id="zhongxin" style="padding-top: 13px;">
									<table id="table_report" class="table table-striped table-bordered table-hover">
										<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">房间规则ID:</td>
											<td  class='center'>${var.ROOMRULE_ID}</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">房间ID:</td>
											<td  class='center'>${var.ROOM_ID }</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">房间规则:</td>
											<td  class='center'>${var.RULE }</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">局数:</td>
											<td  class='center'>${var.NUM }</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">封顶:</td>
											<td  class='center'>${var.TOP }</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">自摸算法:</td>
											<td  class='center'>${var.ZMSF }</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">点杠算法:</td>
											<td  class='center'>${var.DGSF }</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">换三张:</td>
											<td  class='center'>${var.HSZ }</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">幺九将对:</td>
											<td  class='center'>${var.YJJD }</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">门清中张:</td>
											<td  class='center'>${var.MQZZ }</td>
										</tr>
										<tr>
											<td style="width:79px;text-align: right;padding-top: 13px;">天地胡:</td>
											<td class='center'>${var.TDH }</td>
										</tr>
										</c:forEach>
									</table>
									</div>
									<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
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
	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- inline scripts related to this page -->
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
</body>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	//保存
	function save(){
		if($("#role_id").val()==""){
			$("#juese").tips({
				side:3,
	            msg:'选择角色',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#role_id").focus();
			return false;
		}
		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			$("#loginname").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}
		
		
	$(function() {
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
	});
}
</script>
</html>