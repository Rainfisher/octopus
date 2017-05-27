package com.obsidian.octopus.configuration;

import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.resolver.ConfigResolver;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex Chou
 */
public abstract class ConfigurationLoader {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoader.class);

    protected ConfigResolver configResolver;
    protected IocInstanceProvider iocInstanceProvider;
    protected Set<String> names = new HashSet<>();

    public ConfigResolver getConfigResolver() {
        return configResolver;
    }

    public void setConfigResolver(ConfigResolver configResolver) {
        this.configResolver = configResolver;
    }

    public void setIocInstanceProvider(IocInstanceProvider iocInstanceProvider) {
        this.iocInstanceProvider = iocInstanceProvider;
    }

    public Set<String> getNames() {
        return names;
    }

    public abstract void process(boolean isHotReload) throws Exception;

    public void save(String name, Object data, boolean hotLoad) {
        this.save(name, data, true, hotLoad);
    }

    public void save(String name, Object data, boolean trigger, boolean hotLoad) {
        if (configResolver.isSave()) {
            ConfigurationManager manager = ConfigurationManager.getInstance();
            manager.putConfiguration(name, data);
        }
        names.add(name);
        if (trigger) {
            triggerCallback(name, data, hotLoad);
        }
        LOGGER.info("octopus configuration:{} loading success", name);
    }

    public void triggerCallback(String name, Object data, boolean hotLoad) {
        Class callback = configResolver.getCallback();
        if (callback != null && iocInstanceProvider != null) {
            if (data == null) {
                data = ConfigurationManager.getInstance().getConfiguration(name);
            }
            ConfigurationCallback instance = (ConfigurationCallback) iocInstanceProvider.getInstance(callback);
            instance.trigger(this.configResolver, name, data, hotLoad);
        }
    }

}
