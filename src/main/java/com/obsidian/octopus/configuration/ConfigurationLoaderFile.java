package com.obsidian.octopus.configuration;

import com.obsidian.octopus.configuration.type.ConfigurationTypeInterface;
import com.obsidian.octopus.configuration.type.ConfigurationTypeManager;
import com.obsidian.octopus.resolver.ConfigResolver;
import java.io.File;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ConfigurationLoaderFile extends ConfigurationLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoaderFile.class);

    public ConfigurationLoaderFile(ConfigResolver configResolver, File file) {
        super(configResolver, file);
    }

    @Override
    public void process() throws Exception {
        Object object = processFile(file);
        save(_getName(), object);
    }

    private String _getName() {
        String name = configResolver.getName();
        if (name == null) {
            name = FilenameUtils.getBaseName(file.getName());
        }
        return name;
    }

    protected Object processFile(File file) throws Exception {
        String fileName = file.getName();
        long lastModified = file.lastModified();
        if (loadingTimestamp.containsKey(fileName)
                && loadingTimestamp.get(fileName) == lastModified) {
            return null;
        }
        String fileType = configResolver.getFileType();
        ConfigurationTypeInterface instance = ConfigurationTypeManager.getInstance(fileType);
        loadingTimestamp.put(file.getName(), lastModified);
        return instance.parse(file);
    }

    @Override
    public void triggerCallback(Object data) {
        Class callback = configResolver.getCallback();
        if (callback != null && iocInstanceProvider != null) {
            ConfigurationCallback instance = (ConfigurationCallback) iocInstanceProvider.getInstance(callback);

            if (data == null) {
                String name = _getName();
                data = ConfigurationManager.getInstance().getConfiguration(name);
            }
            if (data != null) {
                instance.trigger(data);
            }
        }
    }

}
