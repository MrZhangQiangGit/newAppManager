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
		.wh{
			width: 25px;
			height: 35px;
		}
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
					
					<form action="controlmjsign/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="CONTROLSIGN_ID" id="CONTROLSIGN_ID" value="${pd.CONTROLSIGN_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户游戏ID:</td>
								<td><input type="text" name="USERID" id="USERID" value="${pd.USERID}" maxlength="255" placeholder="这里输入用户游戏ID" title="用户游戏ID" style="width:98%;" onblur="checkId()"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">棋牌类型：</td>
								<td><input type="text" name="TYPE" id="TYPE" value="麻将" maxlength="255" placeholder="斗地主" readonly="readonly" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">牌内容：</td>
								<td id="sel">
									<input type="hidden" name="CONTENT" id="CONTENT" value="${pd.CONTENT}" maxlength="255" placeholder="这里输入牌内容，形式如下：1，2，3" title="牌内容，形式如下：1，2，3" style="width:98%;"/>
								</td>
								
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"></td>
								<td class="pl" id="1" >
								
								</td>
								<td class="pl" id="2" style="display: none ">
									<input type="button" onclick="shang(this)">
									<input type="button" onclick="xia(this)">
								</td>
								<td class="pl" id="3" style="display: none">
									<input type="button" onclick="shang(this)">
									<input type="button" onclick="xia(this)">
								</td>
								<td class="pl" id="4" style="display: none">
									<input type="button" onclick="shang(this)">
									<input type="button" onclick="xia(this)">
								</td>
								<td class="pl" id="5" style="display: none">
									<input type="button" onclick="shang(this)">
									<input type="button" onclick="xia(this)">
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
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		//保存
		function save(){
			if($("#USERID").val()==""){
				$("#USERID").tips({
					side:3,
		            msg:'请输入用户游戏ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USERID").focus();
			return false;
			}
			if($("#TYPE").val()==""){
				$("#TYPE").tips({
					side:3,
		            msg:'请输入棋牌类型：1 麻将 ，2斗地主',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#TYPE").focus();
			return false;
			}
			var ID=$("#USERID").val();
			$.ajax({
				"url":"controlmjsign/checkId.do",
				"data":{"USER_ID":ID},
				"type":"GET",
				"dataType":"json",
				"async":"true",
				"success":function(data){
					for(var i=0;i<data.length;i++){
						if(data[i].check=="f"){
							$("#USERID").tips({
								side:3,
					            msg:'用户游戏ID不存在',
					            bg:'#AE81FF',
					            time:2
					        });
							$("#USERID").focus();
						}else{
							$("#Form").submit();
							$("#zhongxin").hide();
							$("#zhongxin2").show();
							
						}
					}
				},
			});
			
			
		}
		
		//放牌
		$(function(){
			for(var j=0;j<3;j++){
				for(var i=1;i<10;i++){
					$('<img class="wh" src="uploadFiles/uploadImgs/mj/'+(i+j*36)+'.png">').appendTo(".pl");
					$('<img class="wh" src="uploadFiles/uploadImgs/mj/'+(i+9+j*36)+'.png">').appendTo(".pl");
					$('<img class="wh" src="uploadFiles/uploadImgs/mj/'+(i+18+j*36)+'.png">').appendTo(".pl");
					$('<img class="wh" src="uploadFiles/uploadImgs/mj/'+(i+27+j*36)+'.png">').appendTo(".pl");
				}
			}
			for(var k=0;k<4;k++){
				$('<img class="wh" src="uploadFiles/uploadImgs/mj/'+(k+109)+'.png">').appendTo(".pl");
			}
		});
		
		function shang(obj){
			var id= obj.parentElement.id;
			var up=parseInt(id)-1;
			if(document.getElementById(up)==null){
				//第一个不切换
			}else{
				document.getElementById(id).style.display= "none ";
				document.getElementById(up).style.display= "inline "; 
			}
			
		}
		function xia(obj){
			var id= obj.parentElement.id;
			var next=parseInt(id)+1;
			if(document.getElementById(next)==null){
				//最后一个不切换
			}else{
				document.getElementById(id).style.display= "none ";
				document.getElementById(next).style.display= "inline "; 
				//document.getElementById(next).setAttribute('padding',"8px");
				//document.getElementById(next).setAttribute('line-height',"1.42857143");
				//document.getElementById(next).setAttribute('vertical-align',"top");
			}
		}
		
		$(function(){//获取点击的牌
			$(".wh").click(function(){
				var img =  this.src;
				var pai="";
				var strFileName=parseInt(img.substring(img.lastIndexOf("/")+1,img.lastIndexOf(".")));//文件名
				
				var strs= new Array(); //定义一数组 
				var str = $("#CONTENT").val();
				strs=str.split(","); //字符分割 
				var sld=strs.indexOf(img.substring(img.lastIndexOf("/")+1,img.lastIndexOf(".")));//判断是否已经选择
				var newstr="";
				if(sld>-1){//已选择
					this.src="uploadFiles/uploadImgs/mj/"+strFileName+".png";//再次点击还原
					var name =new Array();//存放图片名
					var selectp= new Array();
					selectp=$("#sel").children("img");//获取牌内容中的牌
					for(var i=0;i<selectp.length;i++){
						//牌内容中牌的名称
						var asrc= selectp[i].src
						var selp=parseInt(asrc.substring(asrc.lastIndexOf("/")+1,asrc.lastIndexOf(".")));
						if(selp!=strFileName){
							name.push(selp);
						}
					}
					$("#sel .wh").remove();
					for(var i=0;i<name.length;i++){//将新的内容添加到牌内容
						var nname=parseInt(name[i]);
						$('<img class="wh" src="uploadFiles/uploadImgs/mj/'+nname+'.png">').appendTo("#sel");
					}
					for(var i=0;i<strs.length;i++){
						if(strFileName !=strs[i]){
							if(newstr==""){
								newstr = strs[i];
							}else{
								newstr =newstr+ ","+ strs[i];
							}
						}
					}
					$("#CONTENT").val("");
					$("#CONTENT").val(newstr);
				}else{//未选择
					if(strs.length>12){//最多输入17张
						$("#sel").tips({
							side:3,
				            msg:'最多选择13张',
				            bg:'#AE81FF',
				            time:2
				        });
						$("#CNT").focus();
					}else{
						this.src="uploadFiles/uploadImgs/mj_selected/"+strFileName+".png";//选中更改
						console.log(strFileName);
						$('<img class="wh" src="uploadFiles/uploadImgs/mj/'+strFileName+'.png">').appendTo("#sel"); 
						if($("#CONTENT").val()==""){
							$("#CONTENT").val(strFileName);
							$("#CNT").val(pai);
						}else{
							$("#CONTENT").val($("#CONTENT").val()+","+strFileName);
							$("#CNT").val($("#CNT").val()+","+pai);
							$("#CNT").attr("title",$("#CNT").val());
						}
					//console.log($("#CONTENT").val());
					}
				}
				});
		});
		
		function checkId(){//校验ID
			var ID=$("#USERID").val();
			if(ID==""){
				$("#USERID").tips({
					side:3,
		            msg:'请输入ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#USERID").focus();
			}else{
				$.ajax({
					"url":"controlmjsign/checkId.do",
					"data":{"USER_ID":ID},
					"type":"GET",
					"dataType":"json",
					"async":"true",
					"success":function(data){
						for(var i=0;i<data.length;i++){
							if(data[i].check=="f"){
								$("#USERID").tips({
									side:3,
						            msg:'用户游戏ID不存在',
						            bg:'#AE81FF',
						            time:2
						        });
								$("#USERID").focus();
							}
						}
					},
				});
			}
			
		}
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>