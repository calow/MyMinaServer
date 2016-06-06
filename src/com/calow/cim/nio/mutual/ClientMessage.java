package com.calow.cim.nio.mutual;

import java.io.Serializable;

public class ClientMessage implements Serializable {

	/**
	 * 客户端http发送消息
	 */
	private static final long serialVersionUID = 1L;

	private String content;

	private String groupId;

	private String messageId;
	
	private String loginId;

	public ClientMessage() {
	}

	public ClientMessage(String content, String groupId, String messageId, String loginId) {
		this.content = content;
		this.groupId = groupId;
		this.messageId = messageId;
		this.loginId = loginId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

}
