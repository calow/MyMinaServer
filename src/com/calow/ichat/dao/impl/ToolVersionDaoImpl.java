package com.calow.ichat.dao.impl;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.ToolVersionDao;
import com.calow.ichat.entity.Toolversion;

public class ToolVersionDaoImpl extends HibernateDaoSupport implements
		ToolVersionDao {

	@Override
	public int saveToolVersion(Toolversion tv) {
		Session session = this.getSession();
		return (Integer) session.save(tv);
	}

}
