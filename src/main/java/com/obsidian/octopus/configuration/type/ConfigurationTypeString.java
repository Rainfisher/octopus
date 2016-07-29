package com.obsidian.octopus.configuration.type;

import com.obsidian.octopus.configuration.ConfigurationLoader;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeString implements ConfigurationTypeInterface {

    @Override
    public void parse(ConfigurationLoader loader, String name, InputStream inputStream) throws Exception {
        String string = IOUtils.toString(inputStream);
        loader.save(name, string);
    }

}
