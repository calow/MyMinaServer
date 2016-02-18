package com.calow.ichat.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.MessageTypeDao;
import com.calow.ichat.entity.Group;
import com.calow.ichat.entity.Messagetype;

public class MessageTypeDaoImpl extends HibernateDaoSupport implements
		MessageTypeDao {

	@SuppressWarnings("unchecked")
	@Override
	public Messagetype getMessageTypeByTypeValue(short value) {
		Messagetype result = null;
		Session session = null;
		String hql = "From Messagetype mt where mt.mtValue=:value";
		session = this.getSession();
		Query query = session.createQuery(hql);
		query.setParameter("value", value);
		List<Messagetype> list = query.list();
		if (list.size() > 0 && list != null) {
			result = list.get(0);
		}
		return result;
	}

}
