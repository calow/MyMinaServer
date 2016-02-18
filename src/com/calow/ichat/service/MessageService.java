package com.calow.ichat.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.calow.ichat.entity.Messageset;

public interface MessageService {

	public String getGroupMessage(String account, String groupId);

	public String getOfflineMessage(String account);

	public String getUnReadMessageList(String account);

	public int updateOfflineMessageState(String account, JSONArray array);

	public Map<String, Object> saveMessage(String account, String groupId,
			String content, String messageId);
	
	public boolean updataMessageSet(Messageset messageset);
	
	public List<Messageset> getMessageSetListByMessageId(int messageId);

}
