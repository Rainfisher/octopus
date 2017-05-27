package com.obsidian.octopus.configuration.type;

import com.obsidian.octopus.configuration.ConfigurationLoader;
import java.io.InputStream;

/**
 *
 * @author Alex Chou
 */
public interface ConfigurationTypeInterface {

    public abstract void parse(ConfigurationLoader loader, String name,
            InputStream inputStream, boolean hotLoad) throws Exception;

}
