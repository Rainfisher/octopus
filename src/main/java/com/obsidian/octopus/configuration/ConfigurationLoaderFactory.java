package com.obsidian.octopus.configuration;

import com.obsidian.octopus.resolver.ConfigResolver;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationLoaderFactory {

    public static ConfigurationLoader build(ConfigResolver configResolver) {
        ConfigurationLoader loader;
        if (configResolver.isInner()) {
            loader = new ConfigurationLoaderInnerFile();
        } else {
            loader = new ConfigurationLoaderFile();
        }
        loader.setConfigResolver(configResolver);
        return loader;
    }

}
