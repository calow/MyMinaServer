package com.calow.ichat.dao.impl;

import org.hibernate.Query;
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

	@Override
	public Friendgroup searchFriendGroupByUserIdAndGroupName(String userId,
			String groupName) {
		Session session = null;
		String hql = "From Friendgroup fg where fg.fgName=:groupName AND fg.user.ULoginId=:userId";
		session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter("groupName", groupName);
		query.setParameter("userId", userId);
		return (Friendgroup) query.list().get(0);
	}

}
