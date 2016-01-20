package com.obsidian.octopus.configuration.type;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeProperties implements ConfigurationTypeInterface {

    @Override
    public Object parse(File file) throws Exception {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            return properties;
        }
    }

}
