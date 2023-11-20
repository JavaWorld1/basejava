package com.webapp.storage;

import com.webapp.exception.StorageException;
import com.webapp.model.Resume;
import com.webapp.storage.serializer.ObjectStreamSerializerStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private final File directory;
    private final ObjectStreamSerializerStrategy objectStreamSerializerStrategy;

    protected FileStorage(File directory, ObjectStreamSerializerStrategy objectStreamSerializerStrategy) {
        this.objectStreamSerializerStrategy = objectStreamSerializerStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) { // не директория, когда возвращается false
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writeable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error");
        }
        List<Resume> list = new ArrayList<>();
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    protected File getSearchKey(String uuid) {
        // имя файла генерируется на основе uuid резюме
        // сохраняет файл с именем uuid в каталог directory и возвращает вызвавшему методу этот файл
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) { // проверяет, существует ли переданный в метод файл в базе
        return file.exists();
    }

    @Override
    protected void doUpdate(Resume resume, File file) { //заменяет существующий файл в базе на новый, переданный в метод
        try {
            objectStreamSerializerStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        // имя файла генерируется на основе uuid резюме
        // если файл с таким именем еще не существует в базе, то метод записывает переданное резюме в этот файл
        try {
            file.createNewFile(); // создается файл
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e); // причиной выброса StorageException
            // является IOException
        }
        doUpdate(resume,file); // объект resume сериализуется в файл file
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return objectStreamSerializerStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", file.getName());
        }
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory read error");
        }
        return list.length;
    }
}
