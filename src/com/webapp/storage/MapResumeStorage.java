package com.webapp.storage;

import com.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    public Map<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) { //возвращает из мапы резюме, которое принадлежит ключу uuid
        return mapStorage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    public void doUpdate(Resume r, Resume searchKey) {
        mapStorage.put(r.getUuid(), r);
    }

    public void clear() {
        mapStorage.clear();
    }

    @Override
    public void doSave(Resume r, Resume searchKey) {
        mapStorage.put(r.getUuid(), r);
    }

    @Override
    public void doDelete(Resume searchKey) {
        mapStorage.remove((searchKey).getUuid());
    }

    @Override
    public Resume doGet(Resume searchKey) {
        return searchKey;
    }

    public List<Resume> doCopyAll() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
