package com.calow.cim.nio.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.calow.cim.nio.constant.CIMConstant;

public class DefaultSessionManager implements SessionManager {

	private static HashMap<String, CIMSession> sessions = new HashMap<String, CIMSession>();

	private static final AtomicInteger connectionsCounter = new AtomicInteger(0);

	@Override
	public void addSession(String account, CIMSession session) {
		if (session != null) {
			session.setAttribute(CIMConstant.SESSION_KEY, account);
			sessions.put(account, session);
			connectionsCounter.incrementAndGet();
		}
	}

	@Override
	public CIMSession getSession(String account) {

		return sessions.get(account);
	}

	@Override
	public Collection<CIMSession> getSessions() {

		return sessions.values();
	}

	@Override
	public void removeSession(CIMSession session) {
		sessions.remove(session.getAttribute(CIMConstant.SESSION_KEY));
	}

	@Override
	public void removeSession(String account) {
		sessions.remove(account);
	}

	@Override
	public boolean containsCIMSession(CIMSession ios) {

		return sessions.containsKey(ios.getAttribute(CIMConstant.SESSION_KEY));
	}

	@Override
	public String getAccount(CIMSession ios) {

		if (ios.getAttribute(CIMConstant.SESSION_KEY) == null) {
			for (String key : sessions.keySet()) {
				if (sessions.get(key).equals(ios)
						|| sessions.get(key).getNid() == ios.getNid()) {
					return key;
				}
			}
		} else {
			return ios.getAttribute(CIMConstant.SESSION_KEY).toString();
		}
		return null;
	}

}
