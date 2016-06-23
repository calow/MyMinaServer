package com.calow.ichat.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.common.util.MD5;
import com.calow.ichat.dao.FriendDao;
import com.calow.ichat.dao.FriendGroupDao;
import com.calow.ichat.dao.GroupDao;
import com.calow.ichat.dao.GroupUsersDao;
import com.calow.ichat.dao.ResourceDao;
import com.calow.ichat.dao.UserDao;
import com.calow.ichat.dao.UserStateDao;
import com.calow.ichat.dao.impl.UserDaoImpl;
import com.calow.ichat.entity.Friend;
import com.calow.ichat.entity.Friendgroup;
import com.calow.ichat.entity.Group;
import com.calow.ichat.entity.Groupusers;
import com.calow.ichat.entity.Resource;
import com.calow.ichat.entity.User;
import com.calow.ichat.entity.Userstate;
import com.calow.ichat.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public Map<String, Object> getValidation(User user,
			HttpServletRequest request) {
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		HashMap<String, String> data = new HashMap<String, String>();
		UserDao ud = (UserDao) ContextHolder.getBean("userDao");
		if (ud.checkUser(user)) {
			user = ud.getUserByUserAccount(user.getULoginId());
			if (user != null) {
				request.getSession().setAttribute("user", user.getULoginId());
				datamap.put("userName", user.getUNickName());
			}
			datamap.put("code", 200);
			data.put("createTime", String.valueOf(System.currentTimeMillis()));
			data.put("userName", user.getUNickName());
			datamap.put("data", data);
		} else {
			datamap.put("code", 201);
			data.put("createTime", String.valueOf(System.currentTimeMillis()));
			datamap.put("data", data);
		}
		return datamap;
	}

	@Override
	public String getGroupFriendsList(String account) {
		UserDao userDao = (UserDao) ContextHolder.getBean("userDao");
		return userDao.getGroupFriendsList(account);
	}

	@Override
	public User getUserByUserId(String userId) {
		UserDao userDao = (UserDao) ContextHolder.getBean("userDao");
		return userDao.getUserByUserAccount(userId);
	}

	public Map<String, Object> saveRegisterInfo(User user,
			HttpServletRequest request) {
		if (user == null) {
			try {
				throw new Exception("user为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		UserDao ud = (UserDao) ContextHolder.getBean("userDao");
		ResourceDao rd = (ResourceDao) ContextHolder.getBean("resourceDao");
		UserStateDao usd = (UserStateDao) ContextHolder.getBean("userstateDao");
		FriendGroupDao fgd = (FriendGroupDao) ContextHolder
				.getBean("friendgroupDao");
		User u = new User();
		int count = ud.getUserCount() + 10001;
		u.setULoginId(count + "");
		u.setUNickName(user.getUNickName());
		u.setUPassWord(MD5.GetMD5Code(user.getUPassWord()));
		u.setResource(rd.getResourceById(1));
		u.setUserstate(usd.getUserstateById(2));
		int userId = ud.addUser(u);
		Friendgroup fg = new Friendgroup();
		fg.setFgName("我的好友");
		fg.setUser(ud.getUserById(userId));
		fgd.addFriendGroup(fg);
		datamap.put("code", 200);
		datamap.put("loginId", count + "");
		return datamap;
	}

	@Override
	public Map<String, Object> searchUser(User user,
			HttpServletRequest request) {
		if (user == null) {
			try {
				throw new Exception("user为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		UserDao ud = (UserDao) ContextHolder.getBean("userDao");
		String result = ud.searchUser(user.getULoginId(), user.getUNickName());
		datamap.put("code", 200);
		datamap.put("result", result);
		return datamap;
	}

	@Override
	public Map<String, Object> addFriendAbs(User user,
			HttpServletRequest request) {
		if (user == null) {
			try {
				throw new Exception("user为空");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		HashMap<String, Object> datamap = new HashMap<String, Object>();
		UserDao ud = (UserDao) ContextHolder.getBean("userDao");
		FriendGroupDao fgd = (FriendGroupDao) ContextHolder.getBean("friendgroupDao");
		FriendDao fd = (FriendDao) ContextHolder.getBean("friendDao");
		ResourceDao rd = (ResourceDao) ContextHolder.getBean("resourceDao");
		GroupDao gd = (GroupDao) ContextHolder.getBean("groupDao");
		GroupUsersDao gud = (GroupUsersDao) ContextHolder.getBean("groupUsersDao");
		short groupType = 0;
		String loginId = user.getULoginId();
		String[] loginIdArray = loginId.split(":");
		//1.获取用户1对象
		User user1 = ud.getUserByUserAccount(loginIdArray[0]);
		//2.获取用户2对象
		User user2 = ud.getUserByUserAccount(loginIdArray[1]);
		//3.查找用户1的“我的好友”分组
		Friendgroup fg1 = fgd.searchFriendGroupByUserIdAndGroupName(loginIdArray[0], "我的好友");
		//4.为用户1添加一条与用户2之间的好友记录
		Friend f1 = new Friend();
		f1.setFriendgroup(fg1);
		f1.setFName(user2.getUNickName());
		f1.setUserByFUserId(user1);
		f1.setUserByFFriendId(user2);
		fd.save(f1);
		//5.查找用户2的“我的好友”分组
		Friendgroup fg2 = fgd.searchFriendGroupByUserIdAndGroupName(loginIdArray[1], "我的好友");
		//6.为用户2添加一条与用户1之间的好友记录
		Friend f2 = new Friend();
		f2.setFriendgroup(fg2);
		f2.setFName(user1.getUNickName());
		f2.setUserByFUserId(user2);
		f2.setUserByFFriendId(user1);
		fd.save(f2);
		//7.创建用户1与用户2之间的私聊室
		Group g = new Group();
		g.setGName(user1.getUNickName() + ":" + user2.getUNickName());
		g.setUser(user1);
		Timestamp ts = new Timestamp(new Date().getTime());
		g.setGCreateTime(ts);
		g.setGType(groupType);
		g.setGJson(user1.getULoginId() + ":" + user2.getULoginId());
		g.setResource(rd.getResourceById(1));
		int groupId = gd.save(g);
		//8.在私聊室用户表添加两个用户记录
		Group group = gd.getGroupByGroupId(groupId);
		Groupusers gu1 = new Groupusers();
		gu1.setGroup(group);
		gu1.setGuCreateTime(ts);
		gu1.setUser(user1);
		gud.save(gu1);
		Groupusers gu2 = new Groupusers();
		gu2.setGroup(group);
		gu2.setGuCreateTime(ts);
		gu2.setUser(user2);
		gud.save(gu2);
		datamap.put("code", 200);
		datamap.put("result", groupId);
		return datamap;
	}

}
