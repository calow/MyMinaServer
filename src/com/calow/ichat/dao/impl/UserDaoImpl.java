package com.calow.ichat.dao.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.cim.nio.mutual.Message;
import com.calow.ichat.common.util.MD5;
import com.calow.ichat.dao.UserDao;
import com.calow.ichat.entity.Friend;
import com.calow.ichat.entity.Friendgroup;
import com.calow.ichat.entity.Group;
import com.calow.ichat.entity.User;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

	public void save(Message msg) {
		this.getHibernateTemplate().save(msg);
	}

	/**
	 * 登录时，用户验证
	 */
	@SuppressWarnings("unchecked")
	public boolean checkUser(User user) {
		boolean result = false;
		String loginId = user.getULoginId();
		String password = MD5.GetMD5Code(user.getUPassWord());
		Session session = null;
		String hql = "From User u where u.ULoginId=:loginId";
		session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter("loginId", loginId);
		List<User> list = query.list();
		if (list.size() > 0 && list != null) {
			String p = list.get(0).getUPassWord();
			if (p.equals(password)) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 获取用户所有好友列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getAllFriends(String account) {
		Session session = null;
		JSONArray jsonArray = null;
		String hql = "From Friend f where f.userByFUserId.ULoginId=:account";
		session = this.getSession();
		jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		Query query = session.createQuery(hql);
		query.setParameter("account", account);
		List<Friend> list = query.list();
		if (list.size() > 0 && list != null) {
			for (Friend f : list) {
				jsonObject = new JSONObject();
				jsonObject.put("loginId", f.getUserByFFriendId().getULoginId());
				jsonObject.put("nickName", f.getUserByFFriendId()
						.getUNickName());
				jsonObject.put("remarkName", f.getFName());
				if (f.getUserByFFriendId().getUSignture() != null) {
					jsonObject.put("signture", f.getUserByFFriendId()
							.getUSignture());
				} else {
					jsonObject.put("signture", "");
				}
				jsonArray.add(jsonObject);
			}
		}
		return jsonArray.toString();
	}

	/**
	 * 按分组获取用户好友列表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getGroupFriendsList(String account) {
		Session session = null;
		JSONArray jsonArray = null;
		String groupList = "SELECT fg.FG_Name, fg.FG_ID FROM friendgroup fg "
				+ "INNER JOIN `user` u1 ON u1.U_ID = fg.FG_UserID "
				+ "WHERE u1.U_LoginID = ?";
		String friendList = "From Friend f where f.friendgroup.fgId=:groupId order by f.userByFFriendId.userstate.usValue desc";
		String groupId = "From Group g where g.GJson=:a2b or g.GJson=:b2a";
		session = this.getSession();
		jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		JSONObject object = null;
		JSONArray array = null;
		SQLQuery query1 = session.createSQLQuery(groupList);
		query1.setParameter(0, account);
		List<Object[]> list1 = query1.list();
		for (Object[] o : list1) {
			jsonObject = new JSONObject();
			jsonObject.put("groupName", o[0]);
			jsonObject.put("groupId", o[1]);
			jsonObject.put("owner", account);
			// 查询每个分组的所有成员
			Query query2 = session.createQuery(friendList);
			query2.setParameter("groupId", o[1]);
			List<Friend> list2 = query2.list();
			array = new JSONArray();
			if (list2.size() > 0 && list2 != null) {
				for (Friend f : list2) {
					object = new JSONObject();
					object.put("friedID", f.getFId());
					object.put("loginId", f.getUserByFFriendId().getULoginId());
					object.put("nickName", f.getUserByFFriendId()
							.getUNickName());
					object.put("remarkName", f.getFName());
					if (f.getUserByFFriendId().getUSignture() != null) {
						object.put("signture", f.getUserByFFriendId()
								.getUSignture());
					} else {
						object.put("signture", "");
					}
					object.put("onlineCode", f.getUserByFFriendId()
							.getUserstate().getUsValue());
					object.put("onlienValue", f.getUserByFFriendId()
							.getUserstate().getUsName());
					// 获取私聊室Id-------------
					Query query3 = session.createQuery(groupId);
					query3.setParameter("a2b", account + ":"
							+ f.getUserByFFriendId().getULoginId());
					query3.setParameter("b2a", f.getUserByFFriendId()
							.getULoginId() + ":" + account);
					List<Group> list3 = query3.list();
					object.put("chatRoomId", list3.get(0).getGId());
					// 获取私聊室Id-------------
					array.add(object);
				}
			}
			jsonObject.put("friendList", array);
			jsonArray.add(jsonObject);
		}
		return jsonArray.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByUserAccount(String account) {
		User result = null;
		Session session = null;
		String hql = "From User u where u.ULoginId=:loginId";
		session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter("loginId", account);
		List<User> list = query.list();
		if (list.size() > 0 && list != null) {
			result = list.get(0);
		}
		return result;
	}

	@Override
	public int addUser(User user) {
		int result = -1;
		Session session = null;
		session = this.getSession();
		result = (Integer) session.save(user);
		return result;
	}

	@Override
	public int getUserCount() {
		int result = -1;
		Session session = null;
		String sql = "SELECT COUNT(*) FROM `user`";
		session = this.getSession();
		SQLQuery query = session.createSQLQuery(sql);
		result = ((Number) query.uniqueResult()).intValue();
		return result;
	}

	@Override
	public User getUserById(int userId) {
		User result = null;
		Session session = this.getSession();
		result = (User) session.get(User.class, userId);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String searchUser(String searchValue, String loginId) {
		Session session = null;
		JSONObject object = null;
		JSONArray array = new JSONArray();
		String sql = "SELECT u2.U_ID, u2.U_LoginID, u2.U_NickName,u2.U_Signture, ust.US_Name FROM `user` u2 INNER JOIN "
				+ "(SELECT * FROM "
				+ "(SELECT u.U_ID FROM `user` u WHERE (u.U_LoginID=:searchValue1 OR u.U_NickName LIKE :searchValue2) AND u.U_LoginID!=:loginId1) A "
				+ "WHERE A.U_ID NOT IN "
				+ "(SELECT f.F_FriendID FROM friend f INNER JOIN `user` u1 ON f.F_UserID = u1.U_ID WHERE u1.U_LoginID=:loginId2)) B "
				+ "ON u2.U_ID = B.U_ID "
				+ "INNER JOIN userstate ust ON u2.U_UserState = ust.US_ID";
//		String hql = "From User u where u.ULoginId=:loginId OR u.UNickName LIKE :userName";
		session = this.getSession();
//		Query query = session.createQuery(hql);
//		query.setParameter("loginId", searchValue);
//		query.setParameter("userName", "%" + searchValue + "%");
//		List<User> list = query.list();
//		if (list.size() > 0 && list != null) {
//			for (User u : list) {
//				object = new JSONObject();
//				object.put("UID", u.getUId());
//				object.put("ULoginId", u.getULoginId());
//				object.put("UNickName", u.getUNickName());
//				if (u.getUSignture() == null) {
//					object.put("USignture", "");
//				} else {
//					object.put("USignture", u.getUSignture());
//				}
//				object.put("UState", u.getUserstate().getUsName());
//				array.add(object);
//			}
//		}
		SQLQuery query1 = session.createSQLQuery(sql);
		query1.setParameter("searchValue1", searchValue);
		query1.setParameter("searchValue2", "%" + searchValue + "%");
		query1.setParameter("loginId1", loginId);
		query1.setParameter("loginId2", loginId);
		List<Object[]> list1 = query1.list();
		for (Object[] o : list1) {
			object = new JSONObject();
			object.put("UID", o[0]);
			object.put("ULoginId", o[1]);
			object.put("UNickName", o[2]);
			if(o[3] != null){
				object.put("USignture", o[3]);
			}else{
				object.put("USignture", "");
			}
			object.put("UState", o[4]);
			array.add(object);
		}
		return array.toString();
	}

	@Override
	public String addFriendAbs(String loginId, String loginId2) {
		return null;
	}

}
