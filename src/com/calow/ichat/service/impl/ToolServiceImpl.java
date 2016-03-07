package com.calow.ichat.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import javax.faces.application.Application;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.io.SAXReader;

import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.common.util.JarUtils;
import com.calow.ichat.dao.StorageDao;
import com.calow.ichat.dao.ToolDao;
import com.calow.ichat.dao.ToolVersionDao;
import com.calow.ichat.entity.Storage;
import com.calow.ichat.entity.Tool;
import com.calow.ichat.entity.Toolversion;
import com.calow.ichat.service.ToolService;

public class ToolServiceImpl implements ToolService {

	@Override
	public List<Tool> getToolList() {
		ToolDao toolDao = (ToolDao) ContextHolder.getBean("toolDao");
		return toolDao.getToolList();
	}

	@Override
	public List<Tool> getToolListByLimit(String start, String limit) {
		ToolDao toolDao = (ToolDao) ContextHolder.getBean("toolDao");
		return toolDao.getToolListByLimit(Integer.valueOf(start),
				Integer.valueOf(limit));
	}

	@Override
	public byte[] getToolContentByToolId(String toolId) {
		Toolversion version = this.getToolVersionByToolId(toolId);
		Storage storage = version.getStorage();
		return storage.getSContent();
	}

	@Override
	public Toolversion getToolVersionByToolId(String toolId) {
		ToolVersionDao toolVersionDao = (ToolVersionDao) ContextHolder
				.getBean("toolVersionDao");
		Toolversion version = toolVersionDao.getToolVersionByToolId(Integer
				.valueOf(toolId));
		return version;
	}

	@Override
	public Tool getToolByToolId(String toolId) {
		ToolDao toolDao = (ToolDao) ContextHolder.getBean("toolDao");
		Tool tool = toolDao.getToolByToolId(Integer.valueOf(toolId));
		return tool;

	}

	@Override
	public byte[] getToolContentByTvId(String tvId) {
		Toolversion tv = this.getToolVersionByTvId(tvId);
		Storage storage = tv.getStorage();
		return storage.getSContent();
	}

	@Override
	public Toolversion getToolVersionByTvId(String tvId) {
		ToolVersionDao toolVersionDao = (ToolVersionDao) ContextHolder
				.getBean("toolVersionDao");
		return toolVersionDao.getToolVersionByTvId(Integer.valueOf(tvId));
	}

	@Override
	public Tool getToolByTvId(String tvId) {
		Toolversion tv = this.getToolVersionByTvId(tvId);
		return tv.getTool();

	}

	@Override
	public int saveTool(byte[] content, String fileName, String contentType,
			Tool tool) {
		ToolDao toolDao = (ToolDao) ContextHolder.getBean("toolDao");
		ToolVersionDao toolVersionDao = (ToolVersionDao) ContextHolder
				.getBean("toolVersionDao");
		StorageDao storageDao = (StorageDao) ContextHolder
				.getBean("storageDao");

		Storage storage = new Storage();
		storage.setSContent(content);
		int storageId = storageDao.saveStorage(storage);

		int toolId = toolDao.saveToolMessage(tool);

		Toolversion tv = new Toolversion();
		tv.setStorage(storageDao.getStorageById(storageId));
		tv.setTool(toolDao.getToolByToolId(toolId));
		tv.setTvDescription(tool.getTDescription());
		tv.setTvFormat(contentType);
		tv.setTvSize(content.length);
		Short state = 1;
		tv.setTvState(state);
		int tvId = toolVersionDao.saveToolVersion(tv);

		return tvId;
	}

	@Override
	public boolean deleteToolByToolId(String toolId) {
		ToolDao toolDao = (ToolDao) ContextHolder.getBean("toolDao");
		ToolVersionDao toolVersionDao = (ToolVersionDao) ContextHolder
				.getBean("toolVersionDao");
		Tool tool = toolDao.getToolByToolId(Integer.valueOf(toolId));
		List<Toolversion> list = toolVersionDao.getToolVersionsByToolId(Integer
				.valueOf(toolId));
		Short state = 0;
		tool.setTState(state);
		toolDao.saveToolMessage(tool);
		for (Toolversion tv : list) {
			tv.setTvState(state);
			toolVersionDao.updateToolVersion(tv);
		}
		return true;
	}

	@Override
	public boolean deleteToolVersionByTvId(String tvId) {
		ToolVersionDao toolVersionDao = (ToolVersionDao) ContextHolder
				.getBean("toolVersionDao");
		Toolversion tv = toolVersionDao.getToolVersionByTvId(Integer
				.valueOf(tvId));
		Short state = 0;
		tv.setTvState(state);
		toolVersionDao.updateToolVersion(tv);
		return true;
	}

	@Override
	public String runTool(String toolId, String contextPath, String realPath) {
		String result = null;
		ToolVersionDao toolVersionDao = (ToolVersionDao) ContextHolder
				.getBean("toolVersionDao");
		Toolversion tv = null;
		FileOutputStream outputStream = null;
		String toolPath = realPath + "jarList/";
		String toolName = realPath + "jarList/" + toolId + ".jar";
		String releasePath = realPath + "releaseList/" + toolId;
		String returnPath = "/releaseList/" + toolId;
		File tool = new File(toolName);
		File filePath = new File(toolPath);
		File release = new File(releasePath);
		try {
			if(!tool.exists()){
				if(!filePath.exists()){
					filePath.mkdirs();
				}
				tv = toolVersionDao.getToolVersionByToolId(Integer
						.valueOf(toolId));
				byte[] b = tv.getStorage().getSContent();
				outputStream = new FileOutputStream(tool);
				outputStream.write(b);
			}
			if(!release.exists()){
				JarUtils.unJar(tool, release);
			}
			result = returnPath;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public void dispatchActRunTool(HttpServletRequest request,
			HttpServletResponse response, String toolParams) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String clazzName = SAX(realPath + toolParams, "EntryClass");
		try {
			URL url = new URL("file:" + realPath + toolParams);
			URLClassLoader myClassLoader = new URLClassLoader(new URL[] { url }, Thread.currentThread()
			        .getContextClassLoader());
			Class<?> myClass = myClassLoader.loadClass(clazzName);
			com.calow.cim.nio.mutual.Tool tool = (com.calow.cim.nio.mutual.Tool) myClass.newInstance();
			tool.act(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String SAX(String jarPath, String entry) {
		String value = null;
		try {
			JarFile mJarFile = new JarFile(jarPath);
			ZipEntry entity = mJarFile.getEntry("config.xml");
			InputStream in = mJarFile.getInputStream(entity);
			SAXReader reader = new SAXReader();
			org.dom4j.Document document = reader.read(in);
			org.dom4j.Element element = document.getRootElement();
			value = element.elementText(entry).trim();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return value;
	}
}
