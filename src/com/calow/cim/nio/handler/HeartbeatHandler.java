package com.calow.cim.nio.handler;

import org.apache.log4j.Logger;

import com.calow.cim.nio.constant.CIMConstant;
import com.calow.cim.nio.mutual.ReplyBody;
import com.calow.cim.nio.mutual.SentBody;
import com.calow.cim.nio.session.CIMSession;

public class HeartbeatHandler implements CIMRequestHandler {

	protected final Logger logger = Logger.getLogger(HeartbeatHandler.class);

	@Override
	public ReplyBody process(CIMSession ios, SentBody message) {
		logger.warn("heartbeat... from " + ios.getRemoteAddress().toString());

		ReplyBody reply = new ReplyBody();
		reply.setKey(CIMConstant.RequestKey.CLIENT_HEARTBEAT);
		reply.setCode(CIMConstant.ReturnCode.CODE_200);
		ios.setHeartbeat(System.currentTimeMillis());
		return reply;
	}

}
