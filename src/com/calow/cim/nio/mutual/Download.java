package com.calow.cim.nio.mutual;

import java.io.Serializable;

public class Download implements Serializable {

	/**
	 * 下载文件实体类
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
