package com.webapp.storage;

import com.webapp.exception.NotExistStorageException;
import com.webapp.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected static final File STORAGE_DIR = new File("C:\\basejava\\storage");

    protected final Storage storage;

    private final static Resume R_1;
    private final static Resume R_2;
    private final static Resume R_3;
    private final static Resume R_4;

    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static String UUID_4 = "uuid4";
    private final static String UUID_5 = "uuid5";
    private final static String NOT_EXIST_UUID = "notExistUuid";
    protected final static Resume OVERFLOW_RESUME;

    static {
        R_1 = new Resume(UUID_1, "Name1");
        R_2 = new Resume(UUID_2, "Name2");
        R_3 = new Resume(UUID_3, "Name3");
        R_4 = new Resume(UUID_4, "Name4");
        OVERFLOW_RESUME = new Resume(UUID_5, "OVERFLOW_RESUME");

        R_1.addContact(ContactType.MAIL, "mail1@ya.ru");
        R_1.addContact(ContactType.PHONE, "11111");
        R_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R_1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        R_1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        R_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(2005,
                                        Month.JANUARY, "position1", "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005,
                                        Month.JANUARY, "position2", "content2"))));
        R_1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000,
                                        Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005,
                                        Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
        R_2.addContact(ContactType.SKYPE, "skype2");
        R_2.addContact(ContactType.PHONE, "22222");
        R_1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Position(2015,
                                        Month.JANUARY, "position1", "content1"))));
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(R_1);
        storage.save(R_2);
        storage.save(R_3);
    }

    @Test
    void update() {
        Resume existResume = new Resume(UUID_1, "Name1");
        storage.update(existResume);
        assertEquals(existResume, storage.get(R_1.getUuid()));
    }

    @Test
    void updateNotExist() {
        Resume notExistResume = new Resume(NOT_EXIST_UUID, "New Name");
        assertThrows(NotExistStorageException.class, () -> storage.update(notExistResume));
    }

    @Test
    void clear() {
        storage.clear();
        assertSize(0);
        assertEquals(Collections.emptyList(), storage.getAllSorted());
    }

    @Test
    void save() {
        storage.save(R_4);
        assertSize(4);
        assertGet(R_4);
    }

    @Test
    void delete() {
        storage.delete(R_1.getUuid());
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(R_1.getUuid()));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(NOT_EXIST_UUID));
    }

    @Test
    void get() {
        assertGet(R_1);
        assertGet(R_2);
        assertGet(R_3);
    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(NOT_EXIST_UUID));
    }

    @Test
    void getAllSorted() {
        List<Resume> actual = storage.getAllSorted();
        assertEquals(3, actual.size());
        List<Resume> expected = Arrays.asList(R_1, R_2, R_3);
        assertEquals(expected, actual);
    }

    @Test
    void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
