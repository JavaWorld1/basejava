package com.webapp.storage;

import com.webapp.exception.ExistStorageException;
import com.webapp.exception.NotExistStorageException;
import com.webapp.exception.StorageException;
import com.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractArrayStorageTest {
    Storage storage;

    Resume r1 = new Resume("uuid0");
    Resume r2 = new Resume("uuid1");
    Resume r3 = new Resume("uuid2");
    Resume r4 = new Resume("uuid3");

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @Test
    void update() {
        Resume testResume = new Resume("uuid0");
        Resume testNotExistResume = new Resume("uuid5");
        storage.update(testResume);
        assertSame(testResume, storage.get(r1.getUuid()));
        assertThrows(NotExistStorageException.class, () -> storage.update(testNotExistResume));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void save() {
        storage.save(r4);
        assertThrows(ExistStorageException.class, () -> storage.save(r1));
        for (int i = 4; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        try {
            storage.save(new Resume());
        } catch (StorageException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete() {
        storage.delete(r1.getUuid());
        assertEquals(2, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.delete(r1.getUuid()));
        assertThrows(NotExistStorageException.class, () -> storage.delete("notExistUuid"));
    }

    @Test
    void get() {
        assertEquals(r1, storage.get(r1.getUuid()));
        assertThrows(NotExistStorageException.class, () -> storage.get("notExistUuid"));
    }

    @Test
    void getAll() {
        Resume[] storageTest = storage.getAll();
        assertEquals(3, storageTest.length);
        Resume[] expectedResults = {r1, r2, r3};
        for (int i = 0; i < expectedResults.length; i++) {
            assertSame(expectedResults[i],storageTest[i]);
        }
    }

    @Test
    void size() {
        assertEquals(3, storage.size());
    }
}