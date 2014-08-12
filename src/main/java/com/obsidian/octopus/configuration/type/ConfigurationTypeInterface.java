package com.obsidian.octopus.configuration.type;

import java.io.File;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public interface ConfigurationTypeInterface {

    Object parse(File file) throws Exception;

}
