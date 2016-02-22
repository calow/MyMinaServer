package com.calow.ichat.api.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;

import com.calow.cim.nio.mutual.Upload;
import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.dao.StorageDao;
import com.calow.ichat.dao.impl.StorageDaoImpl;
import com.calow.ichat.entity.Storage;
import com.calow.ichat.service.StorageService;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UploadAction extends ActionSupport implements ModelDriven<Upload> {

	/**
	 * 上传文件action拦截器
	 */
	private static final long serialVersionUID = 1L;

	Upload upload = new Upload();

	@Override
	public Upload getModel() {
		return upload;
	}

	public void uploadFile() {
		System.out.println("name = " + upload.getName() + "---format = " + upload.getFormat());
		HttpServletResponse response = ServletActionContext.getResponse();
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("code", 200);
		StorageService storageService = (StorageService) ContextHolder.getBean("storageService");
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("ic_launcher.png");
		try {
			byte[] photo = new byte[inputStream.available()];
			inputStream.read(photo);
			inputStream.close();
			Storage storage = new Storage();
			storage.setSId(1);
			storage.setSContent(photo);
			storageService.updateStorage(storage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		postResult(response, datamap);
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
