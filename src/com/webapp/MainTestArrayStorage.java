package com.webapp;

import com.webapp.model.Resume;
import com.webapp.storage.ArrayStorage;
import com.webapp.storage.Storage;

/**
 * Test for your com.webapp.storage.SortedArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");

        //save method test
        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        //getAll method test
        printAll();

        //get method test
        System.out.println();
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));

        //size method test
        System.out.println();
        System.out.println("Size: " + ARRAY_STORAGE.size());

        //update method test
        Resume testResume = new Resume();
        testResume.setUuid("uuid1");
        ARRAY_STORAGE.update(testResume);
        System.out.println("Get updated resume: " + ARRAY_STORAGE.get(testResume.getUuid()));

        //get method test
        System.out.println();
        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        //delete method test
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();

        //clear method test
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
