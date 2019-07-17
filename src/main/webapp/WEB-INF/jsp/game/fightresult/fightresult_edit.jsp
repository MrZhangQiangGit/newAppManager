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
					
					<form action="fightresult/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="FIGHTRESULT_ID" id="FIGHTRESULT_ID" value="${pd.FIGHTRESULT_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">房间号:</td>
								<td><input type="text" name="ROOM_ID" id="ROOM_ID" value="${pd.ROOM_ID}" maxlength="255" placeholder="这里输入房间号" title="房间号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户ID:</td>
								<td><input type="text"  name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="12"  title="ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态:</td>
								<td style="vertical-align:top;padding-left:2px;">
								<select class="chosen-select form-control"   name="STATUS" id="id" data-placeholder="请选择状态" style="vertical-align:top;width: 120px;">
								<option value="1">请选择状态</option>
								<option value="1">正在进行</option>
								<option value="0">已完成</option>
								</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">昵称:</td>
								<td><input type="text" name="NICK_NAME" id="NICK_NAME" value="${pd.NICK_NAME}" maxlength="12" placeholder="这里输入昵称" title="昵称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">得分:</td>
								<td><input type="text" name="SCORE" id="SCORE" value="${pd.SCORE}" maxlength="12" placeholder="这里输入得分" title="得分" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">开始时间:</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="START_TIME" id="START_TIME"  value="" type="text" data-date-format="yyyy-mm-dd"  style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
<%-- 								<td><input type="text" name="START_TIME" id="START_TIME" value="${pd.START_TIME}" maxlength="12" placeholder="这里输入开始时间" title="开始时间" style="width:98%;"/>时间格式：年-月-日</td> --%>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">结束时间:</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="END_TIME" id="END_TIME"  value="" type="text" data-date-format="yyyy-mm-dd"  style="width:88px;" placeholder="结束时间" title="结束时间"/></td>
<%-- 								<td><input type="text" name="END_TIME" id="END_TIME" value="${pd.END_TIME}" maxlength="12" placeholder="这里输入结束时间" title="结束时间" style="width:98%;"/>时间格式：年-月-日</td> --%>
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
			if($("#ROOM_ID").val()==""){
				$("#ROOM_ID").tips({
					side:3,
		            msg:'请输入房间号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ROOM_ID").focus();
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
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>