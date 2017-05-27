package com.obsidian.octopus.configuration.type;

import com.alibaba.fastjson.JSONArray;
import com.obsidian.octopus.configuration.ConfigurationLoader;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeFastArray implements ConfigurationTypeInterface {

    @Override
    public void parse(ConfigurationLoader loader, String name,
            InputStream inputStream, boolean hotLoad) throws Exception {
        String text = IOUtils.toString(inputStream);
        JSONArray array = JSONArray.parseArray(text);
        loader.save(name, array, hotLoad);
    }

}
