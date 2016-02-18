package com.calow.cim.nio.handler;

import com.calow.cim.nio.mutual.ReplyBody;
import com.calow.cim.nio.mutual.SentBody;
import com.calow.cim.nio.session.CIMSession;

public interface CIMRequestHandler {

	public abstract ReplyBody process(CIMSession session, SentBody message);
}
