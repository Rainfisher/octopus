package com.obsidian.octopus.configuration.type;

import java.io.File;

/**
 *
 * @author Alex Chou
 */
public interface ConfigurationTypeInterface {

    Object parse(File file) throws Exception;

}
