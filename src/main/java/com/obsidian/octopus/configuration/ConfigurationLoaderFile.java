package com.obsidian.octopus.configuration;

import com.obsidian.octopus.configuration.type.ConfigurationTypeInterface;
import com.obsidian.octopus.configuration.type.ConfigurationTypeManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationLoaderFile extends ConfigurationLoader {

    protected final Map<String, Long> loadingTimestamp;

    public ConfigurationLoaderFile() {
        this.loadingTimestamp = new HashMap<>();
    }

    @Override
    public void process(boolean isHotReload) throws Exception {
        File file = (File) src;
        if (isHotReload) {
            String fileName = file.getName();
            long lastModified = file.lastModified();
            if (!this.checkTime(fileName, lastModified)) {
                return;
            }
        }
        Object object = processInputStream(file);
        if (object != null) {
            save(_getName(), object, true);
        }
    }

    private String _getName() {
        String name = configResolver.getName();
        if (name == null) {
            File file = (File) src;
            name = FilenameUtils.getBaseName(file.getName());
        }
        return name;
    }

    protected Object processInputStream(File file)
            throws Exception {
        String fileType = configResolver.getFileType();
        try (InputStream inputStream = new FileInputStream(file)) {
            ConfigurationTypeInterface instance = ConfigurationTypeManager.getInstance(fileType);
            return instance.parse(inputStream);
        }
    }

    protected boolean checkTime(String fileName, long lastModified) {
        if (loadingTimestamp.containsKey(fileName)
                && loadingTimestamp.get(fileName) == lastModified) {
            return false;
        }
        loadingTimestamp.put(fileName, lastModified);
        return true;
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
        this.process(false);
    }

}
