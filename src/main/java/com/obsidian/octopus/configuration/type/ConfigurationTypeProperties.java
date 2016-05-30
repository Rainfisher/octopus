package com.obsidian.octopus.configuration.type;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeProperties implements ConfigurationTypeInterface {

    @Override
    public Object parse(InputStream inputStream) throws Exception {
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

}
