package com.webapp.storage;

import com.webapp.exception.StorageException;
import com.webapp.model.Resume;
import com.webapp.storage.serializer.StreamSerializerStrategy;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    private final Path directory;
    private final StreamSerializerStrategy streamSerializerStrategy;

    protected PathStorage(String dir, StreamSerializerStrategy streamSerializerStrategy) {
        this.directory = Paths.get(dir);
        this.streamSerializerStrategy = streamSerializerStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) { // directory - не директория тогда, когда возвращается false
            throw new IllegalArgumentException(dir + " is not directory");
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        try (Stream<Path> pathStream = Files.list(directory)) {
            return pathStream.map(new Function<Path, Resume>() {
                @Override
                public Resume apply(Path path) { // принимает элемент стрима типа Path, проводит действие на ним
                    // получает объект другого типа, заменяет элемент стрима типа Path на объект другого типа
                    // и так с каждым элементом стрима
                    return doGet(path);
                }
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            streamSerializerStrategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path); // создается новый файл
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + path, path.getFileName().toString(), e); // причиной выброса StorageException
            // является IOException
        }
        doUpdate(resume, path); // сериализация объекта resume в файл
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path); // если delete возвращает false, то выбросить exception
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString());
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return streamSerializerStrategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", path.getFileName().toString());
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> files = Files.list(directory)) {
            files.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", (String) null);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> files = Files.list(directory)) {
            return Math.toIntExact(files.count());
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
    }
}
