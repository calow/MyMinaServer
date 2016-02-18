package com.calow.ichat.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.calow.ichat.entity.User;

public interface UserService {
	
	public Map<String, Object> getValidation(User user, HttpServletRequest request);
	
	public String getGroupFriendsList(String account);

}
