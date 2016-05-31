package com.obsidian.octopus.configuration;

import com.obsidian.octopus.resolver.ConfigResolver;
import java.io.File;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationLoaderFactory {

    public static ConfigurationLoader build(ConfigResolver configResolver) {
        Object src = null;
        ConfigurationLoader loader = null;

        if (configResolver.isInner()) {
            src = Thread.class.getResourceAsStream(configResolver.getPath());
            if (src == null) {
                return null;
            }
            loader = new ConfigurationLoaderInnerFile();
        } else {
            File file = new File(configResolver.getPath());
            if (!file.exists()) {
                return null;
            }
            if (file.isDirectory()) {
                loader = new ConfigurationLoaderPath();
            } else if (file.isFile()) {
                loader = new ConfigurationLoaderFile();
            }
            src = file;
        }

        if (loader == null) {
            return null;
        }

        loader.setSrc(src);
        return loader;
    }

}
