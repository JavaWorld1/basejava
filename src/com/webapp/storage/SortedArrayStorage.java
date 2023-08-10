package com.webapp.storage;

import com.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void deleteByIndex(int index) {
        Resume[] newArray = new Resume[storage.length];
        for (int i = 0; i < index; i++) {
            newArray[i] = storage[i];
        }
        for (int i = index + 1; i < size; i++) {
            newArray[i - 1] = storage[i];
        }
        storage = newArray;
    }

    @Override
    protected void saveByIndex(Resume resume, int index) {
        int startIndex = -(index + 1);
        Resume[] newArray = new Resume[storage.length];
        for (int i = 0; i < startIndex; i++) {
            newArray[i] = storage[i];
        }
        for (int i = startIndex; i < size; i++) {
            newArray[i + 1] = storage[i];
        }
        storage = newArray;
        storage[startIndex] = resume;
    }
}
