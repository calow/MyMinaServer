package com.calow.ichat.cim.push;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.calow.cim.nio.mutual.Message;
import com.calow.cim.nio.session.CIMSession;
import com.calow.cim.nio.session.DefaultSessionManager;

public class DefaultMessagePusher implements CIMMessagePusher {

	private final Log log = LogFactory.getLog(getClass());

	private DefaultSessionManager sessionManager;

	public void setSessionManager(DefaultSessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	@Override
	public void pushMessageToUser(Message msg) {
		CIMSession session = sessionManager.getSession(msg.getReceiver());
		if (session != null && session.isConnected()) {
			session.write(msg);
		} else {
			// 写到数据库中
		}
	}

	public CIMSession getCIMSessionByUserAccount(String account) {
		CIMSession session = sessionManager.getSession(account);
		return session;
	}

}
