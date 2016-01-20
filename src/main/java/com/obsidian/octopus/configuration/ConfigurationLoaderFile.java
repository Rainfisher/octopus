package com.obsidian.octopus.configuration;

import com.obsidian.octopus.configuration.type.ConfigurationTypeInterface;
import com.obsidian.octopus.configuration.type.ConfigurationTypeManager;
import com.obsidian.octopus.resolver.ConfigResolver;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationLoaderFile extends ConfigurationLoader {

    protected final Map<String, Long> loadingTimestamp;

    public ConfigurationLoaderFile(ConfigResolver configResolver, File file) {
        super(configResolver, file);
        this.loadingTimestamp = new HashMap<>();
    }

    @Override
    public void process() throws Exception {
        Object object = processFile(file, true);
        if (object != null) {
            save(_getName(), object, true);
        }
    }

    private String _getName() {
        String name = configResolver.getName();
        if (name == null) {
            name = FilenameUtils.getBaseName(file.getName());
        }
        return name;
    }

    protected Object processFile(File file, boolean checkTime) throws Exception {
        String fileName = file.getName();
        long lastModified = file.lastModified();
        if (checkTime && loadingTimestamp.containsKey(fileName)
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

    @Override
    public void reload() throws Exception {
        Object object = processFile(file, false);
        if (object != null) {
            save(_getName(), object, false);
        }
    }

}
