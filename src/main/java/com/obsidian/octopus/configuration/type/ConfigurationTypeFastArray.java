package com.obsidian.octopus.configuration.type;

import com.alibaba.fastjson.JSONArray;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeFastArray implements ConfigurationTypeInterface {

    @Override
    public Object parse(InputStream inputStream) throws Exception {
        String text = IOUtils.toString(inputStream);
        return JSONArray.parseArray(text);
    }

}
