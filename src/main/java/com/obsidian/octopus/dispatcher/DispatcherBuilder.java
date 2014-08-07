package com.obsidian.octopus.dispatcher;

import com.obsidian.octopus.config.ConfigDescribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class DispatcherBuilder {

    private String startupName;
    private final Map<String, DispatcherDetail> startups;

    public DispatcherBuilder() {
        this.startups = new HashMap<>();
    }

    public String getStartupName() {
        return startupName;
    }

    public void setStartupName(String startupName) {
        this.startupName = startupName;
    }

    public void putStartup(String name, DispatcherDetail dispatcherDetail) {
        this.startups.put(name, dispatcherDetail);
    }

    public Map<String, DispatcherDetail> getStartups() {
        return startups;
    }

    public Dispatcher build() {
        return null;
    }

    private class DispatcherImpl extends DispatcherAbastract implements Dispatcher {

    }

    public static class DispatcherDetail {

        private final List<ConfigDescribe> configList;

        public DispatcherDetail() {
            configList = new ArrayList<>();
        }

        public void addConfig(ConfigDescribe config) {
            configList.add(config);
        }

        public List<ConfigDescribe> getConfigList() {
            return configList;
        }
    }
}
