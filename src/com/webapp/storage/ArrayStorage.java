package com.webapp.storage;

import com.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    private int size = 0;

    public void update(Resume resume) {
        int index = indexIsExist(resume.getUuid());
        if (index == -1) {
            System.out.println("Resume does not exist in the database");
        } else {
            storage[index] = resume;
        }
    }

    private int indexIsExist(String uuid) {
        int indexNumber = -1;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return indexNumber;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int index = indexIsExist(resume.getUuid());
        if (size >= storage.length) {
            System.out.println("The database is full");
        } else if (index != -1) {
            System.out.println("Resume already exists in the database");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    public Resume get(String uuid) {
        int index = indexIsExist(uuid);
        if (index == -1) {
            System.out.println("Resume does not exist in the database");
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = indexIsExist(uuid);
        if (index == -1) {
            System.out.println("Resume does not exist in the database");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
