package com.calow.ichat.api.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.calow.cim.nio.mutual.Download;
import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.entity.Group;
import com.calow.ichat.entity.User;
import com.calow.ichat.service.GroupService;
import com.calow.ichat.service.StorageService;
import com.calow.ichat.service.UserService;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class DownloadAction extends ActionSupport implements
		ModelDriven<Download> {

	/**
	 * 下载文件action拦截器
	 */
	private static final long serialVersionUID = 1L;
	Download download = new Download();

	@Override
	public Download getModel() {
		return download;
	}

	public void downloadFile() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("code", 200);
		postResult(response, datamap);
	}

	public void downloadGroupPic() {
		HttpServletResponse response = ServletActionContext.getResponse();
		StorageService storageService = (StorageService) ContextHolder
				.getBean("storageService");
		GroupService groupService = (GroupService) ContextHolder
				.getBean("groupService");
		OutputStream outputStream = null;
		try {
			Group group = groupService.getGroupByGroupId(Integer.valueOf(download.getId()));
			byte[] b = storageService.getStorageByResourceId(group
					.getResource().getRId());
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

	public void downloadUserPic() {
		HttpServletResponse response = ServletActionContext.getResponse();
		StorageService storageService = (StorageService) ContextHolder
				.getBean("storageService");
		UserService userService = (UserService) ContextHolder
				.getBean("userService");
		OutputStream outputStream = null;
		try {
			User user = userService.getUserByUserId(download.getId());
			byte[] b = storageService.getStorageByResourceId(user.getResource()
					.getRId());
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
