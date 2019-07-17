<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>顺风十点半</title>
	<meta name="keywords" content="" />
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0" name="viewport">
    <meta name="360-fullscreen" content="true"/> 
    <link rel="stylesheet" type="text/css" href="css/showalert.css"> 
    <%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

    <style type="text/css">
        #weixin-tip {
            display: none;
            position: fixed;
            left: 0;
            top: 0;
            background: rgba(0, 0, 0, 0.8);
            filter: alpha(opacity=80);
            width: 100%;
            height: 100%;
            z-index: 100;
        }

        #weixin-tip p {
            text-align: center;
            margin: 0;
            height: 100%;
            position: relative;
        }

        #weixin-tip p img {
            width: 100%;
            height: 100%;
        }

        #weixin-tip .close {
            color: #fff;
            padding: 5px;
            font: bold 24px/26px simsun;
            text-shadow: 0 1px 0 #ddd;
            position: absolute;
            top: 0;
            left: 5px;
        }

        .hide {
            display: none;
        }

        /* 提示框*/
        .confirmBox {
            position: fixed;
            top: 0;
            left: 0;
            padding-top: 45%;
            display: -webkit-box;
            display: -ms-flexbox;
            display: -webkit-flex;
            display: flex;
            background: rgba(0, 0, 0, 0.4);
            width: 100%;
            height: 500px;
            visibility: hidden;
        }

        .confirmBox .row1 {
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
        }

        .confirmBox .row2 {
            position: relative;
            width: 290px;
        }

        .confirmBox .row2 .info {
            width: 100%;
            background-color: rgba(253, 253, 253, 0.95);
            display: inline-block;
            border-radius: .3em;
        }

        .confirmBox .row2 .info h2 {
            padding-top: 1em;
            margin-bottom: 10px;
            font-weight: bold;
            text-align: center;
            font-size: 16px;
        }

        .confirmBox .row2 .info .con {
            padding: 0 1em 1em 1em;
            text-align: center;
            font-size: 14px;
            line-height: 1.4;
        }

        .confirmBox .row2 .info .btn-box {
            display: -webkit-box;
            -webkit-box-orient: horizontal;
            -webkit-box-pack: justify;
            border-top: 1px #ECECEE solid;
        }

        .confirmBox .row2 .info .btn-box .btn-confirm {
            -webkit-box-flex: 1;
            padding: 14px 0;
            text-align: center;
            font-size: 16px;
            color: #0080FA;
        }

        .confirmBox .row2 .info .btn-box .btn-cancel {
            -webkit-box-flex: 1;
            padding: 14px 0;
            text-align: center;
            font-size: 16px;
            color: #0080FA;
            border-right: 1px #ECECEE solid;
        }

        .confirmBox .row3 {
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
        }

        @media only screen
        and (min-device-width: 240px)
        and (max-device-width: 700px)
        and (orientation: landscape) {
            /*iphone landscape */
            .confirmBox {
                padding-top: 20% !important;
            }

        }

        /*滚动浮层*/
        .overlay {
            width: 100%;
            position: fixed;
            bottom: -900px;
            left: 0px;
            z-index: 11;
        }

        .overlay .header {
            background-color: #F3F3F3;
            text-align: center;
            padding: 10px;
            position: relative;
            font-size: 14px;
            color: #666666;
            font-weight: bold;
        }

        .overlay .header a {
            display: block;
            position: absolute;
            right: 10px;
            top: 10px;
        }

        .overlay .content {
            padding: 10px;
            background-color: #fff;
        }

        .overlay .content .tit {
            margin-top: 0;
            font-size: 12px;
            color: #666666;
            margin-bottom: 10px;
        }

        .overlay .btn-list {
            position: relative;
        }

        .overlay .content #jc {
            text-decoration: underline;
            color: #ee272f;
            font-size: 12px;
            line-height: 26px;
            position: absolute;
            bottom: 0;
            right: 10px;
        }

        .overlay .content #btn-trust {
            display: block;
            margin: 30px auto 10px auto;
            color: #00A0EC;
            font-size: 14px;
            border-radius: 3em;
            padding: 5px;
            width: 40%;
            text-align: center;
            border: 1px solid #00A0EC;
            line-height: 26px;
        }

        .overlay .content .step1 {
            display: block;
            width: 80%;
            margin: 0 auto 20px auto;
        }

        .overlay .content .sign {
            text-decoration: none;
            border: 1px solid #dddddd;
            border-radius: .3em;
            padding: 7px 10px;
            display: -webkit-box;
            -webkit-box-orient: horizontal;
            -webkit-box-pack: justify;
            -webkit-box-align: center;
            margin: 0 auto;
        }

        .overlay .sign img {
            display: block;
            width: 40px;
            height: 41px;
            margin-right: 10px;
        }

        .overlay .sign p {
            font-size: 14px;
            color: #B5B5BB;

        }

        .overlay .sign p.sign-name {
            width: 80%;
            overflow: hidden;
            color: #000;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

        .overlay .finger {
            display: block;
            width: 24px;
            position: relative;
            top: -15px;
            float: right;
            right: 0;
            z-index: 12;
        }

        a {
            color: #8a8a8a;
            text-decoration: none;
            letter-spacing: 1px;
        }
		body{
            padding: 0;
            margin: 0;
            background: url(img/bg.png) center center repeat;
		}
    </style>

    <script type="text/javascript" src="js/zepto.min.js" charset="UTF-8"></script>
	 <script type="text/javascript" src="js/jquery-2.1.4.min.js" charset="UTF-8"></script>
	 <script  type="text/javascript" src="js/jweixin-1.0.0.js" charset="UTF-8"></script>
	 <script type="text/javascript" src="js/utils.js" charset="UTF-8"></script>
    <style type="text/css"></style>
    <script type="text/javascript">
    	//安卓
		var androidUrl = "https://fir.im/hfk6";
    	//ios
		var iosUrl = "https://fir.im/hfk6";
	
        var is_weixin = function () {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
				
                return true;
            } else {
                return false;
            }
        }
        function doLocation(url) {
            var a = document.createElement("a");
            if (!a.click) {
                window.location = url;
                return;
            }
            a.setAttribute("href", url);
            a.style.display = "none";
            document.body.appendChild(a);
            a.click();
        }
        function downandroid() {
            if (is_weixin()) {
                var winHeight = typeof window.innerHeight != "undefined" ? window.innerHeight : document.documentElement.clientHeight;
                //兼容IOS，不需要的可以去掉
                var tip = document.getElementById("weixin-tip");
                var close = document.getElementById("close");
                tip.style.height = winHeight + "px";
                //兼容IOS弹窗整屏
                tip.style.display = "block";
                close.onclick = function () {
                    tip.style.display = "none";
                }
            } else {
                doLocation(androidUrl);
            }
        }

        function downandroid2() {
            if (is_weixin()) {
                var winHeight = typeof window.innerHeight != "undefined" ? window.innerHeight : document.documentElement.clientHeight;
                //兼容IOS，不需要的可以去掉
                var tip = document.getElementById("weixin-tip");
                var close = document.getElementById("close");
                tip.style.height = winHeight + "px";
                //兼容IOS弹窗整屏
                tip.style.display = "block";
                close.onclick = function () {
                    tip.style.display = "none";
                }
            } else {
                doLocation(androidUrl);
            }
        }

        function downios() {
            if (is_weixin()) {
                var winHeight = typeof window.innerHeight != "undefined" ? window.innerHeight : document.documentElement.clientHeight;
                //兼容IOS，不需要的可以去掉
                var tip = document.getElementById("weixin-tip");
                var close = document.getElementById("close");
                tip.style.height = winHeight + "px";
                //兼容IOS弹窗整屏
                tip.style.display = "block";
                close.onclick = function () {
                    tip.style.display = "none";
                }
            } else {
                doLocation(iosUrl);
            }
        }

        function downios2() {
            if (is_weixin()) {
                var winHeight = typeof window.innerHeight != "undefined" ? window.innerHeight : document.documentElement.clientHeight;
                //兼容IOS，不需要的可以去掉
                var tip = document.getElementById("weixin-tip");
                var close = document.getElementById("close");
                tip.style.height = winHeight + "px";
                //兼容IOS弹窗整屏
                tip.style.display = "block";
                close.onclick = function () {
                    tip.style.display = "none";
                }
            } else {
                doLocation(iosUrl);
            }
        }

	var targetUrl = location.href.split('#')[0];

    </script>
</head>
<body>
<table style="border-spacing:10px;width: 100%;" >
    <tr>
        <td  style="width: 50%;background: #fff;text-align: center;">
            <div style="padding: 10px 5px;background: url(img/bg.png) center center repeat;">
                <img  style="width: 100%" src="img/android.png" title="" alt="安卓图片" />
                <img style="width: 100%;margin: 20px 0 10px 0;" src="img/androiddown.png" onClick="downandroid2();"> <br/>
            </div>
        </td>


        <td  style="width: 50%;background: #fff;text-align: center;">
            <div style="padding: 10px 5px;background: url(img/bg.png) center center repeat;">

                <img  style="width: 100%" src="img/ios.png" title="" alt="IOS图片" />
                <img  style="width: 100%;margin: 20px 0 10px 0;" src="img/iosdown.png" onClick="downios2();">

            </div>

        </td>
    </tr>
</table>
<div> <img id="img" style="width: 100%;vertical-align:bottom" src="img/youniu.png">
		<!-- <img id="img" style="width: 100%;vertical-align:bottom" src="img/yj.png"> -->
<br/><br/> </div>
<table style="width: 100%;border-spacing:0;" cellspace="0" >
    <tbody>
    <tr align="center">
        <td style="color:#666;font-family: '微软雅黑', Arial, Helvetica, sans-serif;padding:10px 10px 20px 10px;text-align: justify;font-size:18px;"  >
           微信不允许直接下载文件，请点击右上角的按钮，选择在浏览器中打开，或者复制链接到浏览器。<span style="color:#f00;font-size:14px;">（安装时请删除之前的游戏，苹果的用户安装后，请按此操作:设置☞通用☞设备管理或者描述文件，选择信用才能用）</span>
        </td>
    </tr>



    </tbody>
</table>
<div> <img id="img" style="width: 100%;vertical-align:bottom" src="img/iosSet.png"><br/><br/> </div>
<div id="weixin-tip">
    <p>
        <img src="img/live_weixin.png" alt="微信扫描打开APP下载链接提示代码优化">
        <span id="close" title="关闭" class="close">×</span>
    </p>
</div>

<script>
if (is_weixin()) {
var img = document.getElementById('img');
				img.width=150;
}
    var userAgent = navigator.userAgent.toLowerCase();
    /*滚动浮层 取消*/
    $('#btn-cancel').click(function () {
        $('.overlay').animate({bottom: "-900px"});
    });

    /*提示框 取消&确认*/
    $('.btn-cancel').click(function () {
        $('.confirmBox').css('visibility', 'hidden');
    });
    $('.btn-confirm').click(function () {
        window.open("http://tui.tongbu.com/HTML/guide-tui.html");
    });
    /*判断是否是微博或微信*/
    function is_weixn() {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            return true;
        } else {
            return false;
        }
    }
    function is_weibo() {
        var ua = navigator.userAgent.toLowerCase();
        if (ua.match(/Weibo/i) == "weibo") {
            return true;
        } else {
            return false;
        }
    }
    function addClass(el, css) {
        el.className = el.className + ' ' + css;
    }
    function removeClass(el, css) {
        el.className = el.className.replace(css, '');
    }
    function closeCover() {
        removeClass(document.querySelector('.popup'), 'show');
        addClass(document.querySelector('.popup'), 'hide');
        return false;
    }

    if (is_weixn() || is_weibo()) {
        var tags = document.getElementsByTagName('a');
        for (var i = 0; i < tags.length; i++) {
            if (tags[i].className == "d d1" || tags[i].className == "d d2") {
                tags[i].addEventListener('click', function (e) {
                    e.preventDefault();
                    removeClass(document.querySelector('.popup'), 'hide');
                    addClass(document.querySelector('.popup'), 'show');
                    return false;
                });
            }
        }

    }

    function GetQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;

    }
    var s = GetQueryString("s");
    if (s == "www") {
        removeClass(document.querySelector('.pc'), 'hide');
    }
	
// 	$(function(){
// 		utils.fun.showAlert("<b style='color:#f00;line-height:30px;'>贝贝四川麻将永久免费，</b><br/>免费才能畅玩！");
	
// 		var type = GetQueryString("type");
// 		if(type == 1){
// 			utils.fun.showAlert("有版本更新<br/><b style='color:#f00;line-height:30px;'>请卸载老版本后重新安装</b>");
// 		}
// 	})
	
	
</script>

</body>
</html>
