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
					
					<form action="mallattribute/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<%-- <tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">商品分类:</td>
								<td>
									<select class="chosen-select form-control" name="MALL_CATE_ID" id="MALL_CATE_ID" data-placeholder="这里选择商品分类" style="vertical-align:top;width: 98%;">
										<c:forEach items="${list}" var="lis">
											<option value=${lis.ID} <c:if test="${lis.ID == pd.MALL_CATE_ID}">selected</c:if>>${lis.NAME}</option>
										</c:forEach>
									</select>
								</td>
							</tr> --%>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">属性名称:</td>
								<td>
									<c:if test="${pd.NAME=='price'}"><input type="text" readonly value="房卡价格"  style="width:98%;"/></td></c:if>
									<c:if test="${pd.NAME=='num'}"><input type="text" readonly value="房卡数量"   style="width:98%;"/></td></c:if>
									<c:if test="${pd.NAME=='send'}"><input type="text" readonly value="赠送数量"  style="width:98%;"/></td></c:if>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">属性值:</td>
								<td><input type="text" name="VALUE" id="VALUE" value="${pd.VALUE}" maxlength="512" placeholder="这里输入属性值" title="属性值" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">备注:</td>
								<td><textarea name="REMARK" id="REMARK" maxlength="2000" placeholder="这里输入备注" title="备注" style="width:98%;">${pd.REMARK}</textarea></td>
							</tr>
							<tr>
								<td style="text-align: center;" colspan="10">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
								</td>
							</tr>
							<input type="hidden" name="NAME" id="NAME" value="${pd.NAME}" maxlength="512" placeholder="这里输入属性名称" title="属性名称" style="width:98%;"/></td>
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
			if($("#MALL_CATE_ID").val()==""){
				$("#MALL_CATE_ID").tips({
					side:3,
		            msg:'请输入商品分类ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#MALL_CATE_ID").focus();
			return false;
			}
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入属性名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME").focus();
			return false;
			}
			if($("#VALUE").val()==""){
				$("#VALUE").tips({
					side:3,
		            msg:'请输入属性值',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#VALUE").focus();
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