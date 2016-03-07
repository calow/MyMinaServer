package com.calow.cim.nio.mutual;

import java.io.Serializable;

public class PreTool implements Serializable {

	/**
	 * 工具开发平台，工具实体类对象
	 */
	private static final long serialVersionUID = 1L;

	private int toolId;
	private String toolName;
	private Short toolPlatform;
	private String description;

	public int getToolId() {
		return toolId;
	}

	public void setToolId(int toolId) {
		this.toolId = toolId;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	
	public Short getToolPlatform() {
		return toolPlatform;
	}

	public void setToolPlatform(Short toolPlatform) {
		this.toolPlatform = toolPlatform;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
