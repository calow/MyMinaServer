package com.calow.ichat.dao.impl;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.UserStateDao;
import com.calow.ichat.entity.Userstate;

public class UserStateDaoImpl extends HibernateDaoSupport implements
		UserStateDao {

	@Override
	public Userstate getUserstateById(int userStateId) {
		Session session = this.getSession();
		return (Userstate) session.get(Userstate.class, userStateId);
	}

}
