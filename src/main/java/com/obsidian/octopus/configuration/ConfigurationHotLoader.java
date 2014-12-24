package com.obsidian.octopus.configuration;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ConfigurationHotLoader extends Thread {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationHotLoader.class);

    private final List<ConfigurationLoader> loaderList;

    public ConfigurationHotLoader() {
        super("octopus configuration hotloader");
        this.loaderList = new ArrayList<>();
    }

    public void addConfigurationLoader(ConfigurationLoader loader) {
        loaderList.add(loader);
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (ConfigurationLoader configurationLoader : loaderList) {
                    configurationLoader.process();
                }
                Thread.sleep(1000);
            }
            catch (Exception e) {
                LOGGER.error("octopus: hotload failed", e);
            }
        }
    }

}
