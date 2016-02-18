package com.calow.ichat.cim.push;

import com.calow.cim.nio.mutual.Message;

public interface CIMMessagePusher {
	/**
	 * 向用户发送消息
	 * 
	 * @param msg
	 */
	public void pushMessageToUser(Message msg);
}
