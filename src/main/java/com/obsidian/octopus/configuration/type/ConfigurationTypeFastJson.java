package com.obsidian.octopus.configuration.type;

import com.alibaba.fastjson.JSONObject;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeFastJson implements ConfigurationTypeInterface {

    @Override
    public Object parse(InputStream inputStream) throws Exception {
        String text = IOUtils.toString(inputStream);
        return JSONObject.parseObject(text);
    }

}
