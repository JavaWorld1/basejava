package com.webapp;

import com.webapp.model.Resume;
import com.webapp.storage.SortedArrayStorage;
import com.webapp.storage.Storage;

/**
 * Test for your com.webapp.storage.SortedArrayStorage implementation
 */
public class MainTestSortedArrayStorage {
    static final Storage SORTED_ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid0");
        Resume r2 = new Resume();
        r2.setUuid("uuid1");
        Resume r3 = new Resume();
        r3.setUuid("uuid2");
        Resume r4 = new Resume();
        r4.setUuid("uuid3");

        //save method test
        SORTED_ARRAY_STORAGE.save(r1);
        SORTED_ARRAY_STORAGE.save(r2);
        SORTED_ARRAY_STORAGE.save(r3);
        SORTED_ARRAY_STORAGE.save(r4);

        //getAll method test
        printAll();

        //get method test
        System.out.println();
        System.out.println("Get r1: " + SORTED_ARRAY_STORAGE.get(r1.getUuid()));

        //size method test
        System.out.println();
        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());

        //update method test
        Resume testResume = new Resume();
        testResume.setUuid("uuid1");
        SORTED_ARRAY_STORAGE.update(testResume);
        System.out.println("Get updated resume: " + SORTED_ARRAY_STORAGE.get(testResume.getUuid()));

        //get method test
        System.out.println();
        System.out.println("Get dummy: " + SORTED_ARRAY_STORAGE.get("dummy"));

        //delete method test
        SORTED_ARRAY_STORAGE.delete(r3.getUuid());
        SORTED_ARRAY_STORAGE.delete(r4.getUuid());
        printAll();

        //clear method test
        SORTED_ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + SORTED_ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : SORTED_ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
