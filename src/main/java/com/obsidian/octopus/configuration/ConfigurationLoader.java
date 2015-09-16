package com.obsidian.octopus.configuration;

import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.resolver.ConfigResolver;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public abstract class ConfigurationLoader {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoader.class);

    protected final ConfigResolver configResolver;
    protected final File file;
    protected IocInstanceProvider iocInstanceProvider;

    public ConfigurationLoader(ConfigResolver configResolver, File file) {
        this.configResolver = configResolver;
        this.file = file;
    }

    public void setIocInstanceProvider(IocInstanceProvider iocInstanceProvider) {
        this.iocInstanceProvider = iocInstanceProvider;
    }

    public abstract void process() throws Exception;

    protected void save(String name, Object data, boolean trigger) throws InstantiationException, IllegalAccessException {
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
