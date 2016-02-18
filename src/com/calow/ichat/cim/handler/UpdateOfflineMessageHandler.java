package com.calow.ichat.cim.handler;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.calow.cim.nio.constant.CIMConstant;
import com.calow.cim.nio.handler.CIMRequestHandler;
import com.calow.cim.nio.mutual.ReplyBody;
import com.calow.cim.nio.mutual.SentBody;
import com.calow.cim.nio.session.CIMSession;
import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.dao.impl.MessageDaoImpl;
import com.calow.ichat.service.MessageService;

public class UpdateOfflineMessageHandler implements CIMRequestHandler {

	protected final Logger logger = Logger
			.getLogger(UpdateOfflineMessageHandler.class);

	@Override
	public ReplyBody process(CIMSession ios, SentBody message) {
		ReplyBody reply = new ReplyBody();
		String account = ios.getAccount();
		String messageSetList = message.get("list");
		JSONArray array = JSONArray.fromObject(messageSetList);
		reply.setCode(CIMConstant.ReturnCode.CODE_200);
		try {
			MessageService messageService = (MessageService) ContextHolder
					.getBean("messageService");
			reply.setMessage(String.valueOf(messageService
					.updateOfflineMessageState(account, array)));
		} catch (Exception e) {
			reply.setCode(CIMConstant.ReturnCode.CODE_500);
			e.printStackTrace();
			logger.error("更新离线消息状态失败", e);
		}
		return reply;
	}

}
