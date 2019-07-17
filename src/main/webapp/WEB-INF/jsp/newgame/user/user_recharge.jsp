<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html class="no-js fixed-layout">
<base href="<%=basePath%>">
<head>
	<!-- jsp文件头和头部 -->
	<%@ include file="../../newsystem/index/top.jsp"%>
</head>
<body>
<!-- header start 头部开始 -->
 <%@ include file="../../newsystem/index/head.jsp"%> 

<!-- header end 头部结束 -->

<div class="admin-content">
    <div class="admin-content-body">
		<!-- hbody start -->
		<form action="userApp/${pd.msg }.do" method="post" id="form" class="am-form am-form-horizontal">
			<div class="am-g">
				<div class="am-u-sm-12 am-u-sm-centered am-u-md-6 am-u-lg-5 am-padding-xs">
					<table class="am-table am-bordered">
						<tr>
							<td>游戏ID</td>
							<td><input value="${pd.USER_ID }" onblur="checkId();" type="text" id="USER_ID" name="USER_ID" <c:if test="${pd.USER_ID!='' && pd.USER_ID!=null }">readonly="readonly"</c:if> placeholder="输入玩家Id" /></td>
						</tr>
						<tr>
							<td>玩家昵称</td>
							<td><input readonly="readonly" id="NICK_NAME" type="text" name="NICK_NAME" value="${pd.NICK_NAME }" placeholder="玩家昵称"/></td>
						</tr>
						<tr>
							<td>充值类型</td>
							<td>
								<select id="CHARGE_TYPE" name="CHARGE_TYPE">
									<option <c:if test="${pd.CHARGE_TYPE==1 }">selected="selected"</c:if> value="1">购买</option>
									<option <c:if test="${pd.CHARGE_TYPE==2 }">selected="selected"</c:if> value="2">赠送</option>
								</select>
							
							</td>
						</tr>
						<tr>
							<td>数量</td>
							<td><input  type="number" id="CHARGE_NUM" name="CHARGE_NUM" value="${pd.CHARGE_NUM }" placeholder="请输入充值数量"/></td>
						</tr>
						<tr>
							<td>金额</td>
							<td>
								<input type="number" id="MONEY" name="MONEY" value="${pd.MONEY }" placeholder="输入金额,选填"/>
							</td>
						</tr>
						<tr>
							<td>备注</td>
							<td><input type="text" id="REMARK" name="REMARK" value="${pd.REMARK }" placeholder="备注"/></td>
						</tr>
						<tr>
							<td colspan="2">
								<div class="am-g am-cf">
									<div class="am-center am-padding-horizontal">
										<input style="width: 40%;" type="button" class="am-btn am-btn-primary am-radius am-fl pwdbtn" value="提  交" onclick="recharge();">
										<input style="width: 40%;" type="button" class="am-btn am-btn-warning am-radius am-fr pwdbtn" value="取  消" onclick="cancel();">
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</form>
		<!-- hbody end -->
		<!-- <div class="" style="height: 100%;"> -->
			<!-- <iframe name="mainFrame" id="mainFrame" frameborder="0" src="tab.do" style="margin:0 auto;width:100%;height:100%;"></iframe> -->
		<!-- </div> -->
    </div>
	
    <footer class="admin-content-footer">
      <hr>
      <p class="am-padding-left">Copyright ©  2018</p>
    </footer>
  </div>

<!-- 底部 Navbar -->
<%-- <%@ include file="foot.jsp"%>  --%>
  

</body>
<script src="amazeUI/js/jquery.min.js"></script>
<!--<![endif]-->
<script src="amazeUI/js/amazeui.min.js"></script>
<script src="amazeUI/js/app.js"></script>
<script type="text/javascript">
	//验证登录人房卡是否足够
	function recharge(){
		
		var chargeNum = $("#CHARGE_NUM").val();
		if(chargeNum=="" || +chargeNum<=0){
			$("#CHARGE_NUM").popover({
				content:"填写充值数量错误",
				theme:"danger sm"
			}).popover("open");
			return false;
		}
		var flog = checkId();
		console.log(flog);
		if(flog==false){
			return false;
		}
		$.ajax({
			url:"userRechargeApp/userRecharge.do",
			type:"post",
			data:$("#form").serialize(),
			dataType:"json",
			success:function(data){
				var msg = data[0].msg;
				if(msg=="ok"){
					alert("充值成功");
					$("#CHARGE_NUM").val("");
					$("#MONEY").val("");
					$("#REMARK").val("");
				}else{
					$("#CHARGE_NUM").popover({
						content:"房卡不足",
						theme:"danger sm"
					}).popover("open");
				}
			}
		});
	}
	//检查玩家是否存在
	function checkId(){
		var flog = true;
		var userId = $("#USER_ID").val();
		$.ajax({
			url:"userRechargeApp/checkUserId.do",
			data:{"USER_ID":userId},
			type:"get",
			dataType:"json",
			async:false,
			success:function(data){
				if(data[0].msg=="ok"){
					$("#NICK_NAME").val(data[0].NICK_NAME);
				}else{
					$("#USER_ID").popover({
						content:"玩家不存在",
						theme:"danger sm"
					}).popover("open");
					flog = false;
				}
			}
		});
		return flog;
	}
	
	//取消修改并返回
	function cancel(){
		//var uid = ${pd.USER_ID};
		var b = $("#USER_ID").attr("readonly");
		//alert(b);
		if(b){
			window.location.href="userApp/lowerUserList.do";
		}else{
			window.location.href="mainApp/index";
		}
	}
</script>
</html>
