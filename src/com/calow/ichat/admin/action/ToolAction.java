package com.calow.ichat.admin.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.calow.cim.nio.mutual.PreTool;
import com.calow.cim.nio.mutual.ToolParams;
import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.entity.Tool;
import com.calow.ichat.entity.Toolversion;
import com.calow.ichat.service.ToolService;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ToolAction extends ActionSupport implements
		ModelDriven<ToolParams> {

	private File file1; // 具体上传文件的 引用 , 指向临时目录中的临时文件
	private String file1FileName; // 上传文件的名字 ,FileName 固定的写法
	private String file1ContentType; // 上传文件的类型， ContentType 固定的写法

	/**
	 * 工具平台请求拦截器
	 */
	private static final long serialVersionUID = 1L;

	ToolParams tp = new ToolParams();

	@Override
	public ToolParams getModel() {
		return tp;
	}

	public String getToolList() {
		String result = "success";
		String start = null;
		String limit = null;
		List<Tool> tools;
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			start = tp.getStart();
			limit = tp.getLimit();
			ToolService toolService = (ToolService) ContextHolder
					.getBean("toolService");
			if (start != null && !start.equals("") && limit != null
					&& !limit.equals("")) {
				tools = toolService.getToolListByLimit(start, limit);
			} else {
				tools = toolService.getToolList();
			}
			List<PreTool> list = new ArrayList<PreTool>();
			for (int i = 0; i < tools.size(); i++) {
				PreTool pt = new PreTool();
				Tool t = tools.get(i);
				pt.setToolId(t.getTId());
				pt.setToolName(t.getTName());
				pt.setToolPlatform(t.getTPlatform());
				pt.setDescription(t.getTDescription());
				list.add(pt);
			}
			request.setAttribute("toolList", list);
		} catch (Exception e) {
			result = "error";
			e.printStackTrace();
		}
		return result;
	}

	public void downloadToolWithToolId() {
		HttpServletResponse response = ServletActionContext.getResponse();
		String toolId = tp.getToolId();
		ToolService toolService = (ToolService) ContextHolder
				.getBean("toolService");
		OutputStream outputStream = null;
		try {
			Toolversion tv = toolService.getToolVersionByToolId(toolId);
			Tool tool = toolService.getToolByToolId(toolId);
			String fileName = tool.getTName() + "." + tv.getTvFormat();
			byte[] b = toolService.getToolContentByToolId(toolId);
			response.setContentType("application/" + tv.getTvFormat());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			outputStream = response.getOutputStream();
			outputStream.write(b);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void downloadToolWithTvId() {
		HttpServletResponse response = ServletActionContext.getResponse();
		String tvId = tp.getTvId();
		ToolService toolService = (ToolService) ContextHolder
				.getBean("toolService");
		OutputStream outputStream = null;
		try {
			Toolversion tv = toolService.getToolVersionByTvId(tvId);
			Tool tool = toolService.getToolByTvId(tvId);
			String fileName = tool.getTName() + "." + tv.getTvFormat();
			byte[] b = toolService.getToolContentByTvId(tvId);
			response.setContentType("application/" + tv.getTvFormat());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			outputStream = response.getOutputStream();
			outputStream.write(b);
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String uploadTool() {
		InputStream is = null;
		try {
			is = new FileInputStream(file1);
			byte[] buf = new byte[is.available()];
			is.read(buf);
			Tool tool = new Tool();
			String toolName = tp.getToolName();
			if (toolName != null && !toolName.equals("")) {
				tool.setTName(toolName);
			} else {
				tool.setTName(file1FileName);
			}
			Short platform = tp.getPlatfrom();
			tool.setTPlatform(platform);
			String description = tp.getDescription();
			tool.setTDescription(description);
			Short state = 1;
			tool.setTState(state);
			ToolService toolService = (ToolService) ContextHolder
					.getBean("toolService");
			toolService.saveTool(buf, getName(file1FileName),
					getExtension(file1FileName), tool);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String deleteTool() {
		boolean result = false;
		String toolId = tp.getToolId();
		ToolService toolService = (ToolService) ContextHolder
				.getBean("toolService");
		result = toolService.deleteToolByToolId(toolId);
		return result ? "success" : "error";
	}
	
	public void runTool() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String contextPath = request.getSession().getServletContext().getContextPath();
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String toolId = tp.getToolId();
		ToolService toolService = (ToolService) ContextHolder
				.getBean("toolService");
		String result = toolService.runTool(toolId, contextPath, realPath);
		String jarPath = "jarList/" + toolId + ".jar";
		try {
			request.setAttribute("resourceUrl", contextPath + result);
			request.setAttribute("fyToolUrl", contextPath + "/tool/actRunTool.action?tp=" + jarPath);//fyToolUrl = actRunTool?tp=1664332/1703596/151/0/1/zhuangweihao//0/1664318/project_teaching/1';//提交表单请求url
			request.setAttribute("fyForwardUrl", contextPath + "/tool/runTool4ward.action?tp=" + result);//fyForwardUrl = 'runTool4ward?tp=1664332/1703596/151/0/1/zhuangweihao//0/1664318/project_teaching/1';//跳转的url
			request.setAttribute("userAccount", "");//userAccount = 'zhuangweihao';//当前用户
			request.getRequestDispatcher(result + "/pages/main.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void actRunTool(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String toolParams = tp.getTp();
		ToolService toolService = (ToolService) ContextHolder
				.getBean("toolService");
		toolService.dispatchActRunTool(request, response, toolParams);
	}

	private String getName(String fileName) {
		int position = fileName.lastIndexOf(".");
		String name = fileName.substring(0, position);
		return name;
	}

	private String getExtension(String fileName) {
		int position = fileName.lastIndexOf(".");
		String extension = fileName.substring(position + 1);
		return extension;
	}

	public File getFile1() {
		return file1;
	}

	public void setFile1(File file1) {
		this.file1 = file1;
	}

	public String getFile1FileName() {
		return file1FileName;
	}

	public void setFile1FileName(String file1FileName) {
		this.file1FileName = file1FileName;
	}

	public String getFile1ContentType() {
		return file1ContentType;
	}

	public void setFile1ContentType(String file1ContentType) {
		this.file1ContentType = file1ContentType;
	}

	public void postResult(HttpServletResponse response,
			HashMap<String, Object> datamap) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(new Gson().toJson(datamap));
			pw.flush();
		} catch (IOException e) {
			datamap.put("code", 500);
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
}
