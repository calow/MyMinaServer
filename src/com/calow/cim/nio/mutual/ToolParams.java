package com.calow.cim.nio.mutual;

import java.io.Serializable;

public class ToolParams implements Serializable {

	/**
	 * 工具平台拦截器，对象属性注入实例
	 */
	private static final long serialVersionUID = 1L;
	public String start;
	public String limit;
	public String toolId;
	public String tvId;
	public String toolName;
	public Short platfrom;
	public String description;
	public String tp;
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getToolId() {
		return toolId;
	}
	public void setToolId(String toolId) {
		this.toolId = toolId;
	}
	public String getTvId() {
		return tvId;
	}
	public void setTvId(String tvId) {
		this.tvId = tvId;
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public Short getPlatfrom() {
		return platfrom;
	}
	public void setPlatfrom(Short platfrom) {
		this.platfrom = platfrom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTp() {
		return tp;
	}
	public void setTp(String tp) {
		this.tp = tp;
	}
	
	
}
