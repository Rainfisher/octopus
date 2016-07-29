package com.obsidian.octopus.configuration.type;

import com.obsidian.octopus.configuration.ConfigurationLoader;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeProperties implements ConfigurationTypeInterface {

    @Override
    public void parse(ConfigurationLoader loader, String name, InputStream inputStream) throws Exception {
        Properties properties = new Properties();
        properties.load(inputStream);
        loader.save(name, properties);
    }

}
