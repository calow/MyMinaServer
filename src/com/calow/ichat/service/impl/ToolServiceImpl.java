package com.calow.ichat.service.impl;

import java.util.List;

import com.calow.ichat.common.util.ContextHolder;
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
	public byte[] getToolByToolId(String toolId) {
		return null;
	}

	@Override
	public byte[] getToolByTvId(String tvId) {
		return null;
	}
	
	@Override
	public int saveTool(byte[] content, String fileName, String contentType, Tool tool){
		ToolDao toolDao = (ToolDao) ContextHolder.getBean("toolDao");
		ToolVersionDao toolVersionDao = (ToolVersionDao) ContextHolder.getBean("toolVersionDao");
		StorageDao storageDao = (StorageDao) ContextHolder.getBean("storageDao");
		
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
		int tvId = toolVersionDao.saveToolVersion(tv);
		
		return tvId;
	}

}
