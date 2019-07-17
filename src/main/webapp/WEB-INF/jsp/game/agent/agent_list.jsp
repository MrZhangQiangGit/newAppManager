<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<!-- <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css"> -->
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
							
						<!-- 检索  -->
						<form action="agent/list.do" method="post" name="Form" id="Form">
						<table style="margin-top:5px;">
							<tr>
								<td>
									<div class="nav-search">
										<span class="input-icon">
											<input type="text" placeholder="这里输入关键词" class="nav-search-input" id="nav-search-input" autocomplete="off" name="keywords" value="${pd.keywords }" placeholder="这里输入关键词"/>
											<i class="ace-icon fa fa-search nav-search-icon"></i>
										</span>
									</div>
								</td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastStart" id="lastStart"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期" title="开始日期"/></td>
								<td style="padding-left:2px;"><input class="span10 date-picker" name="lastEnd" name="lastEnd"  value="" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期" title="结束日期"/></td>
								<td style="vertical-align:top;padding-left:2px;">
								</td>
								<td style="vertical-align:top;padding-left:2px"><a class="btn btn-light btn-xs" onclick="tosearch();"  title="检索"><i id="nav-search-icon" class="ace-icon fa fa-search bigger-110 nav-search-icon blue"></i></a></td>
							</tr>
						</table>
						<input type="hidden" id="cid" name="cid" value="${pd.id }">
						<!-- 检索  -->
					
						<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center" style="width:35px;">
									<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
									</th>
									<th class="center" style="width:50px;">序号</th>
									<th class="center">代理游戏ID</th>
									<th class="center">代理名称</th>
									<th class="center">代理级别</th>
									<th class="center">状态</th>
									<th class="center">分润比例 (%)</th>
									<th class="center">上级分润比例 (%)</th>
									<th class="center">总金额</th>
									<th class="center">已提现</th>
									<th class="center">余额</th>
									<th class="center">性别</th>
									<!-- <th class="center">手机号</th> -->
									<th class="center">上级代理</th>
									<th class="center">公会号</th>
									<th class="center">剩余房卡</th>
									<th class="center">售卡总量</th>
									<th class="center">创建人</th>
									<th class="center">创建时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty list}">
									<c:forEach items="${list}" var="var" varStatus="vs">
										<tr>
										<c:forEach items="${number}" var="num">
											<c:if test="${var.AGENT_CODE != num }">
												<td class='center'>
													<label class="pos-rel"><input type='checkbox' name='ids' value="${var.AGENT_CODE}" class="ace" /><span class="lbl"></span></label>
												</td>
											</c:if>
											<c:if test="${var.AGENT_CODE == num }">
												<td>
													<img alt="自己" src="static/ace/img/qizi.png">
												</td>
											</c:if>
										</c:forEach>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td class='center' onclick="getownuser('${var.AGENT_CODE}')">${var.AGENT_CODE}</td>
										<td class='center' onclick="recharge('${var.AGENT_CODE}','${var.AGENT_NAME}');">${var.AGENT_NAME}</td>
										<td class='center'>
											<c:if test="${var.LEVEL==0}">总代理</c:if>
											<c:if test="${var.LEVEL==1}">工会会长</c:if>
											<c:if test="${var.LEVEL==2}">工会副会长</c:if>
											<c:if test="${var.LEVEL>=3}">统战部长</c:if>
										</td>
										<td class='center'>
											<c:if test="${var.STATUS==1}">正常</c:if>
											<c:if test="${var.STATUS==0}">失效</c:if>
										</td>
										<td class='center'>${var.PROFIT}</td>
										<td class='center'>${var.UP_PROFIT}</td>
										<td class='center'>${var.TOTAL_MONEY}</td>
										<td class='center'>${var.CASH_MONEY}</td>
										<td class='center'>${var.SURPLUS_MONEY}</td>
										<td class='center'>
											<c:if test="${var.SEX==1}">男</c:if>
											<c:if test="${var.SEX==2}">女</c:if>
										</td>
										<%-- <td class='center'>${var.PHONE_NO}</td> --%>
										<td class='center'>${var.P_ID}</td>
										<td class='center'>${var.YQ_CODE}</td>
										<td class='center'>${var.CARD_NUM}</td>
										<td class='center'>${var.SALE_TOTAL}</td>
										<td class='center'>${var.CREATOR}</td>
										<td class='center'>
											<fmt:formatDate value="${var.CREATOR_TIME}" pattern="yyyy-MM-dd HH:mm:ss" />
										</td>
										<td class="center">
											<div class="hidden-sm hidden-xs btn-group">
												<a class="btn btn-xs btn-success" title="编辑" onclick="edit('${var.AGENT_CODE}');">
													<i class="ace-icon fa fa-pencil-square-o bigger-120" title="修改"></i>
												</a>
												<a class="btn btn-xs btn-success" title="充卡" onclick="recharge('${var.AGENT_CODE}','${var.AGENT_NAME}');">
													<i class="ace-icon fa fa-wrench bigger-120" title="充卡"></i>
												</a>
												<a class="btn btn-xs btn-success" title="玩家信息" onclick="userlist('${var.AGENT_CODE}','agent')">
													<i class=" bigger-120 glyphicon glyphicon-user" title="玩家信息"></i>
												</a>
<%-- 												<c:forEach items="${number}" var="num"> --%>
<%-- 													<c:if test="${var.AGENT_CODE != num }"> --%>
<%-- 														<a class="btn btn-xs btn-danger" onclick="del('${var.AGENT_CODE}');"> --%>
<!-- 															<i class="ace-icon fa fa-trash-o bigger-120" title="删除"></i> -->
<!-- 														</a> -->
<%-- 													</c:if> --%>
<%-- 												</c:forEach> --%>
											</div>
											<div class="hidden-md hidden-lg">
												<div class="inline pos-rel">
													<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
														<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
													</button>
		
													<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
														<li>
															<a style="cursor:pointer;" onclick="edit('${var.AGENT_CODE}');" class="tooltip-success" data-rel="tooltip" title="修改">
																<span class="green">
																	<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																	
																</span>
															</a>
														</li>
<%-- 														<c:forEach items="${number}" var="num"> --%>
<%-- 															<c:if test="${var.AGENT_CODE != num }"> --%>
<!-- 																<li> -->
<%-- 																	<a style="cursor:pointer;" onclick="del('${var.AGENT_CODE}');" class="tooltip-error" data-rel="tooltip" title="删除"> --%>
<!-- 																		<span class="red"> -->
<!-- 																			<i class="ace-icon fa fa-trash-o bigger-120"></i> -->
<!-- 																		</span> -->
<!-- 																	</a> -->
<!-- 																</li> -->
<%-- 															</c:if> --%>
<%-- 														</c:forEach> --%>
													</ul>
												</div>
											</div>
										</td>
									</tr>
									
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
									<a class="btn btn-mini btn-success" onclick="add();">新增代理</a>
									<c:if test="${level !=3}"><a class="btn btn-mini btn-success" onclick="updateyq();">用户平移</a></c:if>
<!-- 									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a> -->
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						</div>
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

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#Form").submit();
		}
		$(function() {
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
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
			
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		
		//新增
		function add(){
			console.log("${level}"+"asdas");
			 if(${level>=3}){
				 alert("无权限");
			 }else{
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="新增";
				 diag.URL = '<%=basePath%>agent/goAdd.do';
				 diag.Width = 600;
				 diag.Height = 550;
				 diag.Modal = true;				//有无遮罩窗口
				 diag. ShowMaxButton = true;	//最大化按钮
			     diag.ShowMinButton = true;		//最小化按钮
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 if('${page.currentPage}' == '0'){
							 top.jzts();
							 setTimeout("self.location=self.location",100);
						 }else{
							 nextPage(${page.currentPage});
						 }
					}
					diag.close();
				 };
				 diag.show();
			 }
			
		}
		
		//充卡
		function recharge(AGENT_CODE,AGENT_NAME){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="充卡";
			 diag.URL = '<%=basePath%>agentrecharge/goAdd.do?AGENT_CODE='+AGENT_CODE+'&AGENT_NAME='+AGENT_NAME;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>agent/delete.do?AGENT_CODE="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>agent/goEdit.do?AGENT_CODE='+Id;
			 diag.Width = 600;
			 diag.Height = 550;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		function updateyq(){
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>agent/updateyq.do?';
			 diag.Width = 600;
			 diag.Height = 550;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>agent/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		};
		//获取直属玩家及代理
		function getownuser(id){
			$("#cid").val(id)
			$("#Form").submit();
			 
			 
		}
		
		//玩家信息
		function userlist(id,type){//代理id ,查询类型:从代理页面查询
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="玩家信息";
			 diag.URL = '<%=basePath%>majuser/list.do?AGENT_CODE='+id+'&type='+type;
			 diag.Width = 850;
			 diag.Height = 550;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>agent/excel.do';
		}
	</script>


</body>
</html>