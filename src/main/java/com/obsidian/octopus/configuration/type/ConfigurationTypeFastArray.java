package com.obsidian.octopus.configuration.type;

import com.alibaba.fastjson.JSONArray;
import java.io.File;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ConfigurationTypeFastArray implements ConfigurationTypeInterface {

    @Override
    public Object parse(File file) throws Exception {
        String text = FileUtils.readFileToString(file);
        return JSONArray.parseArray(text);
    }

}
