package com.obsidian.octopus.configuration.type;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationTypeManager {

    private static final Map<String, ConfigurationTypeInterface> mapping = new HashMap<>();

    static {
        register("string", new ConfigurationTypeString());
        register("properties", new ConfigurationTypeProperties());
        register("json", new ConfigurationTypeJson());
        register("json-array", new ConfigurationTypeJsonArray());
        register("fastjson", new ConfigurationTypeFastJson());
        register("fastjson-array", new ConfigurationTypeFastArray());
    }

    public static final void register(String type, ConfigurationTypeInterface instance) {
        mapping.put(type, instance);
    }

    public static ConfigurationTypeInterface getInstance(String key) {
        return mapping.get(key);
    }

}
