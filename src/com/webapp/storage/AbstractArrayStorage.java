package com.webapp.storage;

import com.webapp.exception.ExistStorageException;
import com.webapp.exception.NotExistStorageException;
import com.webapp.exception.StorageException;
import com.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    public Resume[] storage = new Resume[STORAGE_LIMIT];
    public int size = 0;

    @Override
    public void updateOperation(Resume resume, Integer index) {
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void saveOperation(Resume resume, Integer index) {
        if (size >= storage.length) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveByIndex(resume, index);
            size++;
        }
    }

    @Override
    public void deleteOperation(Integer index, String uuid) {
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteByIndex(index);
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    public Resume getOperation(Integer index, String uuid) {
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            return storage[index];
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract Integer getIndex(String uuid);

    protected abstract void saveByIndex(Resume resume, Integer index);

    protected abstract void deleteByIndex(Integer index);
}
