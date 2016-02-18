package com.calow.ichat.cim.handler;

import org.apache.log4j.Logger;

import com.calow.cim.nio.constant.CIMConstant;
import com.calow.cim.nio.handler.CIMRequestHandler;
import com.calow.cim.nio.mutual.ReplyBody;
import com.calow.cim.nio.mutual.SentBody;
import com.calow.cim.nio.session.CIMSession;
import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.dao.impl.MessageDaoImpl;
import com.calow.ichat.dao.impl.UserDaoImpl;
import com.calow.ichat.service.UserService;

public class OnlineFriendsHandler implements CIMRequestHandler {

	protected final Logger logger = Logger
			.getLogger(OnlineFriendsHandler.class);
	
	@Override
	public ReplyBody process(CIMSession ios, SentBody message) {
		ReplyBody reply = new ReplyBody();
		String account = ios.getAccount();
		reply.setCode(CIMConstant.ReturnCode.CODE_200);
		try {
			UserService userService = (UserService) ContextHolder
					.getBean("userService");
			reply.setMessage(userService.getGroupFriendsList(account));
		} catch (Exception e) {
			reply.setCode(CIMConstant.ReturnCode.CODE_500);
			e.printStackTrace();
			logger.error("拉取好友列表失败", e);
		}
		return reply;
	}

}
