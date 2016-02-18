package com.calow.ichat.api.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.calow.cim.nio.mutual.ClientMessage;
import com.calow.cim.nio.session.CIMSession;
import com.calow.ichat.cim.push.DefaultMessagePusher;
import com.calow.ichat.cim.push.SystemMessagePusher;
import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.dao.GroupDao;
import com.calow.ichat.dao.MessageSetDao;
import com.calow.ichat.dao.impl.GroupDaoImpl;
import com.calow.ichat.dao.impl.MessageDaoImpl;
import com.calow.ichat.dao.impl.MessageSetDaoImpl;
import com.calow.ichat.dao.impl.MessageTypeDaoImpl;
import com.calow.ichat.dao.impl.UserDaoImpl;
import com.calow.ichat.entity.Group;
import com.calow.ichat.entity.Groupusers;
import com.calow.ichat.entity.Message;
import com.calow.ichat.entity.Messageset;
import com.calow.ichat.entity.Messagetype;
import com.calow.ichat.entity.User;
import com.calow.ichat.service.MessageService;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class ClientMessageAction extends ActionSupport implements
		ModelDriven<ClientMessage> {

	private static final long serialVersionUID = 1L;
	ClientMessage cm = new ClientMessage();

	@Override
	public ClientMessage getModel() {
		return cm;
	}

	public void sendMessage() {
		System.out.println("content = " + cm.getContent() + "---"
				+ "groupId = " + cm.getGroupId() + "---" + "messageId = "
				+ cm.getMessageId() + "---");
		HttpServletResponse response = ServletActionContext.getResponse();
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		String account = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("user");
		MessageService ms = (MessageService) ContextHolder.getBean("messageService");
		datamap = (HashMap<String, Object>) ms.saveMessage(account, cm.getGroupId(), cm.getContent(), cm.getMessageId());
		postResult(response, datamap);
	}

	public void postResult(HttpServletResponse response,
			HashMap<String, Object> datamap) {
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(new Gson().toJson(datamap));
			pw.flush();
		} catch (IOException e) {
			datamap.put("code", 500);
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}
}
