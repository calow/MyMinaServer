package com.calow.ichat.dao;

import java.util.List;

import com.calow.ichat.entity.Toolversion;

public interface ToolVersionDao {

	public int saveToolVersion(Toolversion tv);
	
	public List<Toolversion> getToolVersionsByToolId(int toolId);
	
	public Toolversion getToolVersionByToolId(int toolId);
	
	public Toolversion getToolVersionByTvId(int tvId);
	
	public void updateToolVersion(Toolversion tv);
	
}
