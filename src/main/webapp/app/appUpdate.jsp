<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>版本更新</title>
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	    <script  type="text/javascript" src="../static/js/jquery-1.7.2.js" charset="utf-8"></script>
	    <style>
	    	body{
	            padding: 0;
	            margin: 0;
	            background: url(img/bg.png) center center repeat;
			}
			#versionNo {
				color:red;
				font-family:"微软雅黑";
				font-size:20px;
				text-align:center;
			}
			#versionMsg {
				color:red;
				font-family:"微软雅黑";
				font-size:20px;
				text-align:center;
			}
			#cent {
				margin-top:90px;
			}
			#a1 {
				text-align:center;
			}
	    </style>
	</head>
<%
   String bashPath=request.getContextPath();
   String clientVer = request.getParameter("clientVer")==null?"0":request.getParameter("clientVer");
   System.out.println("clientVer:"+clientVer);
%>

	<body>
	<div id="cent">
		<div id="versionNo"></div>
		<div id="versionMsg"></div>
	</div>
	<br>
	<a id="a1" href="" onclick="downLoad()"  style="margin-left: 105px"> <img src="img/gengxin.png"></a>
	</body>
	<script type="text/javascript">
	//判断终端类型，通过返回boolean值来处理,使用方法 browser.versions.ios browser.versions.android
	var browser = {
		versions: function() {
			var u = window.navigator.userAgent;
			return {
				trident: u.indexOf('Trident') > -1,//IE内核
				presto: u.indexOf('Presto') > -1, //opera内核
				webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
				gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
				mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
				ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
				android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
				iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者安卓QQ浏览器
				iPad: u.indexOf('iPad') > -1, //是否为iPad
				webApp: u.indexOf('Safari') == -1, //是否为web应用程序，没有头部与底部
				weixin: u.indexOf('MicroMessenger') == -1 //是否为微信浏览器
			};
		}()
	}
	
	   var typeTerm;
	    if (browser.versions.android) {
	    	typeTerm="android";
	    } 
	    if(browser.versions.ios){
	    	typeTerm="ios";
	    }
	    var APP_URL;
	    var ver=<%=clientVer%>;
		$(function() {
			var curWwwPath = window.document.location.href;
			var pathName = window.document.location.pathname;
			var pos = curWwwPath.indexOf(pathName);
			var localhostPaht = curWwwPath.substring(0,pos);
			var reqAddr = localhostPaht+"/";
			$.ajax({
					type: "post",
					url: "<%=bashPath%>/m/checkversion",
					data: {
					   clientVer: ver,type:typeTerm
					},
					dataType: 'json',
					success: function(r) {
					  console.info(r);
					  $("#versionNo").html("最新版本为：v" + r.VERSION_NO);
					  $("#versionMsg").html("版本更新说明：" + r.VERSION_MSG);
					  APP_URL= r.APP_URL;
					},
					error: function(r) {
					   console.info(r);
					},
					complete: function(){
					}
				});
		});
		function downLoad(){
			if (browser.versions.android) {
				  $('#a1').attr('href',APP_URL); 
			    } else if(browser.versions.ios){
			      window.location.href="ios:download:"+APP_URL; 
			}
		}
	</script>
	<style>
		
	</style>
</html>