<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css"> 
.window{ 
    width:250px; 
    z-index:9999;
    background-color:#ACCD3C; 
    position:absolute; 
    padding:2px; 
    margin:5px; 
    display:none; 
    } 
 .content{ 
    height:150px; 
    background-color:#FFF; 
    font-size:14px; 
    overflow:auto; 
    } 
 .title{ 
    padding:2px; 
    color:#761C19; 
    font-size:14px; 
    } 
 .title img{ 
   float:right; 
  } 
</style> 

<div class="window" id="right">
		<div id="title3" class="title">
			<img src="static/images/alttip.jpg" alt="关闭" style="margin-top: 3px;" />操作提示
		</div>
		<div class="content" id="content" style="text-align: center;"></div>
</div>
<script type="text/javascript">
//获取窗口的高度 
var windowHeight; 
//获取窗口的宽度 
var windowWidth; 
//获取弹窗的宽度 
var popWidth; 
//获取弹窗高度 
var popHeight; 
function init(){ 
   windowHeight=$(window).height(); 
   windowWidth=$(window).width(); 
   popHeight=$(".window").height(); 
   popWidth=$(".window").width(); 
} 
//显示弹出层 ,显示弹出内容
function popRightWindow(msg){ 
    init(); 
    //计算弹出窗口的左上角Y的偏移量 
    var popY=(windowHeight-popHeight)/2; 
    var popX=(windowWidth-popWidth)/2; 
    //设定窗口的位置 
    $("#content").html(msg);
    $("#right").css("top",popY).css("left",popX).slideToggle("slow"); 
    //自动关闭
    setTimeout("closetip()", 2000);
    } 
    
function closetip(){
    $("#right").hide();
    nextPage(${page.currentPage});
 }
</script>