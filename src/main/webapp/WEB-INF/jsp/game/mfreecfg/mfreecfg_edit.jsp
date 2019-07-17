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
	<link rel="stylesheet" href="static/ace/css/bootstrap.min.css"  media="screen"/>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/bootstrap-datetimepicker.min.css"  media="screen"/>
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
					
					<form action="mfreecfg/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="MFREECFG_ID" id="MFREECFG_ID" value="${pd.MFREECFG_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开始时间:</td>
								<td>
									<div class="input-group date form_datetime col-md-5" data-date-format="yyyy-mm-dd hh:ii" data-link-field="dtp_input1">
							            <input class="form-control" size="16" type="text" readonly name="START_TIME" id="START_TIME" value="${pd.START_TIME}" maxlength="64" placeholder="这里选择开始时间" title="开始时间" style="width:98%;height:30px;">
							            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
							        </div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">结束时间:</td>
								<td>
									<div class="input-group date form_datetime col-md-5" data-date-format="yyyy-mm-dd hh:ii" data-link-field="dtp_input1">
							            <input class="form-control" size="16" type="text" readonly name="END_TIME" id="END_TIME" value="${pd.END_TIME}" maxlength="64" placeholder="这里选择结束时间" title="结束时间" style="width:98%;height:30px;">
							            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
							        </div>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td>
									<select name="STATUS" id="STATUS" data-placeholder="这里选择状态" style="vertical-align:top;width:98%;height:30px;">
											<option value="0" <c:if test="${'0' == pd.STATUS }">selected</c:if>>无效</option>
											<option value="1" <c:if test="${'1' == pd.STATUS }">selected</c:if>>有效</option>
									</select>
								</td>
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
	<script src="static/ace/js/date-time/jquery-1.8.3.min.js"  charset="UTF-8"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/bootstrap/bootstrap-datetimepicker.js"  charset="UTF-8"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datetimepicker.zh-CN.js"  charset="UTF-8"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#START_TIME").val()==""){
				$("#START_TIME").tips({
					side:3,
		            msg:'请输入开始时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#START_TIME").focus();
			return false;
			}
			if($("#END_TIME").val()==""){
				$("#END_TIME").tips({
					side:3,
		            msg:'请输入结束时间',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#END_TIME").focus();
			return false;
			}
			if($("#STATUS").val()==""){
				$("#STATUS").tips({
					side:3,
		            msg:'请输入状态',
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
			$(".form_datetime").datetimepicker({
					language:  'zh-CN',
				   	weekStart: 1,
			        todayBtn:  1,
					autoclose: 1,
					todayHighlight: 1,
					startView: 2,
					forceParse: 0,
			    showMeridian: 1
			   	});
		});
		</script>
</body>
</html>