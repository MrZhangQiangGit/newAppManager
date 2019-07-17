<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html class="no-js">
<base href="<%=basePath%>">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <meta name="viewport"
        content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
  <title>${pd.SYSNAME }</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <link rel="stylesheet" type="text/css" href="amazeUI/css/amazeui.min.css"/>
  <link rel="stylesheet" type="text/css" href="amazeUI/css/login.css"/>
</head>
<body>
<div class="am-g">
	<!-- LOGO -->
	<div class="am-u-sm-12 am-text-center" >
		 <i class="am-icon-twitch myapp-login-logo"></i>
	</div>
	<!-- 登陆框 -->
	<div class="am-u-sm-11 am-u-sm-centered am-u-md-7 am-u-lg-4 ">
	<form class="am-form">
	  <fieldset class="myapp-login-form am-form-set">
		<div class="am-form-group am-form-icon">
			<i class="am-icon-user"></i>
			<input id="loginname" type="text" name="loginname" class="myapp-login-input-text am-form-field" placeholder="请输入您的账号">
		</div>
	    <div class="am-form-group am-form-icon">
			<i class="am-icon-lock"></i>
			<input id="password" type="password" name="password" class="myapp-login-input-text am-form-field" placeholder="至少6个字符">
		</div>
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
	  </fieldset>
	  <button id="to-recover" onclick="severCheck();" type="button" class="myapp-login-form-submit am-btn am-btn-primary am-btn-block btn-loading-example">登 陆</button>
	</form>
	</div>
	<!-- <div class="am-text-center am-u-sm-11 am-u-sm-centered myapp-login-form-shortcut">
		<hr class="myapp-login-form-hr" />
		<span class="myapp-login-form-hr-font">第三方登陆</span>
	</div>

	<div class="am-u-sm-12 am-text-center myapp-login-form-listico" >
		 <div class="am-u-sm-4 am-text-center" >
		 <i class="am-icon-btn am-primary am-icon-qq"></i>
		 </div>
		 <div class="am-u-sm-4 am-text-center" >
		 <i class="am-icon-btn am-danger am-icon-weibo"></i>
		 </div>
		 <div class="am-u-sm-4 am-text-center" >
		 <i class="am-icon-btn am-success am-icon-weixin"></i>
		 </div>
	</div>

</div>
<div
  class="am-slider am-slider-default"
  data-am-flexslider="{controlNav: 'thumbnails', directionNav: false, slideshow: false}">
  <ul class="am-slides">
    <li data-thumb="http://s.amazeui.org/media/i/demos/pure-4.jpg?imageView2/0/w/360">
      <img src="http://s.amazeui.org/media/i/demos/pure-4.jpg" /></li>
  </ul>
</div> -->
</body>
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="amazeUI/js/jquery.min.js"></script>
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="static/js/jquery.cookie.js"></script>
<script type="text/javascript" src="amazeUI/js/amazeui.js"></script>
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
	(function($) {
		  'use strict';

		  $(function() {
		    var $fullText = $('.admin-fullText');
		    $('#admin-fullscreen').on('click', function() {
		      $.AMUI.fullscreen.toggle();
		    });

		    $(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
		      $fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
		    });
		  });
		})(jQuery);

</script>
</html>