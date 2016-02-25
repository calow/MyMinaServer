package com.calow.ichat.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.ToolDao;
import com.calow.ichat.entity.Tool;

public class ToolDaoImpl extends HibernateDaoSupport implements ToolDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Tool> getToolList() {
		String hql = "From Tool t order by t.TId DESC";
		List<Tool> list = new ArrayList<Tool>();
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tool> getToolListByLimit(int start, int count) {
		String hql = "From Tool t order by t.TId DESC";
		List<Tool> list = new ArrayList<Tool>();
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.setFirstResult(start);
		query.setMaxResults(count);
		list = query.list();
		return list;
	}

	@Override
	public int saveToolMessage(Tool tool) {
		Session session = this.getSession();
		return (Integer) session.save(tool);
	}

	@Override
	public Tool getToolByToolId(int toolId) {
		Session session = this.getSession();
		return (Tool) session.get(Tool.class, toolId);
	}

}
