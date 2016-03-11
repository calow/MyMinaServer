package com.calow.ichat.api.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.calow.cim.nio.mutual.ClientMessage;
import com.calow.ichat.common.util.ContextHolder;
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
		HttpServletResponse response = ServletActionContext.getResponse();
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		String account = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("user");
		MessageService ms = (MessageService) ContextHolder.getBean("messageService");
		String gorupId = cm.getGroupId();
		String content = cm.getContent();
		String messageId = cm.getMessageId();
		datamap = (HashMap<String, Object>) ms.saveMessage(account, gorupId, content, messageId);
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
