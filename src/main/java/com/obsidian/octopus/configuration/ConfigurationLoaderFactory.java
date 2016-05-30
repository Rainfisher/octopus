package com.obsidian.octopus.configuration;

import com.obsidian.octopus.resolver.ConfigResolver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationLoaderFactory {

    public static ConfigurationLoader build(ConfigResolver configResolver) {
        InputStream inputStream;
        File file;
        ConfigurationLoader loader = null;

        if (configResolver.isInner()) {
            inputStream = Thread.class.getResourceAsStream(configResolver.getPath());
            if (inputStream == null) {
                return null;
            }
            loader = new ConfigurationLoaderFile(configResolver);
        } else {
            file = new File(configResolver.getPath());
            if (!file.exists()) {
                return null;
            }
            if (file.isDirectory()) {
                loader = new ConfigurationLoaderPath(configResolver);
            } else if (file.isFile()) {
                loader = new ConfigurationLoaderFile(configResolver);
            }
            try {
                inputStream = new FileInputStream(file);
            }
            catch (FileNotFoundException e) {
                return null;
            }
        }

        if (loader == null) {
            return null;
        }

        loader.setInputStream(inputStream);
        return loader;
    }

}
