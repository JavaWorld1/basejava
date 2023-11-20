package com.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String filePath = ".\\.gitignore";

        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);


        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File(".\\src\\com\\webapp");
        File dir1 = new File("src\\com\\webapp\\MainReflection.java");
        System.out.println(dir.isDirectory());
        System.out.println(dir.canRead());
        System.out.println("*______________________________________________*");
        System.out.println(dir1.exists());
        System.out.println(dir1.canRead());
        System.out.println(dir1.isDirectory());
        System.out.println(dir1.isFile());
        System.out.println(dir1.getAbsolutePath());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.print(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        printDirsRecursive(dir);
        printDirectoryDeeply(dir);
    }

    public static void printDirsRecursive(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File name : files) {
                if(name.isDirectory()) {
                    System.out.println(name);
                    System.out.println(Arrays.toString(Objects.requireNonNull(name.list())));
                }
                else if (name.isFile()) {
                    System.out.println(name);
                }
            }
        }
    }

    public static void printDirectoryDeeply(File dir) {
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    System.out.println("File: " + file.getName());
                } else if (file.isDirectory()) {
                    System.out.println("Directory: " + file.getName());
                    printDirectoryDeeply(file);
                }
            }
        }
    }
}
