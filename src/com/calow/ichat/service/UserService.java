package com.calow.ichat.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.calow.ichat.entity.User;

public interface UserService {
	
	public Map<String, Object> getValidation(User user, HttpServletRequest request);
	
	public String getGroupFriendsList(String account);

	public User getUserByUserId(String userId);
	
	public Map<String, Object> saveRegisterInfo(User user,
			HttpServletRequest request);
	
	public Map<String, Object> searchUser(User user,
			HttpServletRequest request);
	
	public Map<String, Object> addFriendAbs(User user,
			HttpServletRequest request);
}
