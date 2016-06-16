package com.calow.ichat.dao.impl;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.ResourceDao;
import com.calow.ichat.entity.Resource;

public class ResourceDaoImpl extends HibernateDaoSupport implements ResourceDao {

	@Override
	public Resource getResourceById(int resourceId) {
		Session session = this.getSession();
		return (Resource) session.get(Resource.class, resourceId);
	}

}
