package com.calow.ichat.dao;

import com.calow.ichat.entity.Friendgroup;

public interface FriendGroupDao {
	public int addFriendGroup(Friendgroup fg);
	
	public Friendgroup searchFriendGroupByUserIdAndGroupName(String userId, String groupName);
}
