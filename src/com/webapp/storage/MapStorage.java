package com.webapp.storage;

import com.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    public Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected String searchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object uuid) {
        return mapStorage.containsKey((String) uuid);
    }

    @Override
    public void doUpdate(Resume resume, Object uuid) {
        mapStorage.replace((String) uuid, resume);
    }

    public void clear() {
        mapStorage.clear();
    }

    @Override
    public void doSave(Resume resume, Object uuid) {
        mapStorage.put((String) uuid, resume);
    }

    @Override
    public void doDelete(Object uuid) {
        mapStorage.remove((String) uuid);
    }

    @Override
    public final Resume doGet(Object uuid) {
       return mapStorage.get((String) uuid);
    }

    public Resume[] getAll() {
        return mapStorage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
