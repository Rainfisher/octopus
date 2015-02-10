package com.obsidian.octopus.utils;

import java.util.Map;
import java.util.Properties;

/**
 *
 * @author alex
 */
public class FileUtils {
    
    public static String getReplacePath(String path) {
        Properties properties = System.getProperties();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            path = path.replaceAll(String.format("\\{%s\\}", entry.getKey()), entry.getValue().toString());
        }
        return path;
    }
    
}
