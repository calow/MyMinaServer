package com.calow.ichat.dao.impl;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.calow.ichat.dao.StorageDao;
import com.calow.ichat.entity.Storage;

public class StorageDaoImpl extends HibernateDaoSupport implements StorageDao {

	@Override
	public Storage getStorageById(int storageId) {
		return null;
	}

	@Override
	public int saveStorage(Storage storage) {
		int result = -1;
		Session session = this.getSession();
		result = (Integer) session.save(storage);
		return result;
	}

	@Override
	public void updateStorage(Storage storage) {
		Session session = null;
		session = this.getSession();
		session.update(storage);
	}

}
