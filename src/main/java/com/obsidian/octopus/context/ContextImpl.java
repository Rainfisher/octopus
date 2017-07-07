package com.obsidian.octopus.context;

import com.obsidian.octopus.configuration.ConfigurationHotLoader;
import com.obsidian.octopus.configuration.ConfigurationLoader;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.listener.OctopusListener;
import com.obsidian.octopus.resolver.ConfigResolver;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alex Chou
 */
class ContextImpl implements Context {

    private final String[] args;
    private final Map<ConfigResolver, ConfigurationLoader> configurationLoaderMap;
    private final ConfigurationHotLoader configurationHotLoader;
    private IocInstanceProvider iocProvide;
    private final List<OctopusListener> listeners;

    public ContextImpl(String[] args) {
        this.args = args;
        this.configurationLoaderMap = new LinkedHashMap<>();
        this.configurationHotLoader = new ConfigurationHotLoader();
        this.listeners = new ArrayList<>();
    }

    @Override
    public String[] getArgs() {
        return args;
    }

    @Override
    public void addConfigurationLoader(ConfigResolver configResolver, ConfigurationLoader loader) {
        configurationLoaderMap.put(configResolver, loader);
    }

    @Override
    public Map<ConfigResolver, ConfigurationLoader> getConfigurationLoaderMap() {
        return configurationLoaderMap;
    }

    @Override
    public ConfigurationHotLoader getConfigurationHotLoader() {
        return configurationHotLoader;
    }

    @Override
    public void setIocProvide(IocInstanceProvider iocProvide) {
        this.iocProvide = iocProvide;
    }

    @Override
    public IocInstanceProvider getIocProvide() {
        return iocProvide;
    }

    @Override
    public void addListener(OctopusListener listener) {
        listeners.add(listener);
    }

    @Override
    public List<OctopusListener> getListeners() {
        return listeners;
    }

}
