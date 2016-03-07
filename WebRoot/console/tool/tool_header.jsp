<%@ page language="java" pageEncoding="UTF-8"%>

<%
	String headerBasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
	Object admin = session.getAttribute("admin");
%>
<script type="text/javascript">
  
	function showUserMenu()
	{
		if($('#_main_face_menu').is(":hidden"))
		{
			$('#_main_face_menu').fadeIn();
	        $('.user-avatar').addClass('user-avatar-hover');}
		else
		{
			$('#_main_face_menu').hide();
			$('.user-avatar').removeClass('user-avatar-hover');
		}
	}
	$(function(){
		$(".user-avatar").mouseenter(function(){
		    $(".user-avatar").addClass('user-avatar-hover');
		    $('#_main_face_menu').fadeIn();
	    });
		$("#_main_face_menu").mouseleave(function(){
			$(".user-avatar").removeClass('user-avatar-hover');
			$('#_main_face_menu').fadeOut();
		});
	});
  
	function doUpload(){
		var toolName = $('#toolName').val();
		var file = $('#file1').val();
		if(toolName == ""){
			alert("工具名称不能为空");
			return;
		}
		if(file == ""){
			alert("请选择文件");
			return;
		}
		var form = document.getElementById("uploadForm");
		form.submit();
	}
</script>


<div id="_main_fixed_header" class="header-fixed">

	<!-- 头部 -->
	<div id="_main_header_banner" class="header">
		<div id="_main_header_cnt" class="header-cnt">
			<div class="logo" style="left: -200px;">
			</div>

			<div class="btn-group" style="float: right; margin-top: 50px;">
				<a type="button" class="btn btn-primary"
					onclick="doShowDialog('aboutDialog')">工具开发管理平台</a>
			</div>
		</div>

	</div>

	<!--web的导航在左侧-->
</div>


<div class="panel panel-primary gdialog" id="aboutDialog"
	style="display: none; width: 400px; position: absolute;">
	<div class="panel-heading">
		工具开发管理平台
		<a class="close" onclick="doHideDialog('aboutDialog')">&times;</a>
	</div>
	<div class="panel-body" style="padding: 0px;">
	    <div style="text-align: center;background: #5FA0D3;height: 150px;">
			<img src="<%=headerBasePath%>/resource/img/icon.png" style="margin-top: 35px;height: 80px;height: 80px;"/>
		</div>
		<ul class="list-group">
			<li class="list-group-item">
				CIM即时通讯后台管理系统-工具开发管理平台
			</li>
			<li class="list-group-item">
				Email:725517818@qq.com
			</li>
			<li class="list-group-item">
				QQ:725517818
			</li>
		</ul>
	</div>
</div>
<div class="panel panel-primary gdialog" id="UploadDialog"
	style="display: none; width: 500px; position: absolute; height: 550px;">
	<div class="panel-heading">
		上传工具
		<a class="close" onclick="doHideDialog('UploadDialog')">&times;</a>
	</div>
	<div style="text-align: center; background: #5FA0D3; height: 100px;">
	</div>
	<form id="uploadForm" enctype="multipart/form-data" method="post" action="<%=headerBasePath%>/tool/tool_upload.action">
		<div class="panel-body">
				<div class="form-group" style="margin-top: 20px;">
					<label style="width: 50px;">
						<font color="red">*</font>名称:
					</label>
					<input type="text" id="toolName" name="toolName" class="form-control" maxlength="15"
						style="display: inline; width: 400px; height: 50px;"/>
				</div>
				<div class="form-group" style="margin-top: 20px;">
					<label style="width: 50px;">
						<font color="red">*</font>平台:
					</label>
					<select id="platfrom" name="platfrom" class="form-control" maxlength="32" style="display: inline; width: 400px; height: 50px;">
						<option value="0">PC</option>
						<option value="1">终端</option>
						<option value="2">移动</option> 
					</select>
				</div>
				<div class="form-group" style="margin-top: 20px;">
					<label style="width: 50px;">
						<font color="red">*</font>描述:
					</label>
					<input type="text" id="description" name="description" class="form-control" 
						maxlength="32" style="display: inline; width: 400px; height: 50px;" />
				</div>
				<div class="form-group" style="margin-top: 20px;">
					<label style="width: 50px;">
						<font color="red">*</font>文件:
					</label>
					<input type="file" id="file1" name="file1" class="form-control" 
						maxlength="32" style="display: inline; width: 400px; height: 50px;" />
				</div>
		</div>
		<div class="panel-footer" style="text-align: center;">
			<a type="button" class="btn btn-success btn-lg"
				style="width: 400px;" onclick="doUpload()"> 上传</a>
		</div>
		
		<div class="progress">
			<div class="bar"></div>
    		<div class="percent">0%</div>
		</div>
	</form>
</div>

<div id="global_mask"
	style="display: none; position: absolute; top: 0px; left: 0px; z-index: 998; background-color: rgb(190, 209, 216); opacity: 0.5; width: 100%; height: 100%; overflow: hidden; background-position: initial initial; background-repeat: initial initial;"></div>
