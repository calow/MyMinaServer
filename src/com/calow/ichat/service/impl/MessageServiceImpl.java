package com.calow.ichat.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.calow.cim.nio.session.CIMSession;
import com.calow.cim.nio.session.DefaultSessionManager;
import com.calow.ichat.cim.push.DefaultMessagePusher;
import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.dao.GroupDao;
import com.calow.ichat.dao.MessageDao;
import com.calow.ichat.dao.MessageSetDao;
import com.calow.ichat.dao.MessageTypeDao;
import com.calow.ichat.dao.UserDao;
import com.calow.ichat.entity.Group;
import com.calow.ichat.entity.Groupusers;
import com.calow.ichat.entity.Message;
import com.calow.ichat.entity.Messageset;
import com.calow.ichat.entity.Messagetype;
import com.calow.ichat.entity.User;
import com.calow.ichat.service.MessageService;

public class MessageServiceImpl implements MessageService {

	@Override
	public String getGroupMessage(String account, String groupId) {
		MessageDao messageDao = (MessageDao) ContextHolder
				.getBean("messageDao");
		return messageDao.getGroupMessage(account, groupId);
	}

	@Override
	public String getOfflineMessage(String account) {
		MessageDao messageDao = (MessageDao) ContextHolder
				.getBean("messageDao");
		return messageDao.getOfflineMessage(account);
	}

	@Override
	public String getUnReadMessageList(String account) {
		MessageDao messageDao = (MessageDao) ContextHolder
				.getBean("messageDao");
		return messageDao.getUnReadMessageList(account);
	}

	@Override
	public int updateOfflineMessageState(String account, JSONArray array) {
		MessageDao messageDao = (MessageDao) ContextHolder
				.getBean("messageDao");
		int result = 0;
		try {
			for(int i = 0; i < array.size(); i++){
				messageDao.updateOfflineMessageState(account, array.getString(i));
			}
			result = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Map<String, Object> saveMessage(String account, String groupId,
			String content, String client_messageId) {
		UserDao ud = (UserDao) ContextHolder.getBean("userDao");
		GroupDao gd = (GroupDao) ContextHolder.getBean("groupDao");
		MessageTypeDao mtd = (MessageTypeDao) ContextHolder
				.getBean("messageTypeDao");
		MessageDao md = (MessageDao) ContextHolder.getBean("messageDao");
		MessageSetDao msd = (MessageSetDao) ContextHolder
				.getBean("messageSetDao");
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		HashMap<String, String> data = new HashMap<String, String>();

		Message message = new Message();
		// 获取消息发送用户对象
		User user = ud.getUserByUserAccount(account);
		if (user != null) {
			message.setUser(user);
		}
		// 获取消息发送聊天室对象
		Group group = gd.getGroupByGroupId(Integer.parseInt(groupId));
		short groupType = 0;
		if (group != null) {
			message.setGroup(group);
			groupType = group.getGType();
		}
		// 获取消息类型对象
		Messagetype mt = null;
		if (groupType == 0) {
			mt = mtd.getMessageTypeByTypeValue((short) 1);
		} else if (groupType == 1) {
			mt = mtd.getMessageTypeByTypeValue((short) 2);
		}
		if (mt != null) {
			message.setMessagetype(mt);
		}
		// 设置消息内容
		message.setMContent(content);
		// 设置消息发送时间
		long time = System.currentTimeMillis();
		message.setMCreateTime(new Timestamp(time));
		// 添加一条message表记录
		int newMessageId = md.addGeneralMessage(message);
		if (newMessageId > 0) {
			List<Groupusers> list = gd.getGroupUsersByGroupId(Integer
					.parseInt(groupId));
			if (list != null) {
				short t1 = 1;
				short t0 = 0;
				for (Groupusers gu : list) {
					// 添加一条messageset记录
					Messageset ms = new Messageset();
					User u = gu.getUser();
					ms.setUser(u);
					ms.setMessage(message);
					if (u.getULoginId().equals(account)) {
						ms.setMsState(t1);
						ms.setMsOffline(t1);
					} else {
						ms.setMsState(t0);
						ms.setMsOffline(t0);
					}
					int messageSetId = msd.addMessageSet(ms);
					if (user.getULoginId().equals(account)) {
						if (messageSetId > 0) {
							datamap.put("code", 200);
							data.put("messageId", String.valueOf(newMessageId));
							data.put("messageSet", String.valueOf(messageSetId));
							data.put("client_messageId", client_messageId);
						} else {
							datamap.put("code", 500);
							data.put("messageId", "-2");
							data.put("messageSet", "-2");
							data.put("client_messageId", client_messageId);
						}
					}
				}
				if (datamap.get("code").toString().equals("200")) {
					loadMessageToOthers(Integer.parseInt(groupId),
							newMessageId, account);
				}
			}
		} else {
			datamap.put("code", 500);
			data.put("messageId", "-2");
			data.put("messageSet", "-2");
			data.put("client_messageId", client_messageId);
		}
		datamap.put("data", data);
		return datamap;
	}

	@Override
	public boolean updataMessageSet(Messageset messageset) {

		return false;
	}

	@Override
	public List<Messageset> getMessageSetListByMessageId(int messageId) {
		MessageSetDao msd = (MessageSetDao) ContextHolder
				.getBean("messageSetDao");
		return msd.getMessageSetByMessageId(messageId);
	}

	/**
	 * 推送消息给互动室中的其他用户
	 * @param groupId
	 * @param messageId
	 * @param account
	 */
	public void loadMessageToOthers(final int groupId, final int messageId,
			final String account) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				List<Messageset> list = getMessageSetListByMessageId(messageId);
				if (list != null && list.size() > 0) {
					for (Messageset messageSet : list) {
						com.calow.cim.nio.mutual.Message msg = new com.calow.cim.nio.mutual.Message();
						String loginId = messageSet.getUser().getULoginId();
						if (!loginId.equals(account)) {
							CIMSession session = ((DefaultSessionManager) ContextHolder
									.getBean("defaultSessionManager"))
									.getSession(loginId);
							if (session != null) {
								msg.setGroupId(String.valueOf(messageSet
										.getMessage().getGroup().getGId()));
								msg.setGroupName(messageSet.getMessage()
										.getGroup().getGName());
								msg.setSender(messageSet.getMessage().getUser()
										.getULoginId());
								msg.setSenderName(messageSet.getMessage()
										.getUser().getUNickName());
								msg.setReceiver(messageSet.getUser()
										.getULoginId());
								msg.setType(String.valueOf(messageSet
										.getMessage().getMessagetype()));
								msg.setContent(messageSet.getMessage()
										.getMContent());
								if(messageSet
										.getMessage().getResource() != null){
									msg.setFile(String.valueOf(messageSet
											.getMessage().getResource().getRId()));
								}
								msg.setTimestamp(messageSet
										.getMessage().getMCreateTime().getTime());
								msg.setMid(String.valueOf(messageId));
								msg.setMessageSetId(String.valueOf(messageSet
										.getMsId()));
								session.write(msg);
							}
						}
					}
				}
			}
		}).start();
	}
}
