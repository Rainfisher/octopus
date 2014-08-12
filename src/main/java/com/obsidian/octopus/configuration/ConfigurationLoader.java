package com.obsidian.octopus.configuration;

import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.resolver.ConfigResolver;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public abstract class ConfigurationLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoader.class);

    protected final ConfigResolver configResolver;
    protected final File file;
    protected IocInstanceProvider iocInstanceProvider;
    protected final Map<String, Long> loadingTimestamp;

    public ConfigurationLoader(ConfigResolver configResolver, File file) {
        this.configResolver = configResolver;
        this.file = file;
        this.loadingTimestamp = new HashMap<>();
    }

    public void setIocInstanceProvider(IocInstanceProvider iocInstanceProvider) {
        this.iocInstanceProvider = iocInstanceProvider;
    }

    public abstract void process() throws Exception;

    protected void save(String name, Object data) throws InstantiationException, IllegalAccessException {
        ConfigurationManager manager = ConfigurationManager.getInstance();
        manager.putConfiguration(name, data);
        triggerCallback(data);
        LOGGER.debug("octopus configuration:{} loading success", name);
    }

    public abstract void triggerCallback(Object data);

}
