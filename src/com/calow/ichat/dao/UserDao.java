package com.calow.ichat.dao;

import com.calow.ichat.entity.User;

public interface UserDao {

	public boolean checkUser(User user);
	
	public String getAllFriends(String account);
	
	public String getGroupFriendsList(String account);
	
	public User getUserByUserAccount(String account);
	
	public int addUser(User user);
	
	public int getUserCount();
	
	public User getUserById(int userId);
	
	public String searchUser(String searchValue, String loginId);
	
	public String addFriendAbs(String loginId1, String loginId2);
}
