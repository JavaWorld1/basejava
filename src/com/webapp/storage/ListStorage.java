package com.webapp.storage;

import com.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListStorage extends AbstractStorage<Integer> {

    public List<Resume> listStorage = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
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
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    public void doUpdate(Resume resume, Integer searchKey) {
        listStorage.set(searchKey, resume);
    }

    public void clear() {
        listStorage.clear();
    }

    @Override
    public void doSave(Resume resume, Integer searchKey) {
        listStorage.add(resume);
    }

    @Override
    public void doDelete(Integer searchKey) {
        listStorage.remove((int) searchKey);
    }

    @Override
    public final Resume doGet(Integer searchKey) {
        return listStorage.get(searchKey);
    }

    @Override
    public List<Resume> doCopyAll() {
        return new ArrayList<>(listStorage); // возвращаем копию листа
    }

    @Override
    public int size() {
        return listStorage.size();
    }
}
