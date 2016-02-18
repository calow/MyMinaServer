package com.calow.ichat.dao;

import java.util.List;

import com.calow.ichat.entity.Group;
import com.calow.ichat.entity.Groupusers;
import com.calow.ichat.entity.User;

public interface GroupDao {

	public Group getGroupByGroupId(int groupId);

	public List<Groupusers> getGroupUsersByGroupId(int groupId);
}
