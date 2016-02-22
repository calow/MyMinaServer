package com.calow.ichat.dao;

import com.calow.ichat.entity.Storage;

public interface StorageDao {

	public Storage getStorageById(int storageId);
	
	public int saveStorage(Storage storage);
	
	public void updateStorage(Storage storage);
}
