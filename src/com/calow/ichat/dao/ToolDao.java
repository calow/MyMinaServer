package com.calow.ichat.dao;

import java.sql.Connection;
import java.util.List;

import net.sf.json.JSONArray;

import com.calow.ichat.entity.Tool;

public interface ToolDao {
	
	public List<Tool> getToolList();
	
	public List<Tool> getToolListByLimit(int start, int count);
	
	public int saveToolMessage(Tool tool);
	
	public Tool getToolByToolId(int toolId);
	
	public void updateToolMessage(Tool tool);
	
	public JSONArray getPCToolMessageAndTvIdList();
	
	public JSONArray getPhoneToolMessageAndTvIdList();
	
	public Connection getSqlConnection();
}
