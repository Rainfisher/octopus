package com.obsidian.octopus.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ConfigurationManager {

    private static final Map<String, Object> MAP = new HashMap<>();

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        return ConfigurationManagerHolder.INSTANCE;
    }

    public void putConfiguration(String name, Object data) {
        MAP.put(name, data);
    }

    public <T> T getConfiguration(String name) {
        return (T) MAP.get(name);
    }

    private static class ConfigurationManagerHolder {

        private static final ConfigurationManager INSTANCE = new ConfigurationManager();

    }
}
