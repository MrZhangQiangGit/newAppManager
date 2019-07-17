<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>Login Page | bets</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="format-detection" content="telephone=no">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp" />
  <link rel="alternate icon" type="image/png" href="amazeUI/i/examples/landing.png">
  <link rel="stylesheet" type="text/css" href="amazeUI/css/amazeui.min.css"/>
  <link rel="stylesheet" type="text/css" href="amazeUI/css/app.css" >
  <style>
    .header {
      text-align: center;
    }
    .header h1 {
      font-size: 200%;
      color: #333;
      margin-top: 30px;
    }
    .header p {
      font-size: 14px;
    }
  </style>
</head>
<body>
<div class="header">
  <div class="am-g">
    <h1 id="sysName">手机适用管理平台</h1>
    <p>Mobile phone application management platform<br/>代理管理，玩家信息，数据更新，财务统计</p>
  </div>
  <hr />
</div>
<div class="am-g">
  <div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
    <h3>登录</h3>
    <hr>
    <!-- <div class="am-btn-group">
      <a href="#" class="am-btn am-btn-secondary am-btn-sm"><i class="am-icon-github am-icon-sm"></i> Github</a>
      <a href="#" class="am-btn am-btn-success am-btn-sm"><i class="am-icon-google-plus-square am-icon-sm"></i> Google+</a>
      <a href="#" class="am-btn am-btn-primary am-btn-sm"><i class="am-icon-stack-overflow am-icon-sm"></i> stackOverflow</a>
    </div> -->
    <br>
    <form method="post" class="am-form am-form-horizontal" id="loginForm">
    	<div class="am-form-group">
    		<!-- label标签占3等分,sm时文字左对齐 ,md时文字右对齐 -->
	      <label for="email" class="am-form-label am-u-sm-3 am-u-md-2 am-sm-text-left am-md-text-center ">账号:</label>
	      <div class="am-u-sm-9 am-u-md-10" >
		      <input id="loginname" type="text" name="loginname" >
	      </div>
    	</div>
      <br>
      <div class="am-form-group">
	      <label for="password" class="am-form-label am-u-sm-3 am-u-md-2 am-sm-text-left am-md-text-center ">密码:</label>
	      <div class="am-u-sm-9 am-u-md-10">
		      <input id="password" type="password" name="password" >
	      </div>
      </div>
      <br>
      <div class="am-cf" >
      	<div class="am-fl">
	      <label for="remember-me">
	        <input id="remember-me" type="checkbox" id="saveid" onclick="savePaw();">
	        	记住密码
	      </label>
      	</div>
	      <div class="am-fr am-cf">
	      	<div class="am-fl">
		      	<input type="text" placeholder="验证码" id="code" name="code" >
	      	</div>
	      	<div class="am-fr">
				<!-- <img id="codeImg" src="amazeUI/i/code.jpg"> -->
				<img style="" id="codeImg" alt="点击更换" title="点击更换" src="" /> 
	      	</div>
		  </div>
      </div>
      <br />
      <div class="am-cf">
        <input id="to-recover" onclick="severCheck();" type="button" name="" value="登 录" class="am-btn am-btn-primary am-btn-sm am-radius am-fl">
        <input onclick="cancel();" type="button" name="" value="取 消" class="am-btn am-btn-danger am-btn-sm am-radius am-fl">
        <input type="button" name="" value="忘记密码 ^_^? " class="am-btn am-btn-default am-btn-sm am-fr">
      </div>
    </form>
    <hr>
    <p class="am-u-sm-centered am-center">Copyright ©  2018</p>
  </div>
</div>
</body>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="amazeUI/js/jquery.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
<script type="text/javascript" src="amazeUI/js/amazeui.js"></script>
<!-- <script type="text/javascript" src="static/ace/js/jquery.js"></script> -->
<script type="text/javascript">
	//登录
	function login(){
		var loginname = $("#loginname").val();
		var password = $("#password").val();
		var code = $("#code").val();
		if(loginname == ''){
			$('#loginname').popover({
			    content: '输入登录账号!',
			    theme:'warning sm'
			}).popover('open'); 
			return false;
		}
		if(password ==''){
			$('#password').popover({content:'请输入密码!',theme:'danger sm'}).popover('open');
			return false;
		}
		if(code ==''){
			$('#code').popover({content:'请输入验证码!',theme:'danger sm'}).popover('open');
			return false;
		}
		return true;
	}
	
	//服务器校验
	function severCheck(){
		if(login()){
			var loginname = $("#loginname").val();
			var password = $("#password").val();
			var code = loginname+","+password+","+$("#code").val();
			$.ajax({
				type: "POST",
				url: 'login_login',
		    	data: {KEYDATA:code,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" == data.result){
						saveCookie();
						window.location.href="mainApp/index";
					}else if("usererror" == data.result){
						$("#loginname").popover({
							content : "用户名或密码有误",
							theme : 'warning sm'
						}).popover('open');
						showfh();
						$("#loginname").focus();
					}else if("codeerror" == data.result){
						$("#code").popover({
							content : "验证码有误",
							theme : 'warning sm'
						}).popover('open');
						showfh();
						$("#code").focus();
					}else{
						$("#loginname").popover({
							content : "缺少参数",
							theme : 'warning sm'
						}).popover('open');
						showfh();
						$("#loginname").focus();
					}
				}
			});
		}
	}
	
	//取消登录
	function cancel() {
		$("#loginname").val('');
		$("#password").val('');
	}
	var timer;
	function showfh(){
		fhi = 1;
		//关闭提示晃动屏幕，注释掉这句话即可
		//timer = setInterval(xzfh2, 10); 
	};
	var current = 0;
	function xzfh(){
		current = (current)%360;
		document.body.style.transform = 'rotate('+current+'deg)';
		current ++;
		if(current>360){current = 0;}
	};
	var fhi = 1;
	var current2 = 1;
	function xzfh2(){
		if(fhi>50){
			document.body.style.transform = 'rotate(0deg)';
			clearInterval(timer);
			return;
		}
		current = (current2)%360;
		document.body.style.transform = 'rotate('+current+'deg)';
		current ++;
		if(current2 == 1){current2 = -1;}else{current2 = 1;}
		fhi++;
	};
	/* //服务器校验
	function severCheck(){
		if(check()){
			var loginname = $("#loginname").val();
			var password = $("#password").val();
			var code = loginname+","+password+","+$("#code").val();
			$.ajax({
				type: "POST",
				url: 'login_login',
		    	data: {KEYDATA:code,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" == data.result){
						saveCookie();
						window.location.href="mainApp/index";
					}else if("usererror" == data.result){
						$("#loginname").tips({
							side : 1,
							msg : "用户名或密码有误",
							bg : '#FF5080',
							time : 15
						});
						showfh();
						$("#loginname").focus();
					}else if("codeerror" == data.result){
						$("#code").tips({
							side : 1,
							msg : "验证码输入有误",
							bg : '#FF5080',
							time : 15
						});
						showfh();
						$("#code").focus();
					}else{
						$("#loginname").tips({
							side : 1,
							msg : "缺少参数",
							bg : '#FF5080',
							time : 15
						});
						showfh();
						$("#loginname").focus();
					}
				}
			});
		}
	} */
	
	//客户端校验
	function check() {
		if ($("#loginname").val() == "") {
			$("#loginname").tips({
				side : 2,
				msg : '用户名不得为空',
				bg : '#AE81FF',
				time : 3
			});
			showfh();
			$("#loginname").focus();
			return false;
		} else {
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}
		if ($("#password").val() == "") {
			$("#password").tips({
				side : 2,
				msg : '密码不得为空',
				bg : '#AE81FF',
				time : 3
			});
			showfh();
			$("#password").focus();
			return false;
		}
		if ($("#code").val() == "") {
			$("#code").tips({
				side : 1,
				msg : '验证码不得为空',
				bg : '#AE81FF',
				time : 3
			});
			showfh();
			$("#code").focus();
			return false;
		}
		$("#loginbox").tips({
			side : 1,
			msg : '正在登录 , 请稍后 ...',
			bg : '#68B500',
			time : 10
		});
		return true;
	}
	//加载验证码和获取系统名称
	$(document).ready(function() {
		getSysName();
		changeCode();
		$("#codeImg").bind("click", changeCode);//给元素添加事件处理程序,规定事件发生时处理方法
	});
	//获取项目名称
	function getSysName(){
		var url = "agentApp/getMessage.do"
		 $.getJSON(url,function(data){
			console.log("系统名:"+data[0].SYSNAME);
			$("#sysName").text(data[0].SYSNAME)
		}); 
		/* $.ajax({
			url:url,
			data:{tm:new Date().getTime()},
			type:"post",
			dataType:"json",
			success:function(data){
				console.log(data[0])
				$("#sysName").html(data[0].SYSNAME)
			}
		});  */
	}
	//获取验证码
	function changeCode() {
		$("#codeImg").attr("src", "code.do?t=" + genTimestamp());//返回或修改元素属性(修改元素src属性)
	}
	$(document).keyup(function(event) {//按键移开事件
		if (event.keyCode == 13) {//keyCode = 13 表示Enter键
			$("#to-recover").trigger("click");//触发单击事件
		}
	});
	//获取时间戳
	function genTimestamp() {
		var time = new Date();
		return time.getTime();
	}
	//记住密码
	function savePaw() {
		if (!$("#saveid").attr("checked")) {
			$.cookie('loginname', '', {
				expires : -1
			});
			$.cookie('password', '', {
				expires : -1
			});
			$("#loginname").val('');
			$("#password").val('');
		}
	}
	//保存cookie
	function saveCookie() {
		if ($("#saveid").attr("checked")) {
			$.cookie('loginname', $("#loginname").val(), {
				expires : 7
			});
			$.cookie('password', $("#password").val(), {
				expires : 7
			});
		}
	}
	//取消登录
	function quxiao() {
		$("#loginname").val('');
		$("#password").val('');
	}
	jQuery(function() {
		var loginname = $.cookie('loginname');
		var password = $.cookie('password');
		if (typeof(loginname) != "undefined"
				&& typeof(password) != "undefined") {
			$("#loginname").val(loginname);
			$("#password").val(password);
			$("#saveid").attr("checked", true);
			$("#code").focus();
		}
	});
</script>
</html>
