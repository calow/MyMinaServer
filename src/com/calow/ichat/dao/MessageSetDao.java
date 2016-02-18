package com.calow.ichat.dao;

import java.util.List;

import com.calow.ichat.entity.Messageset;

public interface MessageSetDao {

	public int addMessageSet(Messageset messageset);
	
	public void updateMessageSet(Messageset messageset);
	
	public List<Messageset> getMessageSetByMessageId(int messageId);
}
