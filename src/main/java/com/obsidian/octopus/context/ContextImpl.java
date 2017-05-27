package com.obsidian.octopus.context;

import com.obsidian.octopus.configuration.ConfigurationHotLoader;
import com.obsidian.octopus.configuration.ConfigurationLoader;
import com.obsidian.octopus.filter.OctopusMinaFilter;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.listener.OctopusListener;
import com.obsidian.octopus.resolver.ConfigResolver;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Alex Chou
 */
class ContextImpl implements Context {

    private final Map<ConfigResolver, ConfigurationLoader> configurationLoaderMap;
    private final ConfigurationHotLoader configurationHotLoader;
    private IocInstanceProvider iocProvide;
    private final List<OctopusListener> listeners;
    private final List<OctopusMinaFilter> filters;
    private Scheduler scheduler;

    public ContextImpl() {
        configurationLoaderMap = new LinkedHashMap<>();
        configurationHotLoader = new ConfigurationHotLoader();
        listeners = new ArrayList<>();
        filters = new ArrayList<>();
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

    @Override
    public void addFilter(OctopusMinaFilter filter) {
        filters.add(filter);
    }

    @Override
    public List<OctopusMinaFilter> getFilters() {
        return filters;
    }

    @Override
    public Scheduler getScheduler(boolean init)
            throws SchedulerException {
        if (init && scheduler == null) {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
        }
        return scheduler;
    }

    @Override
    public void run() {
        for (OctopusListener octopusListener : listeners) {
            octopusListener.onDestroy(this);
        }
        if (scheduler != null) {
            try {
                scheduler.shutdown();
            }
            catch (SchedulerException e) {
            }
        }
    }

}
