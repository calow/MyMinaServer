<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="java.util.Collection"%>
<%@ page import="com.calow.cim.nio.mutual.PreTool"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
			 
	Collection<PreTool> toolList  = (Collection<PreTool>)request.getAttribute("toolList");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>工具开发平台</title>

		<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/css/webbase.css" />
		<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/css/main-layout.css" />
		<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/css/base-ui.css" />
		<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/css/table.css" />
		<link charset="utf-8" rel="stylesheet" 	href="<%=basePath%>/resource/bootstrap/css/bootstrap.min.css" />
		<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/css/dialog.css" />
		<link charset="utf-8" rel="stylesheet" href="<%=basePath%>/resource/css/progress.css" />
		<script type="text/javascript" 	src="<%=basePath%>/resource/js/jquery-1.8.3.min.js"></script>
		<script type="text/javascript" 	src="<%=basePath%>/resource/js/jquery.form.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/resource/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>/resource/js/framework.js"></script>
		<script>
	 
		  
		  function showMessageDialog(account)
			{
			   doShowDialog("messageDialog");
			   $('#Saccount').val(account);
			   
			}
		 function doSendMessage()
		  {
		    var message = $('#message').val();
		    var account = $('#Saccount').val();
		    if($.trim(message)=='')
		    {
		       return;
		    }
		    showProcess('正在发送，请稍后......');
		    $.post("<%=basePath%>/cgi/message_send.action", {content:message,type:2,sender:'system',receiver:account},
			   function(data){
			   
			      hideProcess();
			      showSTip("发送成功");
			      doHideDialog("messageDialog");
			      
		     });
		  }
		  
		  
		  function onImageError(obj)
			{
			    obj.src="<%=basePath%>/webclient/images/icon_head_default.png";   
			}
			
		  function  openWebclient(){
		     window.open ("<%=basePath%>/console/webclient/main.jsp", "","height="+800+", width="+600+", top=0, left=0, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no");
		  }
		  
		</script>
	</head>
	<body class="web-app ui-selectable">


		<%@include file="tool_header.jsp"%>

		<%@include file="tool_nav.jsp"%>

		<div id="mainWrapper">
		
		<div class="lay-main-toolbar">
                   
         </div>
				<div>
					<form id="searchForm" style="padding: 0px;">
						<input type="hidden" name="currentPage" id="currentPage" />
						<table style="width: 100%" class="utable">

							<thead>
								<tr class="tableHeader">
                                    <th width="10%">工具Id</th>
									<th width="10%">工具名称</th>
									<th width="5%">运行平台</th>
									<th width="15%">工具描述</th>
									<th width=20%">操作</th>
								</tr>
								 
							</thead>
							<tbody id="checkPlanList">

                                <%
                                  for(PreTool tl : toolList)
                                  {
                                 %>
                                 	<tr id="<%=tl.getToolId() %>" style=" height: 50px;">
										<td>
											<%=tl.getToolId() %>
										</td>
										<td>
											<%=tl.getToolName()%>
										</td>
										<%
											if(tl.getToolPlatform() == 0){
										%>
										<td>PC</td>
										<%
											}else if(tl.getToolPlatform() == 1){
										%>
										<td>终端</td>
										<%
											} else{
										%>
										<td>移动</td>
										<%
											}
										%>
										<td>
											<%=tl.getDescription()%>
										</td>
										<td>
											<div>
											    <a href="<%=headerBasePath%>/tool/tool_run.action?toolId=<%=tl.getToolId()%>" target="_blank" type="button" class="btn btn-primary">运行</a>&nbsp;&nbsp;&nbsp;&nbsp;
											    <a href="<%=headerBasePath%>/tool/tool_download_tooId.action?toolId=<%=tl.getToolId()%>" target="_blank" type="button" class="btn btn-info">下载</a>&nbsp;&nbsp;&nbsp;&nbsp;
											    <a href="<%=headerBasePath%>/tool/tool_delete.action?toolId=<%=tl.getToolId()%>" type="button" class="btn btn-danger">删除</a>
										    </div>
										</td>
									</tr>	
								<%} %>
							</tbody>
						</table>
					</form>
				</div>
			</div>
		<script>
		       $('#toolListMenu').addClass('current');
		</script>
	</body>
</html>
