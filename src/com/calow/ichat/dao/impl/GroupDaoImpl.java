package com.calow.ichat.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.GroupDao;
import com.calow.ichat.entity.Group;
import com.calow.ichat.entity.Groupusers;
import com.calow.ichat.entity.User;

public class GroupDaoImpl extends HibernateDaoSupport implements GroupDao {

	@Override
	public Group getGroupByGroupId(int groupId) {
		Group result = null;
		Session session = this.getSession();
		result = (Group) session.get(Group.class, groupId);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Groupusers> getGroupUsersByGroupId(int groupId) {
		List<Groupusers> result = new ArrayList<Groupusers>();
		Session session = null;
		String hql = "From Groupusers gu where gu.group.GId=:groupId";
		session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter("groupId", groupId);
		result = query.list();
		return result;
	}

	@Override
	public int save(Group g) {
		int result = -1;
		Session session = this.getSession();
		result =  (Integer) session.save(g);
		return result;
	}

}
