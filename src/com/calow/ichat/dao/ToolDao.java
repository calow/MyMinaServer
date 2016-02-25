package com.calow.ichat.dao;

import java.util.List;

import com.calow.ichat.entity.Tool;

public interface ToolDao {
	
	public List<Tool> getToolList();
	
	public List<Tool> getToolListByLimit(int start, int count);
	
	public int saveToolMessage(Tool tool);
	
	public Tool getToolByToolId(int toolId);
}
