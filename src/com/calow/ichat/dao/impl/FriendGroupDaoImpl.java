package com.calow.ichat.dao.impl;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.FriendGroupDao;
import com.calow.ichat.entity.Friendgroup;

public class FriendGroupDaoImpl extends HibernateDaoSupport implements
		FriendGroupDao {

	@Override
	public int addFriendGroup(Friendgroup fg) {
		Session session = this.getSession();
		return (Integer) session.save(fg);
	}

}
