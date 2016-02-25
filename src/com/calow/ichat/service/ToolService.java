package com.calow.ichat.service;

import java.util.List;

import com.calow.ichat.entity.Tool;

public interface ToolService {

	public List<Tool> getToolList();
	
	public List<Tool> getToolListByLimit(String start, String limit);
	
	public byte[] getToolByToolId(String toolId);
	
	public byte[] getToolByTvId(String tvId);
	
	public int saveTool(byte[] content, String fileName, String contentType, Tool tool);
}
