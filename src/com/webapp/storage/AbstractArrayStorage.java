package com.webapp.storage;

import com.webapp.exception.NotExistStorageException;
import com.webapp.exception.StorageException;
import com.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    public Resume[] storage = new Resume[STORAGE_LIMIT];
    public int size = 0;

    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        if ((Integer) searchKey < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[(Integer) searchKey] = resume;
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveByIndex(resume, (Integer) searchKey);
            size++;
        }
    }

    @Override
    public void doDelete(Object searchKey) {
        deleteByIndex((Integer) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume doGet(Object searchKey) {
        return storage[(Integer) searchKey];
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (Integer) searchKey >= 0;
    }

    protected abstract Integer searchKey(String uuid);

    protected abstract void saveByIndex(Resume resume, Integer index);

    protected abstract void deleteByIndex(Integer index);
}
