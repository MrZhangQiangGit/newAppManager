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
					
					<form action="controldzsign/${msg}.do" name="Form" id="Form" method="post">
						<input type="hidden" name="MCONTROLSIGN_ID" id="MCONTROLSIGN_ID" value="${pd.MCONTROLSIGN_ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">用户游戏ID:</td>
								<td><input type="text" name="USERID" id="USERID" value="${pd.USERID}" maxlength="255" placeholder="这里输入用户游戏ID" title="用户游戏ID" style="width:98%;" onblur="checkId()"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">棋牌类型：</td>
								<td><input type="text" name="TYPE" id="TYPE" value="斗地主" maxlength="255" placeholder="斗地主" readonly="readonly" style="width:98%;"/></td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">状态：</td>
								<td>
									<select id="STATUS" name="STATUS">
										<c:if test="${pd.STATUS==1 }">
										<option value="1" selected="selected">有效</option>
										<option value="0">无效</option>
										</c:if>
										<c:if test="${pd.STATUS==0 }">
											<option value="1" >有效</option>
											<option value="0" selected="selected">无效</option>
										</c:if>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;">牌内容：</td>
								<td id="sel">
									<input type="hidden" name="CONTENT" id="CONTENT" value="${pd.CONTENT}" maxlength="255" placeholder="这里输入牌内容，形式如下：1，2，3" title="牌内容，形式如下：1，2，3" style="width:98%;"/>
									<input type="hidden" value="${pd.CONTENTS }">
								</td>
							</tr>
							<tr>
								<td style="width:75px;text-align: right;padding-top: 13px;"></td>
								<td class="pl" id="1" >
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/54.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/53.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/2.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/15.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/28.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/41.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/1.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/14.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/27.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/40.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/13.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/26.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/39.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/52.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/12.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/25.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/38.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/51.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/11.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/24.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/37.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/50.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/10.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/23.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/36.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/49.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/9.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/22.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/35.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/48.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/8.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/21.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/34.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/47.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/7.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/20.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/33.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/46.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/6.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/19.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/32.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/45.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/5.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/18.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/31.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/44.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/4.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/17.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/30.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/43.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/3.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/16.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/29.png">
									<img class="wh" alt="" src="uploadFiles/uploadImgs/puke/42.png">
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
				"url":"controldzsign/checkId.do",
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
					this.src="uploadFiles/uploadImgs/puke/"+strFileName+".png";//再次点击还原
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
						$('<img class="wh" src="uploadFiles/uploadImgs/puke/'+nname+'.png">').appendTo("#sel");
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
					if(strs.length>16){//最多输入17张
						$("#sel").tips({
							side:3,
				            msg:'最多选择17张',
				            bg:'#AE81FF',
				            time:2
				        });
						$("#CNT").focus();
					}else{
						this.src="uploadFiles/uploadImgs/dz_selected/"+strFileName+".png";//选中更改
						$('<img class="wh" src="uploadFiles/uploadImgs/puke/'+strFileName+'.png">').appendTo("#sel"); 
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
			$.ajax({
				"url":"controldzsign/checkId.do",
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
		
		$(function(){
			var arry= new Array();//存放选取的牌
			var imgs= new Array();//存放所有
			var str=$("#CONTENT").val();
			arry=str.split(",");
			for(var i=0;i<arry.length;i++){//将新的内容添加到牌内容
				var nname=parseInt(arry[i]);
				$('<img class="wh" src="uploadFiles/uploadImgs/puke/'+nname+'.png">').appendTo("#sel");
			}
			var imgs=$(".pl").children(".wh");//获取所有选取的牌
			for(var i=0;i<imgs.length;i++){
				var idex=imgs[i].src
				var sc = idex.substring(idex.lastIndexOf("/")+1,idex.lastIndexOf("."));
				if($.inArray(sc, arry)> -1){//判断哪些牌已经选中
					imgs[i].src='uploadFiles/uploadImgs/dz_selected/'+sc+'.png'
				}
			}
			
		});
		
		$(function() {
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>