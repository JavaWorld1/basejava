package com.webapp.storage;

import com.webapp.exception.NotExistStorageException;
import com.webapp.exception.StorageException;
import com.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    public Resume[] storage = new Resume[STORAGE_LIMIT];
    public int size = 0;

    @Override
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public void doUpdate(Resume resume, Integer searchKey) {
        if (searchKey < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[searchKey] = resume;
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void doSave(Resume resume, Integer searchKey) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            saveByIndex(resume, searchKey); // в array searchKey для save не нужен, он нужен для sortedArray
            size++;
        }
    }

    @Override
    public void doDelete(Integer searchKey) {
        deleteByIndex(searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveByIndex(Resume resume, Integer index);

    protected abstract void deleteByIndex(Integer index);
}
