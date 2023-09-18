package com.webapp;

import com.webapp.exception.ExistStorageException;
import com.webapp.exception.NotExistStorageException;
import com.webapp.model.Resume;
import com.webapp.storage.ListStorage;
import com.webapp.storage.Storage;

public class MainTestListStorage {
    static final Storage LIST_STORAGE = new ListStorage();

    public static void main(String[] args) throws ExistStorageException, NotExistStorageException {
        Resume r1 = new Resume("uuid0");
        Resume r2 = new Resume("uuid1");
        Resume r3 = new Resume("uuid2");

        //save method test
        LIST_STORAGE.save(r1);
        LIST_STORAGE.save(r2);
        LIST_STORAGE.save(r3);

        //getAll method test
        printAll();

        //get method test
        System.out.println();
        System.out.println("Get r1: " + LIST_STORAGE.get(r1.getUuid()));

        //size method test
        System.out.println();
        System.out.println("Size: " + LIST_STORAGE.size());

        //update method test
        Resume testResume = new Resume("uuid1");
        LIST_STORAGE.update(testResume);
        System.out.println("Get updated resume: " + LIST_STORAGE.get(testResume.getUuid()));

        //get method test
        System.out.println();
//        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        //delete method test
        LIST_STORAGE.delete(r1.getUuid());
        printAll();
        System.out.println("Size: " + LIST_STORAGE.size());

        //clear method test
        LIST_STORAGE.clear();
        printAll();
        System.out.println("Size: " + LIST_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : LIST_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
