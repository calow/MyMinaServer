package com.calow.ichat.dao.impl;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.GroupUsersDao;
import com.calow.ichat.entity.Groupusers;

public class GroupUsersDaoImpl extends HibernateDaoSupport implements
		GroupUsersDao {

	@Override
	public int save(Groupusers gu) {
		int result = -1;
		Session session = null;
		session = this.getSession();
		result = (Integer) session.save(gu);
		return result;
	}
	
}
