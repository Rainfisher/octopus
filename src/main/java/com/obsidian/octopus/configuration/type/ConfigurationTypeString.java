package com.obsidian.octopus.configuration.type;

import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeString implements ConfigurationTypeInterface {

    @Override
    public Object parse(InputStream inputStream) throws Exception {
        return IOUtils.toString(inputStream);
    }

}
