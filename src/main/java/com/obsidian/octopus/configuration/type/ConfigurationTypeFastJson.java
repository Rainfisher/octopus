package com.obsidian.octopus.configuration.type;

import com.alibaba.fastjson.JSONObject;
import com.obsidian.octopus.configuration.ConfigurationLoader;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeFastJson implements ConfigurationTypeInterface {

    @Override
    public void parse(ConfigurationLoader loader, String name, InputStream inputStream) throws Exception {
        String text = IOUtils.toString(inputStream);
        JSONObject json = JSONObject.parseObject(text);
        loader.save(name, json);
    }

}
