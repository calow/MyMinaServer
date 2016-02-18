package com.calow.ichat.dao;

import com.calow.ichat.entity.Message;


public interface MessageDao {

	public String getUnReadMessageList(String account);
	
	public int addGeneralMessage(Message msg);
	
	public int addSystemMessage(Message msg);
	
	public String getOfflineMessage(String account);
	
	public String getGroupMessage(String account, String groupId);
	
	public int updateOfflineMessageState(String account, String messageSetId);
}
