package com.obsidian.octopus.configuration.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import net.sf.json.JSONArray;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeJsonArray implements ConfigurationTypeInterface {

    @Override
    public Object parse(File file) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List readValue = mapper.readValue(file, List.class);
        return JSONArray.fromObject(readValue);
    }

}
