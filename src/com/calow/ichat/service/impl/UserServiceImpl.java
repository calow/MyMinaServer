package com.calow.ichat.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.dao.UserDao;
import com.calow.ichat.dao.impl.UserDaoImpl;
import com.calow.ichat.entity.User;
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
		UserDao userDao = (UserDao) ContextHolder
				.getBean("userDao");
		return userDao.getGroupFriendsList(account);
	}

}
