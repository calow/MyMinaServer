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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.calow.cim.nio.mutual.PreTool;
import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.entity.Tool;
import com.calow.ichat.service.ToolService;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class ToolAction extends ActionSupport {

	private File file1; // 具体上传文件的 引用 , 指向临时目录中的临时文件
	private String file1FileName; // 上传文件的名字 ,FileName 固定的写法
	private String file1ContentType; // 上传文件的类型， ContentType 固定的写法

	/**
	 * 工具平台请求拦截器
	 */
	private static final long serialVersionUID = 1L;

	public String getToolList() {
		String result = "success";
		String start = null;
		String limit = null;
		List<Tool> tools;
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			start = (String) request.getAttribute("start");
			limit = (String) request.getAttribute("limit");
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
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String toolId = (String) request.getAttribute("toolId");
		ToolService toolService = (ToolService) ContextHolder
				.getBean("toolService");
		OutputStream outputStream = null;
		try {
			byte[] b = toolService.getToolByToolId(toolId);
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
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String tvId = (String) request.getAttribute("tvId");
		ToolService toolService = (ToolService) ContextHolder
				.getBean("toolService");
		OutputStream outputStream = null;
		try {
			byte[] b = toolService.getToolByTvId(tvId);
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
		String result = "success";
		InputStream is = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			is = new FileInputStream(file1);
			byte[] buf = new byte[is.available()];
			is.read(buf);
			Tool tool = new Tool();
			String toolName = (String) request.getAttribute("toolName");
			if (toolName != null && !toolName.equals("")) {
				tool.setTName(toolName);
			} else {
				tool.setTName(file1FileName);
			}
			String description = (String) request.getAttribute("description");
			tool.setTDescription(description);
			ToolService toolService = (ToolService) ContextHolder
					.getBean("toolService");
			int tvId = toolService.saveTool(buf, file1FileName,
					file1ContentType, tool);
			HashMap<String, Object> datamap = new HashMap<String, Object>();
			datamap.put("tvId", tvId);
			postResult(response, datamap);
		} catch (Exception e) {
			e.printStackTrace();
			result = "error";
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
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
