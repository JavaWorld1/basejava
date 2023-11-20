package com.webapp.storage;

import com.webapp.storage.serializer.ObjectStreamSerializerStrategy;

public class ObjectFileStorageTest extends AbstractStorageTest {

    protected ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializerStrategy()));
    }
}