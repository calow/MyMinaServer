package com.calow.ichat.service;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.calow.ichat.entity.Tool;
import com.calow.ichat.entity.Toolversion;

public interface ToolService {

	public List<Tool> getToolList();
	
	public List<Tool> getToolListByLimit(String start, String limit);
	
	public byte[] getToolContentByToolId(String toolId);
	
	public Toolversion getToolVersionByToolId(String toolId);
	
	public Tool getToolByToolId(String toolId);
	
	public byte[] getToolContentByTvId(String tvId);
	
	public Toolversion getToolVersionByTvId(String tvId);
	
	public String getToolNameByTvId(String tvId);
	
	public int saveTool(byte[] content, String fileName, String contentType, Tool tool);
	
	public boolean deleteToolByToolId(String toolId);
	
	public boolean deleteToolVersionByTvId(String tvId);
	
	public String runTool(String toolId, String contextPath, String realPath);
	
	public void initActRunTool(HttpServletRequest request, HttpServletResponse response, String toolParams);
	
	public JSONArray getPCToolMessageAndTvIdList();
	
	public JSONArray getPhoneToolMessageAndTvIdList();
	
	public Connection getToolConnection();
}
