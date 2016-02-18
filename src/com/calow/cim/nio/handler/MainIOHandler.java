package com.calow.cim.nio.handler;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.calow.cim.nio.constant.CIMConstant;
import com.calow.cim.nio.mutual.ReplyBody;
import com.calow.cim.nio.mutual.SentBody;
import com.calow.cim.nio.session.CIMSession;

public class MainIOHandler extends IoHandlerAdapter {
	protected final Logger logger = Logger.getLogger(MainIOHandler.class);

	private HashMap<String, CIMRequestHandler> handlers = new HashMap<String, CIMRequestHandler>();

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		logger.warn("sessionCreated()... from "
				+ session.getRemoteAddress().toString());
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		logger.warn("sessionOpened()... from "
				+ session.getRemoteAddress().toString());
	}

	@Override
	public void messageReceived(IoSession ios, Object message) throws Exception {
		logger.debug("message: " + message.toString());
		/**
		 * flex 客户端安全策略请求，需要返回特定报文
		 */
		if (CIMConstant.FLEX_POLICY_REQUEST.equals(message)) {
			ios.write(CIMConstant.FLEX_POLICY_RESPONSE);
			return;
		}
		CIMSession session = new CIMSession(ios);
		ReplyBody reply = new ReplyBody();
		SentBody body = (SentBody) message;
		String key = body.getKey();
		CIMRequestHandler handler = handlers.get(key);
		if (handler == null) {
			reply.setCode(CIMConstant.ReturnCode.CODE_405);
			reply.setMessage("KEY [" + key + "] 服务端未定义");
		} else {
			reply = handler.process(session, body);
		}
		if (reply != null) {
			reply.setKey(key);
			session.write(reply);
			logger.debug("-----------------------process done. reply: "
					+ reply.toString());
		}
		session.setAttribute(CIMConstant.HEARTBEAT_KEY,
				System.currentTimeMillis());
	}

	@Override
	public void sessionClosed(IoSession ios) throws Exception {
		CIMSession session = new CIMSession(ios);
		try {
			logger.warn("sessionClosed()... from " + session.getRemoteAddress());
			CIMRequestHandler handler = handlers.get("sessionClosedHander");
			if (handler != null
					&& session.containsAttribute(CIMConstant.SESSION_KEY)) {
				handler.process(session, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.warn("sessionIdle()... from "
				+ session.getRemoteAddress().toString());
		if (!session.containsAttribute(CIMConstant.HEARTBEAT_KEY)) {
			session.close(false);
		} else {
			Object heartbeat = session.getAttribute(CIMConstant.HEARTBEAT_KEY);
			if(heartbeat != null && System.currentTimeMillis() - Long.valueOf(heartbeat.toString()) >= 300000){
				session.close(false);
			}
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.error("exceptionCaught()... from " + session.getRemoteAddress());
		logger.error(cause);
		cause.printStackTrace();
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		//设置心跳时间 
        session.setAttribute(CIMConstant.HEARTBEAT_KEY, System.currentTimeMillis());
	}

	public HashMap<String, CIMRequestHandler> getHandlers() {
		return handlers;
	}

	public void setHandlers(HashMap<String, CIMRequestHandler> handlers) {
		this.handlers = handlers;
	}

}
