package com.obsidian.octopus.configuration.type;

import java.io.File;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ConfigurationTypeJson implements ConfigurationTypeInterface {

    @Override
    public Object parse(File file) throws Exception {
        String data = FileUtils.readFileToString(file);
        return JSONObject.fromObject(data);
    }

}
