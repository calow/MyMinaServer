package com.calow.ichat.service.impl;

import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.dao.GroupDao;
import com.calow.ichat.entity.Group;
import com.calow.ichat.service.GroupService;

public class GroupServiceImpl implements GroupService {

	@Override
	public Group getGroupByGroupId(int groupId) {
		GroupDao groupDao = (GroupDao) ContextHolder.getBean("groupDao");
		return groupDao.getGroupByGroupId(groupId);
	}

}
