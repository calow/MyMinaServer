package com.calow.ichat.dao;

import com.calow.ichat.entity.Messagetype;

public interface MessageTypeDao {

	public Messagetype getMessageTypeByTypeValue(short value);
}
