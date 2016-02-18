package com.calow.ichat.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.MessageSetDao;
import com.calow.ichat.entity.Messageset;

public class MessageSetDaoImpl extends HibernateDaoSupport implements
		MessageSetDao {

	@Override
	public int addMessageSet(Messageset messageset) {
		int result = -1;
		Session session = null;
		session = this.getSession();
		result = (Integer) session.save(messageset);
		return result;
	}

	@Override
	public void updateMessageSet(Messageset messageset) {
		Session session = null;
		session = this.getSession();
		session.update(messageset);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Messageset> getMessageSetByMessageId(int messageId) {
		List<Messageset> result = new ArrayList<Messageset>();
		Session session = this.getSession();
		String hql = "From Messageset ms where ms.message.MId=:messageId";
		Query query = session.createQuery(hql);
		query.setParameter("messageId", messageId);
		result = query.list();
		return result;
	}

}
