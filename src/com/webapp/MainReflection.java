package com.webapp;

import com.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume resume  = new Resume("uuid11", "name");
        Method method = Resume.class.getMethod("toString");
        System.out.println(method.invoke(resume));
    }
}
