package com.obsidian.octopus.configuration.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obsidian.octopus.configuration.ConfigurationLoader;
import java.io.InputStream;
import java.util.Map;
import net.sf.json.JSONObject;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeJson implements ConfigurationTypeInterface {

    @Override
    public void parse(ConfigurationLoader loader, String name, InputStream inputStream) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map readValue = mapper.readValue(inputStream, Map.class);
        JSONObject json = JSONObject.fromObject(readValue);
        loader.save(name, json);
    }

}
