package com.webapp.storage;

import com.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected Integer getSearchKey(String uuid) { // ищет резюме с данным uuid в хранилище и возвращает его индекс
        Resume searchKey = new Resume(uuid, "dummy"); // fullname - заглушка
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
    } // двоичный поиск возвращает предполагаемый индекс для вставки, если указанное резюме не найдено

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
