package com.webapp.storage;

import com.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final void update(Resume resume) {
        Integer index = getIndex(resume.getUuid());
        updateOperation(resume, index);
    }

    public final void save(Resume resume) {
        Integer index = getIndex(resume.getUuid());
        saveOperation(resume, index);
    }

    public final void delete(String uuid) {
        Integer index = getIndex(uuid);
        deleteOperation(index, uuid);
    }

    public final Resume get(String uuid) {
        Integer index = getIndex(uuid);
        return getOperation(index, uuid);
    }

    protected abstract Integer getIndex(String uuid);

    protected abstract void updateOperation(Resume resume, Integer index);

    protected abstract void saveOperation(Resume resume, Integer index);

    protected abstract void deleteOperation(Integer index, String uuid);

    protected abstract Resume getOperation(Integer index, String uuid);
}
