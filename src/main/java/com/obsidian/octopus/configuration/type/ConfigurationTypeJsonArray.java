package com.obsidian.octopus.configuration.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obsidian.octopus.configuration.ConfigurationLoader;
import java.io.InputStream;
import java.util.List;
import net.sf.json.JSONArray;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeJsonArray implements ConfigurationTypeInterface {

    @Override
    public void parse(ConfigurationLoader loader, String name,
            InputStream inputStream, boolean hotLoad) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List readValue = mapper.readValue(inputStream, List.class);
        JSONArray array = JSONArray.fromObject(readValue);
        loader.save(name, array, hotLoad);
    }

}
