package com.calow.ichat.service.impl;

import com.calow.ichat.common.util.ContextHolder;
import com.calow.ichat.dao.StorageDao;
import com.calow.ichat.entity.Storage;
import com.calow.ichat.service.StorageService;

public class StorageServiceImpl implements StorageService {

	@Override
	public void updateStorage(Storage storage) {
		StorageDao storageDao = (StorageDao) ContextHolder
				.getBean("storageDao");
		storageDao.updateStorage(storage);
	}

}
