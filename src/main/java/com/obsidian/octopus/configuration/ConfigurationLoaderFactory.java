package com.obsidian.octopus.configuration;

import com.obsidian.octopus.resolver.ConfigResolver;
import java.io.File;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationLoaderFactory {

    public static ConfigurationLoader build(ConfigResolver configResolver) {
        File file = new File(configResolver.getPath());

        if (!file.exists()) {
            return null;
        }

        ConfigurationLoader loader = null;
        if (file.isDirectory()) {
            loader = new ConfigurationLoaderPath(configResolver, file);
        } else if (file.isFile()) {
            loader = new ConfigurationLoaderFile(configResolver, file);
        }
        return loader;
    }

}
