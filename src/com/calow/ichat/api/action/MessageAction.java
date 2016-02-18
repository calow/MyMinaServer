package com.calow.ichat.api.action;

import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.bind.ServletRequestBindingException;

import com.calow.cim.nio.mutual.Message;
import com.calow.ichat.cim.push.DefaultMessagePusher;
import com.calow.ichat.cim.push.SystemMessagePusher;
import com.calow.ichat.common.util.Constants;
import com.calow.ichat.common.util.ContextHolder;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class MessageAction extends ActionSupport implements
		ModelDriven<Message> {

	private static final long serialVersionUID = 1L;
	Message message = new Message();

	/**
	 * 关于http参数获取， struts2 的模型驱动 比如 http 参数 sender=xiaomao&receiver=xiaogou
	 * struts自动会将参数的值
	 * 存入getModel()返回的对象的对应属性中，即xiaomao会存入message.sender属性,xiaogou会存入message
	 * .receiver属性
	 * 省去了request.getParameter("sender")方式获取参数，，如果参数名在getModel()返回的对象中不存在
	 * ，则需要用request.getParameter()获取 其他相关*Action.java中 同理，这里做统一说明!
	 * 
	 * @throws IOException
	 */
	public String send() throws IOException {
		ServletActionContext.getResponse().setContentType(
				"text/json;charset=UTF-8");
		HashMap<String, String> data = new HashMap<String, String>();
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("code", 200);
		try {
			checkParams();
			if (Constants.MessageType.TYPE_2.equals(message.getType())) {
				// 向客户端发送系统消息
				ContextHolder.getBean(SystemMessagePusher.class)
						.pushMessageToUser(message);
			} else {
				// 向客户端发送用户消息
				ContextHolder.getBean(DefaultMessagePusher.class)
						.pushMessageToUser(message);
			}
			data.put("id", message.getMid());
			data.put("createTime", String.valueOf(message.getTimestamp()));
			datamap.put("data", data);
		} catch (Exception e) {
			datamap.put("code", 500);
			e.printStackTrace();
		}
		ServletActionContext.getResponse().getWriter()
				.write(new Gson().toJson(datamap));
		return null;
	}

	private void checkParams() throws ServletRequestBindingException {
		if (StringUtils.isEmpty(message.getReceiver())) {
			throw new IllegalArgumentException("receiver 不能为空!");
		}
	}

	@Override
	public Message getModel() {
		return message;
	}

}
