package com.calow.ichat.dao.impl;

import java.util.List;

import org.hibernate.Query;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Toolversion> getToolVersionsByToolId(int toolId) {
		String hql = "From Toolversion tv Where tv.tool.TId=:toolId order by tv.tvId DESC";
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter("toolId", toolId);
		return query.list();
	}

	@Override
	public Toolversion getToolVersionByToolId(int toolId) {
		String hql = "From Toolversion tv where tv.tool.TId=:toolId order by tv.tvId DESC";
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter("toolId", toolId);
		Toolversion tv = (Toolversion) query.list().get(0);
		return tv;
	}

	@Override
	public Toolversion getToolVersionByTvId(int tvId) {
		Session session = this.getSession();
		return (Toolversion) session.get(Toolversion.class, tvId);
	}

	@Override
	public void updateToolVersion(Toolversion tv) {
		Session session = this.getSession();
		session.update(tv);
	}

}
