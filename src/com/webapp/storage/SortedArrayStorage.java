package com.webapp.storage;

import com.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer searchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void deleteByIndex(Integer index) {
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(storage, index + 1, storage, index, numMoved);
        }
    }

    @Override
    protected void saveByIndex(Resume resume, Integer index) {
        int startIndex = -index - 1;
        System.arraycopy(storage, startIndex, storage, startIndex + 1, size - startIndex);
        storage[startIndex] = resume;
    }
}
