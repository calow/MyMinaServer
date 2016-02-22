package com.calow.ichat.api.action;

import java.util.HashMap;

import org.apache.struts2.ServletActionContext;

import com.calow.cim.nio.mutual.Download;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class DownloadAction extends ActionSupport implements ModelDriven<Download> {

	/**
	 * 下载文件action拦截器
	 */
	private static final long serialVersionUID = 1L;
	Download download = new Download();

	@Override
	public Download getModel() {
		return download;
	}
	
	public void downloadFile(){
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		HashMap<String, String> data = new HashMap<String, String>();
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("code", 200);
	}
	

}
