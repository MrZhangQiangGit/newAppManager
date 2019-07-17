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
		function check(value){
			$.ajax({
				"url":"agent/findObjectByCardNo.do",
				"data":{"id":value},
				"type":"GET",
				"dataType":"json",
				"async":"true",
				"success":function(data){
					$("#AGENT_NAME").val(data[0].NICK_NAME);
					$("#WX_CODE").val(data[0].OPENID);
				},
				"error":function(data){
					$("#AGENT_NAME").val("");
					$("#WX_CODE").val("");
					$("#AGENT_CODE").tips({
						side:3,
			            msg:'ID不正确,请重新输入',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#AGENT_CODE").focus();
					return false;
				}
			});
			
			
			var arr = ${jsonagent};
			for(var i=0;i<arr.length;i++){
				arr[i] = arr[i].AGENT_CODE;
				if(arr[i]==value){
					$("#AGENT_CODE").tips({
						side:3,
			            msg:'代理游戏ID已存在,请重新输入!',
			            color:'#FF0000',
			            bg:'#CCCCCC',
			            time:2
			        });
				return false;
				}
			}
		}
		function clicks(){
			var arr = ${jsonagent};
			for(var i=0;i<arr.length;i++){
				arr[i] = arr[i].AGENT_CODE;
				if(arr[i]==$("#AGENT_CODE").val()){
					$("#AGENT_CODE").val("");
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
					
					<form action="agent/${msg}.do" name="Form" id="Form" method="post">
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">代理游戏ID:</td>
								<td><input type="text" name="AGENT_CODE" id="AGENT_CODE" onblur="check(this.value);" onclick="clicks();" value="${pd.AGENT_CODE}" maxlength="64" placeholder="这里输入代理商ID" title="代理商ID" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">代理名称:</td>
								<td><input type="text" name="AGENT_NAME" id="AGENT_NAME" value="${pd.AGENT_NAME}" maxlength="256" placeholder="这里输入代理商名称" title="代理商名称" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">微信号:</td>
								<td><input type="text" name="WX_CODE" id="WX_CODE" value="${pd.WX_CODE}" readonly maxlength="64" placeholder="这里输入微信号" title="微信号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">上级代理:</td>
								<td><input type="text" readonly name="P_ID" id="P_ID" value="${pd.P_ID}" maxlength="32" placeholder="这里输入上级ID" title="输入上级ID" style="width:98%;"/></td>
							</tr>
							
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">上级分润比例(%):</td>
								<c:if test="${level== 0}">
									<td><input type="text" readonly name="UP_PROFIT" id="UP_PROFIT" value="0" maxlength="32" placeholder="这里输入上级分润比例" title="输入上级分润比例" style="width:98%;" readonly="readonly"/></td>
								</c:if>
								<c:if test="${level != 0}">
									<td><input type="text" readonly name="UP_PROFIT" id="UP_PROFIT" value="10" maxlength="32" placeholder="这里输入上级分润比例" title="输入上级分润比例" style="width:98%;"/></td>
								</c:if>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">分润比例(%):</td>
								<td>
									<c:if test="${level == 0}"><input type="text"  name="PROFIT" id="PROFIT" value="50" maxlength="32" placeholder="这里输入上级分润比例" title="输入上级分润比例" style="width:98%;" readonly="readonly"/></c:if>
									<c:if test="${level == 1}"><input type="text"  name="PROFIT" id="PROFIT" value="40" maxlength="32" placeholder="这里输入上级分润比例" title="输入上级分润比例" style="width:98%;" readonly="readonly"/></c:if>
									<c:if test="${level == 2}"><input type="text"  name="PROFIT" id="PROFIT" value="30" maxlength="32" placeholder="这里输入上级分润比例" title="输入上级分润比例" style="width:98%;" readonly="readonly"/></c:if>
								<%-- <select class="chosen-select form-control"   name="PROFIT" id="PROFIT" data-placeholder="这里输入分润比例(%)" style="vertical-align:top;width: 120px;">
									<c:forEach var="s"  begin="1" end="99">
										<option value=${s}>${s} </option>
									</c:forEach>
								</select> --%>
								</td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">性别:</td>
								<td>
									<select name="SEX" id="SEX" data-placeholder="这里选择性别" style="vertical-align:top;width:98%;height:30px;">
											<option value="1">男</option>
											<option value="2">女</option>
											<option value="3">未知</option>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">手机号:</td>
								<td><input type="text" name="PHONE_NO" id="PHONE_NO" value="${pd.PHONE_NO}" maxlength="32" placeholder="这里输入手机号" title="手机号" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:90px;text-align: right;padding-top: 13px;">推荐人ID:</td>
								<td><input type="text" name="RECOMMAND_ID" id="RECOMMAND_ID" value="${pd.RECOMMAND_ID}" maxlength="64" placeholder="这里输入推荐人ID" title="推荐人ID" style="width:98%;"/></td>
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
			var arr = ${jsonagent};
			for(var i=0;i<arr.length;i++){
				var code = arr[i].AGENT_CODE;
				if(code==$("#AGENT_CODE").val()){
					$("#AGENT_CODE").tips({
						side:3,
			            msg:'代理游戏ID已存在,请重新输入!',
			            color:'#FF0000',
			            bg:'#CCCCCC',
			            time:2,
			            y:0
			        });
				return false;
				}
			}
			if($("#AGENT_CODE").val()==""){
				$("#AGENT_CODE").tips({
					side:3,
		            msg:'请输入代理游戏ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AGENT_CODE").focus();
			return false;
			}
			if($("#AGENT_NAME").val()==""){
				$("#AGENT_NAME").tips({
					side:3,
		            msg:'请输入代理名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AGENT_NAME").focus();
			return false;
			}
			if($("#PROFIT").val()==""){
				$("#PROFIT").tips({
					side:3,
		            msg:'请选择分润比例',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#PROFIT").focus();
			return false;
			}
			var fenrun = /^([1-9]|[1-9][0-9])$/;
			if($("#PROFIT").val() != ""){
				if(!fenrun.test($("#PROFIT").val())){
					$("#PROFIT").tips({
						side:3,
			            msg:'“分润比例”格式不正确!',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#PROFIT").focus();
				return false;
				}
			}
			var myreg = /^(((13[0-9]{1})|15[0-9]{1}|17[0-9]{1}|18[0-9]{1})+\d{8})$/;
			if($("#PHONE_NO").val() != ""){
				if(!myreg.test($("#PHONE_NO").val())){
					$("#PHONE_NO").tips({
						side:3,
			            msg:'手机号格式不正确!',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#PHONE_NO").focus();
				return false;
				}
			}
			if($("#P_ID").val()==""){
				$("#P_ID").tips({
					side:3,
		            msg:'请选择上级ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#P_ID").focus();
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