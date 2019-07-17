/**
 * Created by rcs on 2015/11/11.
 */
function Utils() {
    var self = this;
    // 提示弹框回掉函数(alert)
    var _alertCallBack = null;
    //提示弹框回掉函数(confirm,确认)
    var _confirmSureBack = null;
    //提示弹框回掉函数(confirm,取消)
    var _confirmCancelBack = null;

    // 应用数据常量
    this.data =
    {
        /*是否在测试中*/
        isTest: false,

        /*设备的系统*/
        //platform:"ios",
        //platform: "android",
        /*当前版本的id*/
        //versionId: "7",

        /*请求头部*/
        //urlHead: "/JvxHzwMJ/GateWay"
        /*图片的头部*/
        //imgHead: ""
        //imgHead:"http://192.168.0.107:8088/JvxWeb/img/"

    };

    // ajax 请求url
    this.url =
    {
        
	};
    // 全局公共方法
    this.fun =
    {
        /*显示加载中*/
        showLoading: function (id, cover) {
            if (cover) {
                var C = "rgba(255,255,255,0.3)"
            }
            else {
                var C = ";pointer-events:none"
            }
            $("body").append("<div id=" + id + " style='position:fixed; left:0; top:0; width:100%; height:100%; background:url(img/loading.gif) no-repeat center center " + C + "; z-index:99999'></div>").css({"overflow": "hidden"})
        },

        /*关闭加载框*/
        hideLoading: function (id) {
            $("body").css({"overflow": "auto"});
            $("#" + id).remove();
        },

        /*获取网页参数*/
        getQueryString: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return (r[2]);
            return null;
        },

        sendRequest: function (url, params, funSuccess, funError, isShowLoading) {
//            console.log("url = " + url);
            if (self.data.isTest) {
                self.fun.getTestJson(url, function (data) {
                    var returnInfo = data;
                    if (returnInfo.s == 1) {
                        funSuccess(returnInfo);
                    }
                    else if (returnInfo.s != 1 && funError)
                        funError(returnInfo.msg);
                });
                return;
            }

            if (isShowLoading)
                self.fun.showLoading("loading", true);
            
            $.post(self.data.urlHead, params, function (data) {
            	self.fun.hideLoading("loading");
            	data = JSON.parse(data);
                var returnInfo = data;
                 funSuccess(returnInfo);
                   
                    
                   
               
            });
            
            
        },






        /*
         *显示地图
         * id 显示地图div id
         **/
        searchmap: function (id, city, address) {
            var map = new BMap.Map(id);
            var myGeo = new BMap.Geocoder();
            myGeo.getPoint(address, function (point) {
                if (point) {
                    map.centerAndZoom(point, 19);
                    map.addOverlay(new BMap.Marker(point));
                }
            }, city);
            map.centerAndZoom(new BMap.Point(myGeo.x, myGeo.y), 18);
            map.addControl(new BMap.MapTypeControl());
            map.setCurrentCity(city);
            map.enableScrollWheelZoom(true);
        },


        //检查电话是否合法
        checkPhone: function (phone) {
            //var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
        	var myreg =  /^1[3|4|5|8|7|9|0]\d{9}$/;
        	var isPhoneNum = myreg.test(phone);
            return isPhoneNum;
        },

        //检查身份证号码是否合法
        checkId: function (id) {
            var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            var isId = reg.test(id);
            return isId;
        },

        //判断是否为数字（可以为小数）
        checkNum: function (num) {
            var reg = /^([0-9.]+)$/;
            var isNum = reg.test(num);
            return isNum;
        },

        //检查是否是自然数(不包括0)
        checkNaturalNum: function (num) {
            var reg = /^[1-9]\d*$/;
            var isNaturalNum = reg.test(num);
            return isNaturalNum;
        },
      //检查是否是自然数(不包括0)
        checkNaturalThreeNum: function (num) {
            var reg = /^[0-9]{4}$/;
            var isNaturalNum = reg.test(num);
            return isNaturalNum;
        },
        
      //检查是否是5位自然数(不包括0)
        checkNaturalFiveNum: function (num) {
            var reg = /^[0-9]{5}$/;
            var isNaturalNum = reg.test(num);
            return isNaturalNum;
        },
      //检查是否是5位自然数(不包括0)
        checkNaturalSixNum: function (num) {
            var reg = /^[0-9]{6}$/;
            var isNaturalNum = reg.test(num);
            return isNaturalNum;
        },
        
        //检查是否是1-5的正整数(不包括0)
        minmax: function (num) {
        	var reg = /^[1-5]$/;
        	var isNaturalNum = reg.test(num);
        	return isNaturalNum;
        },

        //邮箱验证
        checkEmail: function (email) {
            var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            var isEmail = reg.test(email);
            return isEmail;
        },

        //判断日期格式是否正确
        checkDate: function (date) {
            var reg = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
            var isDate = reg.test(date);
            return isDate;
        },

        //检查时候是ip加端口号格式
        checkIpPort:function(ip){
            var reg = /^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9]):\d{0,5}$/;
            var isIp = reg.test(ip);
            return isIp;
        },

        //生成一个固定长度的验证码
        getCode: function () {
            var code = "";
            var codeLength = 4;//验证码的长度
            var random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
            for (var i = 0; i < codeLength; i++) {
                var index = Math.floor(Math.random() * 36);
                code += random[index];
            }
            return code;
        },

        //毫秒值转换正常时间格式
        timeToString: function (time) {
            var datetime = new Date();
            datetime.setTime(time);
            //var year = datetime.getFullYear();
            //var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
            //var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
            var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
            var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
            var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
            return hour + ":" + minute + ":" + second;
        },

        //弹出框提示,和关闭之时回掉函数
        showAlert: function (msg, funCallBack) {
            utils._alertCallBack = funCallBack;
            var alertHtml = '<div class="myCover"> ' +
                '<div class="myPrompt"> ' +
                '<div class="CoverT">提示</div> ' +
                '<div class="CoverC">' + msg + '</div> ' +
                '<a href="javascript:void(0)" class="CoverB" id="btn-cover" onclick="utils.fun.removerAlertCover(this)">确定</a> ' +
                '</div> ' +
                '</div>';

            $("body").append(alertHtml);
        },

        //弹出confirm
        showConfirm: function (msg, sureBack, cancelBack) {
            utils._confirmSureBack = sureBack;
            utils._confirmCancelBack = cancelBack;
            var confirmHtml = '<div class="myConfirmCover" style="display: block;"> ' +
                '<div class="myConfirm"> ' +
                '<div class="prompt_title">提示</div> ' +
                '<div class="prompt_content">' + msg + '</div> ' +
                '<div class="prompt_bottom"><a onclick="utils.fun.sureConfirm(this)">确定</a><a onclick="utils.fun.removeConfirm(this)">取消</a></div> ' +
                '</div> ' +
                '</div>';

            $("body").append(confirmHtml);
        },

        //阻止浏览器返回键
        stopHardBack:function(){
            $(document).ready(function(e) {
                var counter = 0;
                if (window.history && window.history.pushState) {
                    $(window).on('popstate', function () {
                        window.history.pushState('forward', null, '#');
                        window.history.forward(1);
                    });
                }
                window.history.pushState('forward', null, '#'); //在IE中必须得有这两行
                window.history.forward(1);
            });
        },

        //及时推送的弹窗
        showSocketAlert:function(type,msg,id,sureFun,cancelFun){
            var socketAlert = $("#socketAlert");
            var typeStr = "";
            if(type == 1){ //消息不弹窗

            }else if(type == 2){ //投票
                typeStr = "投票"
            }else{  //会议
                typeStr = "活动"
            }
            if(socketAlert.length <= 0){
                var strHtml = '<div class="Cover" style="display: block" id="socketAlert"> ' +
                    '<div class="Confirm04"> ' +
                    '<div class="prompt_title"><strong>'+typeStr+'</strong></div> ' +
                    '<div class="prompt_content" id="">'+msg+'</div> ' +
                    '<div class="prompt_bottom"> ' +
                    '<a  id="msgCancel">忽略</a> ' +
                    '<a  id="msgSure">查看</a> ' +
                    '<div class="Clear"></div> ' +
                    '</div> ' +
                    '</div> ' +
                    '</div>';
                $("body").append(strHtml);

                //确认按钮
                $("#msgSure").click(function(){
                    $("#socketAlert").remove();
                    if(type == 2){
                        if(self.fun.checkIsLogin()){
                            self.fun.requestReadVote(id);
                        }
                    }
                    sureFun();
                });

                //取消按钮
                $("#msgCancel").click(function(){
                    $("#socketAlert").remove();
                });
            }
        },

        //发送阅读投票通知请求
        requestReadVote:function(id){
            var url = "";
            var params = {type:1,xx_id:id,is_read:0};
            params.service = "msgAppService.setXxjsr";
            self.fun.sendRequest(url,params,null,null,false);
        },

        //检查是否有信息,没有信息显示没有信息提示，有则不提示
        checkHasData: function (data) {
            if (data.length > 0) {
                $(".None").hide();
            } else {
                $(".None").show();
            }
        },

        //删除提示框和回掉方法
        removerAlertCover: function (element) {
            $(element).parent().parent().remove();
            if (utils._alertCallBack) {
                utils._alertCallBack();
            }
        },

        //确认按钮confirm对话框
        sureConfirm: function (element) {
            $(element).parent().parent().parent().parent().remove();
            if (utils._confirmSureBack) {
                utils._confirmSureBack();
            }
        },

        //取消按钮confirm对话框
        removeConfirm: function (element) {
            $(element).parent().parent().parent().remove();
            if (utils._confirmCancelBack) {
                utils._confirmCancelBack();
            }
        },


        //请求app的版本信息
        requestVersion: function () {
            var url = utils.url.version;
            var params = {type: 0, service: "versionAppService.getNewVersion"};
            utils.fun.sendRequest(url, params, function (data) {
                utils.fun.checkVersion(data);
            }, function (msg) {
                utils.fun.showAlert(msg);
            }, false);
        },

        //检查版本信息
        checkVersion: function (data) {
            var version_code = data.version_code;
            var version_size = data.version_size;
            var version_id = data.version_id;
            var url_android = data.url_android;
            var url_ios = data.url_ios;

            if (version_id > utils.data.versionId) {
                $("#content").html("软件版本:" + version_code + "<br/>文件大小:" + version_size);
                $("#version_cover").show();

                //确定更新
                $("#version_sure").click(function () {
                    if (utils.data.platform == "android") {//安卓
                        window.location.href = url_android;
                    } else {//苹果
                        window.location.href = url_ios;
                    }
                });
                //暂不更新
                $("#version_cancel").click(function () {
                    $("#version_cover").hide();
                });
            } else {
                window.plugins.toast.show('已经是最新版本了!', 'long', 'bottom', null, null);
            }
        },


        /*获取测试数据*/
        getTestJson: function (url, funCallBacl) {
            $.getJSON("textJson/text.json", function (data) {
                funCallBacl(data[url]);
            });
        },

        /*检测是否是已经登陆用户*/
        checkIsLogin: function () {
            var tel = window.localStorage.getItem("userLoginTel");
            var pwd = window.localStorage.getItem("userLoginPass");
            if (tel && pwd)
                return true;
            else {
                //if (confirm("未登录,不能使用该功能.是否立即登陆?")) {
                //    window.location.href = "../webhcz/login.html";
                //}
                return false;
            }
        },

        //弹出分享框
        showShareAlert:function(params){
            var strHtml = '';
            var shareAlert = $("#shareAlert");
            if(shareAlert.length <= 0){
                strHtml = '<div class="Cover" id="shareAlert"> ' +
                    '<div class="bge8e8e7"> ' +
                    '<div class="prompt_title">分享至</div> ' +
                    '<div class="prompt_content"> ' +
                    '<ul> ' +
                    '<li><img src="img/qq.png" alt="qq" id="qqShare"/><br/>QQ</li> ' +
                    '<li><img src="img/weixin.png" alt="weixin" id="weChatShare" /><br/>微信好友</li> ' +
                    '<li><img src="img/pengyouquan.png" alt="pengyouquan" id="weChatZoneShare" /><br/>朋友圈</li> ' +
                    '</ul> ' +
                    '</div> ' +
                    '<div class="prompt_bottom"> ' +
                    '<a id="share_cancel">取消</a> ' +
                    '</div> ' +
                    '</div> ' +
                    '</div>';

                $("body").append(strHtml);

                //QQ分享
                $("#qqShare").click(function(){
                    var args = {};
                    args.url = params.url;
                    args.title = params.title;
                    args.description = params.dec;
                    args.imageUrl = "http://120.24.85.25:8080/ftpserver/upload/logo/logo.png";
                    args.appName = "激光杂志";
                    YCQQ.shareToQQ(function(){
                        console.log("share success");
                    },function(failReason){
                        console.log(failReason);
                    },args);
                    $("#shareAlert").remove();
                });

                //微信分享
                $("#weChatShare").click(function(){
                    //alert(JSON.stringify(params));
                    Wechat.share({
                        message: {
                            title: params.title,
                            description: params.dec,
                            thumb: "http://120.24.85.25:8080/ftpserver/upload/logo/logo.png",
                            mediaTagName: "TEST-TAG-001",
                            messageExt: "这是第三方带的测试字段",
                            messageAction: "<action>dotalist</action>",
                            media: {webpageUrl:params.url}
                        },
                        scene: Wechat.Scene.SESSION   // share to SESSION
                    }, function () {
                        //alert("Success");
                    }, function (reason) {
                        //alert("Failed: " + reason);
                    });
                    $("#shareAlert").remove();
                });

                //朋友圈分享
                $("#weChatZoneShare").click(function(){
                    Wechat.share({
                        message: {
                            title: params.title,
                            description: params.dec,
                            thumb: "http://120.24.85.25:8080/ftpserver/upload/logo/logo.png",
                            mediaTagName: "TEST-TAG-001",
                            messageExt: "这是第三方带的测试字段",
                            messageAction: "<action>dotalist</action>",
                            media: {webpageUrl:params.url}
                        },
                        scene: Wechat.Scene.TIMELINE   // share to TIMELINE
                    }, function () {
                        //alert("Success");
                    }, function (reason) {
                        //alert("Failed: " + reason);
                    });
                    $("#shareAlert").remove();
                });

                //取消
                $("#share_cancel").click(function(){
                    $("#shareAlert").remove();
                });
            }

        },


        /*添加或者覆盖cookie*/
        "addCookieValue": function (key, value) {
            document.cookie = key + "=" + encodeURIComponent(value);
        },

        /*通过cookie的key来获取值*/
        "getCookieValueByKey": function (key) {
            //获取cookie字符串
            var strCookie = document.cookie;
            //将多cookie切割为多个名/值对
            var arrCookie = strCookie.split("; ");
            //遍历cookie数组，处理每个cookie对
            var lenth = arrCookie.length;
            for (var i = 0; i < lenth; i++) {
                var arr = arrCookie[i].split("=");
                //找到名称为userId的cookie，并返回它的值
                if (key == arr[0]) {
                    return decodeURIComponent(arr[1]);
                }
            }
        }
    }
}
var utils = new Utils();