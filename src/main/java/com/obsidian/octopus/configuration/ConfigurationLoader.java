package com.obsidian.octopus.configuration;

import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.resolver.ConfigResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex Chou
 */
public abstract class ConfigurationLoader {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoader.class);

    protected ConfigResolver configResolver;
    protected Object src;
    protected IocInstanceProvider iocInstanceProvider;

    public void setConfigResolver(ConfigResolver configResolver) {
        this.configResolver = configResolver;
    }

    public void setSrc(Object src) {
        this.src = src;
    }

    public void setIocInstanceProvider(IocInstanceProvider iocInstanceProvider) {
        this.iocInstanceProvider = iocInstanceProvider;
    }

    public abstract void process(boolean isHotReload) throws Exception;

    public void save(String name, Object data) throws InstantiationException, IllegalAccessException {
        this.save(name, data, true);
    }

    public void save(String name, Object data, boolean trigger) throws InstantiationException, IllegalAccessException {
        ConfigurationManager manager = ConfigurationManager.getInstance();
        manager.putConfiguration(name, data);
        if (trigger) {
            triggerCallback(data);
        }
        LOGGER.info("octopus configuration:{} loading success", name);
    }

    public abstract void triggerCallback(Object data);

    public abstract void reload() throws Exception;

}
