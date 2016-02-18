package com.calow.ichat.cim.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.calow.cim.nio.constant.CIMConstant;
import com.calow.cim.nio.handler.CIMRequestHandler;
import com.calow.cim.nio.mutual.Message;
import com.calow.cim.nio.mutual.ReplyBody;
import com.calow.cim.nio.mutual.SentBody;
import com.calow.cim.nio.session.CIMSession;
import com.calow.cim.nio.session.DefaultSessionManager;
import com.calow.ichat.common.util.Constants;
import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.dao.impl.MessageDaoImpl;

public class BindHandler implements CIMRequestHandler {

	protected final Logger logger = Logger.getLogger(BindHandler.class);

	@Override
	public ReplyBody process(CIMSession ios, SentBody message) {

		ReplyBody reply = new ReplyBody();
		DefaultSessionManager sessionManager = ((DefaultSessionManager) ContextHolder
				.getBean("defaultSessionManager"));
		try {
			String account = message.get("account");
			ios.setAccount(account);
			ios.setDeviceId(message.get("deviceId"));
			ios.setGid(UUID.randomUUID().toString());
			ios.setHost(message
					.get(InetAddress.getLocalHost().getHostAddress()));
			ios.setChannel(message.get("channel"));
			ios.setDeviceModel(message.get("device"));
			// 第一次心跳设置为登录时间
			ios.setBindTime(System.currentTimeMillis());
			ios.setHeartbeat(System.currentTimeMillis());
			/**
			 * 由于客户端断线服务端可能会无法获知的情况，客户端重连时，需要关闭旧的连接
			 */
			CIMSession oldSession = sessionManager.getSession(account);
			if (oldSession != null && !oldSession.equals(ios)) {
				oldSession.removeAttribute(CIMConstant.SESSION_KEY);
				Message msg = new Message();
				msg.setType(CIMConstant.MessageType.TYPE_999);
				msg.setReceiver(account);

				if (!oldSession.isLocalhost()) {

					/*
					 * 判断当前session是否连接于本台服务器，如不是发往目标服务器处理
					 * MessageDispatcher.execute(msg, oldSession.getHost());
					 */
				} else {
					oldSession.write(msg);
					oldSession.close(true);
					oldSession = null;
				}
				oldSession = null;
			}
			
			if (oldSession == null) {
				sessionManager.addSession(account, ios);
			}
			
			reply.setCode(CIMConstant.ReturnCode.CODE_200);
		} catch (Exception e) {
			reply.setCode(CIMConstant.ReturnCode.CODE_500);
			e.printStackTrace();
		}
		logger.debug("bind :account:" +message.get("account")+"-----------------------------" +reply.getCode());
		return reply;
	}

}
