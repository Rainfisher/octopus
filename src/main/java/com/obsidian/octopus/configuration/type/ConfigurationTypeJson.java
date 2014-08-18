package com.obsidian.octopus.configuration.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.Map;
import net.sf.json.JSONObject;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ConfigurationTypeJson implements ConfigurationTypeInterface {

    @Override
    public Object parse(File file) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Map readValue = mapper.readValue(file, Map.class);
        return JSONObject.fromObject(readValue);
    }

}
