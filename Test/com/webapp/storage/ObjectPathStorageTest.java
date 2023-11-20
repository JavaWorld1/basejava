package com.webapp.storage;

import com.webapp.storage.serializer.ObjectStreamSerializerStrategy;

public class ObjectPathStorageTest extends AbstractStorageTest {

    protected ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializerStrategy()));
    }
}