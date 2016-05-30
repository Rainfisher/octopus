package com.obsidian.octopus.configuration.type;

import java.io.InputStream;

/**
 *
 * @author Alex Chou
 */
public interface ConfigurationTypeInterface {

    Object parse(InputStream inputStream) throws Exception;

}
