package com.calow.ichat.dao.impl;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.FriendDao;
import com.calow.ichat.entity.Friend;

public class FriendDaoImpl extends HibernateDaoSupport implements FriendDao {

	public int save(Friend f){
		int result = -1;
		Session session = null;
		session = this.getSession();
		result = (Integer) session.save(f);
		return result;
	}
}
