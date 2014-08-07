package com.obsidian.octopus.utils;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ClassUtils {

    public static Class getClass(String className) {
        Class clazz = null;
        try {
            clazz = org.apache.commons.lang.ClassUtils.getClass(className);
        }
        catch (ClassNotFoundException ex) {
        }
        return clazz;
    }

}
