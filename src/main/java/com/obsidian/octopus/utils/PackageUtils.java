package com.obsidian.octopus.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 *
 * @author alex
 */
public class PackageUtils {

    public static List<Class> getClasssFromPackage(String packageName, boolean recursive) {
        List<Class> clazzs = new ArrayList<>();
        // 包名对应的路径名称
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findClassInPackageByFile(packageName, filePath, recursive, clazzs);
                } else if ("jar".equals(protocol)) {
                    String jarFile = System.getProperty("java.class.path");
                    String filePath = URLDecoder.decode(jarFile, "UTF-8");
                    findClassInJar(packageDirName, filePath, clazzs);
                }
            }
        }
        catch (Exception e) {
        }
        return clazzs;
    }

    public static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive, List<Class> clazzs) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 在给定的目录下找到所有的文件，并且进行条件过滤
        File[] dirFiles = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                boolean acceptDir = recursive && file.isDirectory();// 接受dir目录
                boolean acceptClass = file.getName().endsWith("class");// 接受class文件
                return acceptDir || acceptClass;
            }
        });

        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                }
                catch (Exception e) {
                }
            }
        }
    }

    public static void findClassInJar(String packageDirName, String filePath, List<Class> clazzs)
            throws Exception {
        List<String> list = new ArrayList<>();
        try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(filePath))) {
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                String name = jarEntry.getName();
                if (name.startsWith(packageDirName) && name.endsWith("class")) {
                    list.add(name);
                }
            }
        }
        Collections.sort(list);
        for (String name : list) {
            name = name.replace("/", ".");
            name = name.replace(".class", "");
            clazzs.add(Thread.currentThread().getContextClassLoader().loadClass(name));
        }
    }

}
