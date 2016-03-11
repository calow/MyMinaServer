package com.calow.ichat.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.ToolDao;
import com.calow.ichat.entity.Tool;

public class ToolDaoImpl extends HibernateDaoSupport implements ToolDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Tool> getToolList() {
		String hql = "From Tool t Where t.TState=:state order by t.TId DESC";
		List<Tool> list = new ArrayList<Tool>();
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		Short state = 1;
		query.setParameter("state", state);
		list = query.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tool> getToolListByLimit(int start, int count) {
		String hql = "From Tool t Where t.TState=:state order by t.TId DESC";
		List<Tool> list = new ArrayList<Tool>();
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter("state", 1);
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

	@Override
	public void updateToolMessage(Tool tool) {
		Session session = this.getSession();
		session.update(tool);
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getPCToolMessageAndTvIdList() {
		Session session = this.getSession();
		String sql = "SELECT t.T_ID, t.T_Name, t.T_Platform, t.T_Description, tv.TV_ID FROM toolversion tv "
				+ "LEFT JOIN tool t ON tv.TV_ToolID = t.T_ID WHERE t.T_Platform = 0 AND t.T_State = 1 AND tv.TV_State = 1 "
				+ "GROUP BY tv.TV_ToolID ORDER BY tv.TV_ID DESC";
		SQLQuery query = session.createSQLQuery(sql);
		List<Object[]> list = query.list();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		for (Object[] o : list) {
			jsonObject = new JSONObject();
			jsonObject.put("toolId", o[0]);
			jsonObject.put("toolName", o[1]);
			jsonObject.put("platform", o[2]);
			if (o[3] != null) {
				jsonObject.put("description", o[3]);
			} else {
				jsonObject.put("description", "");
			}
			jsonObject.put("tvId", o[4]);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray getPhoneToolMessageAndTvIdList() {
		Session session = this.getSession();
		String sql = "SELECT t.T_ID, t.T_Name, t.T_Platform, t.T_Description, tv.TV_ID FROM toolversion tv LEFT JOIN tool t ON tv.TV_ToolID = t.T_ID " +
				"WHERE t.T_Platform = 1 AND t.T_State = 1 AND tv.TV_State = 1 " +
				"OR t.T_Platform = 2 AND t.T_State = 1 AND tv.TV_State = 1 " +
				"GROUP BY tv.TV_ToolID ORDER BY tv.TV_ID DESC";
		SQLQuery query = session.createSQLQuery(sql);
		List<Object[]> list = query.list();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		for (Object[] o : list) {
			jsonObject = new JSONObject();
			jsonObject.put("toolId", o[0]);
			jsonObject.put("toolName", o[1]);
			jsonObject.put("platform", o[2]);
			if (o[3] != null) {
				jsonObject.put("description", o[3]);
			} else {
				jsonObject.put("description", "");
			}
			jsonObject.put("tvId", o[4]);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	@Override
	public Connection getSqlConnection() {
		Connection connection = null;
		try {
			connection = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
