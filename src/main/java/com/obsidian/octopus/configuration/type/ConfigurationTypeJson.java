package com.obsidian.octopus.configuration.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;
import net.sf.json.JSONObject;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeJson implements ConfigurationTypeInterface {

    @Override
    public Object parse(InputStream inputStream) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map readValue = mapper.readValue(inputStream, Map.class);
        return JSONObject.fromObject(readValue);
    }

}
