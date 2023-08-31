package com.webapp.storage;

import com.webapp.exception.ExistStorageException;
import com.webapp.exception.NotExistStorageException;
import com.webapp.exception.StorageException;
import com.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractArrayStorageTest {
    private final Storage storage;

    private final static Resume R_5 = new Resume();

    private final Resume r1 = new Resume("uuid0");
    private final Resume r2 = new Resume("uuid1");
    private final Resume r3 = new Resume("uuid2");
    private final Resume r4 = new Resume("uuid3");

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
        Resume existResume = new Resume("uuid0");
        storage.update(existResume);
        assertSame(existResume, storage.get(r1.getUuid()));
    }

    @Test
    void updateNotExist() {
        Resume notExistResume = new Resume("notExistUuid");
        assertThrows(NotExistStorageException.class, () -> storage.update(notExistResume));
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertArrayEquals(new Resume[0], storage.getAll());
    }

    @Test
    void save() {
        storage.save(r4);
        assertSize(4);
        assertGet(r4);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(r1));
    }

    @Test
    void saveOverflow() {
        for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        try {
            storage.save(R_5);
        } catch (StorageException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete() {
        storage.delete(r1.getUuid());
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(r1.getUuid()));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("notExistUuid"));
    }

    @Test
    void get() {
        assertGet(r1);
        assertGet(r2);
        assertGet(r3);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("notExistUuid"));
    }

    @Test
    void getAll() {
        Resume[] actual = storage.getAll();
        assertEquals(3, actual.length);
        Resume[] expected = {r1, r2, r3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}