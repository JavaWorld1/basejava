package com.webapp.storage;

import com.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListStorage extends AbstractStorage {

    public List<Resume> listStorage = new ArrayList<>();

    @Override
    protected Integer searchKey(String uuid) {
        ListIterator<Resume> listIterator = listStorage.listIterator();
        while (listIterator.hasNext()) {
            Integer index = listIterator.nextIndex();
            Resume iterResume = listIterator.next();
            if (iterResume.getUuid().equals(uuid)) {
                return index;
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        listStorage.set((Integer) searchKey, resume);
    }

    public void clear() {
        listStorage.clear();
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        listStorage.add(resume);
    }

    @Override
    public void doDelete(Object searchKey) {
        listStorage.remove((int) searchKey);
    }

    @Override
    public final Resume doGet(Object searchKey) {
        return listStorage.get((Integer) searchKey);
    }

    public Resume[] getAll() {
        return listStorage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return listStorage.size();
    }
}
