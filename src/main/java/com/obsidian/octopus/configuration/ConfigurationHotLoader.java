package com.obsidian.octopus.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ConfigurationHotLoader extends Thread {

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
                e.printStackTrace();
            }
        }
    }

}
