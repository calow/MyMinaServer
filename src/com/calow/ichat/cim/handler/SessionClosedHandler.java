package com.calow.ichat.cim.handler;

import com.calow.cim.nio.constant.CIMConstant;
import com.calow.cim.nio.handler.CIMRequestHandler;
import com.calow.cim.nio.mutual.ReplyBody;
import com.calow.cim.nio.mutual.SentBody;
import com.calow.cim.nio.session.CIMSession;
import com.calow.cim.nio.session.DefaultSessionManager;
import com.calow.ichat.common.util.ContextHolder;

public class SessionClosedHandler implements CIMRequestHandler {

	@Override
	public ReplyBody process(CIMSession ios, SentBody message) {
		DefaultSessionManager sessionManager = (DefaultSessionManager) ContextHolder
				.getBean("defaultSessionManager");
		if (ios.getAttribute(CIMConstant.SESSION_KEY) == null) {
			return null;
		}
		String account = ios.getAttribute(CIMConstant.SESSION_KEY).toString();
		sessionManager.removeSession(account);
		return null;
	}

}
