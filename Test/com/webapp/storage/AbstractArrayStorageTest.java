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

    private final static String UUID_1 = "uuid1";
    private final static Resume RESUME_1 = new Resume(UUID_1);
    private final static String UUID_2 = "uuid2";
    private final static Resume RESUME_2 = new Resume(UUID_2);
    private final static String UUID_3 = "uuid3";
    private final static Resume RESUME_3 = new Resume(UUID_3);
    private final static String UUID_4 = "uuid4";
    private final static Resume RESUME_4 = new Resume(UUID_4);

    private final static String UUID_5 = "uuid5";
    private final static Resume OVERFLOW_RESUME = new Resume(UUID_5);


    private final static String NOT_EXIST_UUID = "notExistUuid";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void update() {
        Resume existResume = new Resume(UUID_1);
        storage.update(existResume);
        assertSame(existResume, storage.get(RESUME_1.getUuid()));
    }

    @Test
    void updateNotExist() {
        Resume notExistResume = new Resume(NOT_EXIST_UUID);
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
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    void saveOverflow() {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            storage.save(new Resume());
        }
        try {
            storage.save(OVERFLOW_RESUME);
        } catch (StorageException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void delete() {
        storage.delete(RESUME_1.getUuid());
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(RESUME_1.getUuid()));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(NOT_EXIST_UUID));
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(NOT_EXIST_UUID));
    }

    @Test
    void getAll() {
        Resume[] actual = storage.getAll();
        assertEquals(3, actual.length);
        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3};
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