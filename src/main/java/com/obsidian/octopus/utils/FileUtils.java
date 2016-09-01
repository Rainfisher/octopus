package com.obsidian.octopus.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alex
 */
public class FileUtils {

    private static final Logger LOGGER = Logger.getInstance(FileUtils.class);

    public static void writeStringToFile(String fileName, Object data) {
        File file = new File(fileName);
        try {
            org.apache.commons.io.FileUtils.writeStringToFile(file, data.toString());
        }
        catch (IOException e) {
            LOGGER.debug("writeStringToFile", e);
        }
    }

    public static String readFileToStringFromJar(String fileName) {
        try (InputStream is = FileUtils.class.getResourceAsStream(fileName)) {
            if (is == null) {
                return null;
            }
            return IOUtils.toString(is);
        }
        catch (IOException e) {
            LOGGER.debug("readFileToStringFromJar", e);
        }
        return null;
    }

    public static String readFileToString(String fileName) {
        File file = new File(fileName);
        String content = null;
        try {
            content = org.apache.commons.io.FileUtils.readFileToString(file);
        }
        catch (IOException e) {
            LOGGER.debug("readFileToString", e);
        }
        return content;
    }

    public static String getReplacePath(String path) {
        Properties properties = System.getProperties();
        //生成匹配模式的正则表达式 
        String patternString = "\\$\\{(" + StringUtils.join(properties.keySet(), "|") + ")\\}";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(path);

        //两个方法：appendReplacement, appendTail 
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, properties.getProperty(matcher.group(1)));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
