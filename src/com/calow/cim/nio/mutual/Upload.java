package com.calow.cim.nio.mutual;

import java.io.Serializable;

public class Upload implements Serializable {

	/**
	 * 上传文件实体类
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String format;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	
}
