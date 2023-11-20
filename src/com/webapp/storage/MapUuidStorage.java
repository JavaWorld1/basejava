package com.webapp.storage;

import com.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    public Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected String getSearchKey(String searchKey) {
        return searchKey;
    }

    @Override
    protected boolean isExist(String searchKey) {
        return mapStorage.containsKey(searchKey);
    }

    @Override
    public void doUpdate(Resume resume, String searchKey) {
        mapStorage.replace(searchKey, resume);
    }

    public void clear() {
        mapStorage.clear();
    }

    @Override
    public void doSave(Resume resume, String searchKey) {
        mapStorage.put(searchKey, resume);
    }

    @Override
    public void doDelete(String searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    public Resume doGet(String searchKey) {
       return mapStorage.get(searchKey);
    }

    public List<Resume> doCopyAll() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
