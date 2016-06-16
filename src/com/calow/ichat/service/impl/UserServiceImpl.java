package com.calow.ichat.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.common.util.MD5;
import com.calow.ichat.dao.FriendGroupDao;
import com.calow.ichat.dao.ResourceDao;
import com.calow.ichat.dao.UserDao;
import com.calow.ichat.dao.UserStateDao;
import com.calow.ichat.dao.impl.UserDaoImpl;
import com.calow.ichat.entity.Friendgroup;
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

}
