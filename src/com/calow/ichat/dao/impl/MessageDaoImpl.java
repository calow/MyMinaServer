package com.calow.ichat.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.MessageDao;
import com.calow.ichat.entity.Message;
import com.calow.ichat.entity.Storage;

public class MessageDaoImpl extends HibernateDaoSupport implements MessageDao {

	/**
	 * 获取用户未读消息列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getUnReadMessageList(String account) {

		JSONArray jsonArray = null;
		Session session = null;
		String sql = "SELECT m.M_ID, m.M_Content, m.M_ResourceID, m.M_CreateTime, m.M_groupID, g.G_Name, m.M_Type, u1.U_LoginID as receiver, u2.U_LoginID as sender, u2.U_NickName, ms.MS_ID, ms.MS_State, g.G_JSon "
				+ "from messageset ms INNER JOIN message m ON m.M_ID = ms.MS_MessageID "
				+ "INNER JOIN `group` g ON g.G_ID = m.M_groupID "
				+ "INNER JOIN `user` u1 ON u1.U_ID = ms.MS_ToUser "
				+ "INNER JOIN `user` u2 ON u2.U_ID = m.M_UserID "
				+ "where  MS_State = 0 AND MS_Offline = 0 AND u1.U_LoginID = ? ORDER BY m.M_CreateTime desc";
		session = this.getSession();
		jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter(0, account);
		List<Object[]> list = query.list();
		for (Object[] o : list) {
			jsonObject = new JSONObject();
			jsonObject.put("messageId", o[0]);
			if (o[1] != null) {
				jsonObject.put("messageContent", o[1]);
			} else {
				jsonObject.put("messageContent", "");
			}
			if (o[2] != null) {
				jsonObject.put("messageResourceId", o[2]);
			} else {
				jsonObject.put("messageResourceId", "");
			}
			jsonObject.put("messageCreateTime", o[3]);
			jsonObject.put("groupId", o[4]);
			jsonObject.put("groupName", o[5]);
			jsonObject.put("messageType", o[6]);
			jsonObject.put("receiverLoginId", o[7]);
			jsonObject.put("senderLoginId", o[8]);
			jsonObject.put("senderNickName", o[9]);
			jsonObject.put("messageSetId", o[10]);
			jsonObject.put("statu", o[11]);
			jsonObject.put("JSonId", o[12]);
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	@Override
	public int addGeneralMessage(Message msg) {
		Session session = null;
		int result = -1;
		session = this.getSession();
		result = (Integer) session.save(msg);
		return result;
	}

	@Override
	public int addSystemMessage(Message msg) {
		return 0;
	}

	/**
	 * 获取离线消息列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getOfflineMessage(String account) {
		JSONArray jsonArray = null;
		Session session = null;
		String sql = "select u1.U_LoginID AS senderId, u1.U_NickName AS senderName, m.M_Content, "
				+ "g.G_ID, g.G_Name, g.G_Type from messageset ms "
				+ "INNER JOIN message m ON m.M_ID = ms.MS_MessageID "
				+ "INNER JOIN `user` u1 ON u1.U_ID = m.M_UserID "
				+ "INNER JOIN `user` u2 ON u2.U_ID = ms.MS_ToUser "
				+ "INNER JOIN `group` g ON g.G_ID = m.M_groupID "
				+ "WHERE ms.MS_Offline = '0' AND u2.U_LoginID = ? "
				+ "ORDER BY m.M_CreateTime ASC;";
		session = this.getSession();
		jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter(0, account);
		List<Object[]> list = query.list();
		for (Object[] o : list) {
			jsonObject = new JSONObject();
			jsonObject.put("senderLoginId", o[0]);
			jsonObject.put("senderNickName", o[1]);
			jsonObject.put("messageContent", o[2]);
			jsonObject.put("groupId", o[3]);
			jsonObject.put("groupName", o[4]);
			jsonObject.put("groupType", o[5]);
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	/**
	 * 更新用户离线消息读取状态
	 * 
	 * @param account
	 *            用户登录ID
	 * @return 返回更新的条数
	 */
	@Override
	public int updateOfflineMessageState(String account, String messageSetId) {
		Session session = null;
		int result = 0;
		String sql = "UPDATE messageset ms "
				+ "INNER JOIN `user` u1 ON u1.U_ID = ms.MS_ToUser "
				+ "SET ms.MS_Offline = 1 "
				+ "WHERE u1.U_LoginID = ? AND ms.MS_ID = ?;";
		session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter(0, account);
		query.setParameter(1, Integer.valueOf(messageSetId));
		result = query.executeUpdate();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getGroupMessage(String account, String groupId) {
		JSONObject jsonObject = null;
		Session session = null;
		String sql = "SELECT COUNT(*) FROM messageset ms INNER JOIN message m ON m.M_ID = ms.MS_MessageID "
				+ "INNER JOIN `user` u1 ON u1.U_ID = ms.MS_ToUser WHERE ms.MS_State = 0 AND u1.U_LoginID = ? "
				+ "AND m.M_groupID = ?";
		String sql2 = "SELECT m.M_ID, ms.MS_ID, u2.U_LoginID, u2.U_NickName, u1.U_LoginID, "
				+ "m.M_Type, m.M_Content, m.M_ResourceID, m.M_CreateTime, m.M_groupID, "
				+ "g.G_Name, ms.MS_State FROM messageset ms "
				+ "INNER JOIN message m ON m.M_ID = ms.MS_MessageID "
				+ "INNER JOIN `user` u1 ON u1.U_ID = ms.MS_ToUser "
				+ "INNER JOIN `user` u2 ON u2.U_ID = m.M_UserID "
				+ "INNER JOIN `group` g ON g.G_ID = m.M_groupID "
				+ "WHERE ms.MS_State = 0 AND u1.U_LoginID = ? "
				+ "AND m.M_groupID = ?";
		session = this.getSession();
		jsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		SQLQuery query = session.createSQLQuery(sql);
		query.setParameter(0, account);
		query.setParameter(1, groupId);
		jsonObject.put("total", query.list().get(0));
		jsonObject.put("groupId", groupId);
		SQLQuery query2 = session.createSQLQuery(sql2);
		query2.setParameter(0, account);
		query2.setParameter(1, groupId);
		List<Object[]> list = query2.list();
		JSONObject object2;
		for (Object[] o : list) {
			object2 = new JSONObject();
			object2.put("messageId", o[0]);
			object2.put("messageSetId", o[1]);
			object2.put("senderId", o[2]);
			object2.put("senderName", o[3]);
			object2.put("receiverId", o[4]);
			object2.put("messageType", o[5]);
			if (o[6] != null) {
				object2.put("content", o[6]);
			} else {
				object2.put("content", "");
			}
			if (o[7] != null) {
				object2.put("resourceId", o[7]);
			} else {
				object2.put("resourceId", "");
			}
			object2.put("sendTime", o[8]);
			object2.put("groupId", o[9]);
			object2.put("groupName", o[10]);
			object2.put("statu", o[11]);
			array.add(object2);
		}
		jsonObject.put("rows", array);
		return jsonObject.toString();
	}
}
