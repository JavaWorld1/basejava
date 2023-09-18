package com.webapp.storage;

import com.webapp.exception.ExistStorageException;
import com.webapp.exception.NotExistStorageException;
import com.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListStorage extends AbstractStorage {

    public List<Resume> listStorage = new ArrayList<>();

    protected Integer getIndex(String uuid) {
        ListIterator<Resume> listIterator = listStorage.listIterator();
        while (listIterator.hasNext()) {
            Integer index = listIterator.nextIndex();
            Resume iterResume = listIterator.next();
            if (iterResume.getUuid().equals(uuid))
                return index;
        }
        return null;
    }

    @Override
    public void updateOperation(Resume resume, Integer index) {
        if (index != null)
            listStorage.set(index, resume);
        else
            throw new NotExistStorageException(resume.getUuid());
    }

    public void clear() {
        listStorage.clear();
    }

    @Override
    public void saveOperation(Resume resume, Integer index) {
        if (index != null) {
            if (listStorage.get(index).getUuid().equals(resume.getUuid())) {
                throw new ExistStorageException(resume.getUuid());
            }
        } else {
            listStorage.add(resume);
        }
    }

    @Override
    public void deleteOperation(Integer index, String uuid) {
        if (index != null)
            listStorage.remove((int) index);
        else
            throw new NotExistStorageException(uuid);
    }

    @Override
    public final Resume getOperation(Integer index, String uuid) {
        if (index != null)
            return listStorage.get(index);
        else
            throw new NotExistStorageException(uuid);
    }

    public Resume[] getAll() {
        return listStorage.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return listStorage.size();
    }
}
