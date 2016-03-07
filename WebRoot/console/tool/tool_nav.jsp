
<%
	String navBasePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
%>
<%@ page language="java" pageEncoding="UTF-8"%>
<div id="_main_nav" class="ui-vnav">
	<ul class="ui-nav-inner">
		<li style="height: 50px; text-align: center; margin-top: 10px;">
			<div class="btn-group" style="margin-top: 5px;">
				<a type="button" class="btn btn-danger" target="_blank"
					onclick="doShowDialog('UploadDialog')">上传工具（测试版）</a>
			</div>

		</li>
		<li style="border-bottom: 1px solid #D1D6DA;"></li>
		<li class="ui-item" id="toolListMenu">
			<a href="<%=navBasePath%>/tool/tool_list.action"> <span
				class="ui-text">工具列表</span> <i class="ui-bg nav-recycle"></i> </a>
		</li>
		<li style="border-bottom: 1px solid #D1D6DA;"></li>
		<li class="ui-item" id="toolListMenu">
			<a href=""> <span
				class="ui-text">待审核（未实现）</span> <i class="ui-bg nav-recycle"></i> </a>
		</li>
		<li style="border-bottom: 1px solid #D1D6DA;"></li>
		<li class="ui-item" id="toolListMenu">
			<a href=""> <span
				class="ui-text">已删除（未实现）</span> <i class="ui-bg nav-recycle"></i> </a>
		</li>
	</ul>
</div>