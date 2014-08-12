package com.obsidian.octopus.configuration;

import com.obsidian.octopus.configuration.type.ConfigurationTypeInterface;
import com.obsidian.octopus.configuration.type.ConfigurationTypeManager;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.resolver.ConfigResolver;
import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ConfigurationLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationLoader.class);

    private IocInstanceProvider iocInstanceProvider;
    private final ConfigResolver configResolver;
    private boolean isDirectory;
    private final Map<String, Long> loadingTimestamp;

    public ConfigurationLoader(ConfigResolver configResolver) {
        this.configResolver = configResolver;
        this.loadingTimestamp = new HashMap<>();
    }

    public void setIocInstanceProvider(IocInstanceProvider iocInstanceProvider) {
        this.iocInstanceProvider = iocInstanceProvider;
    }

    public void process() throws Exception {
        File file = new File(configResolver.getPath());

        if (file.exists()) {
            if (file.isFile()) {
                _processFile(file);
            } else if (file.isDirectory()) {
                _processDirectory(file);
            }
        }
    }

    private void _processFile(File file) throws Exception {
        String name = file.getName();
        long lastModified = file.lastModified();
        if (loadingTimestamp.containsKey(name)
                && loadingTimestamp.get(name) == lastModified) {
            return;
        }
        String fileType = configResolver.getFileType();
        ConfigurationTypeInterface instance = ConfigurationTypeManager.getInstance(fileType);
        Object object = instance.parse(file);
        _save(file, object);
        loadingTimestamp.put(file.getName(), lastModified);
    }

    private void _processDirectory(File path) throws Exception {
        isDirectory = true;
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String extension = FilenameUtils.getExtension(name);
                return ArrayUtils.contains(configResolver.getExtensions(), extension);
            }
        };
        File[] files = path.listFiles(filter);
        for (File file : files) {
            _processFile(file);
        }
    }

    private void _save(File file, Object data) throws InstantiationException, IllegalAccessException {
        String name = configResolver.getName();
        if (isDirectory) {
            String tmp = FilenameUtils.getBaseName(file.getName());
            if (name == null) {
                name = tmp;
            } else {
                name = name + tmp;
            }
        } else if (name == null) {
            name = FilenameUtils.getBaseName(file.getName());
        }
        ConfigurationManager manager = ConfigurationManager.getInstance();
        manager.putConfiguration(name, data);

        Class callback = configResolver.getCallback();
        if (callback != null) {
            ConfigurationCallback instance;
            if (iocInstanceProvider != null) {
                instance = (ConfigurationCallback) iocInstanceProvider.getInstance(callback);
            } else {
                instance = (ConfigurationCallback) callback.newInstance();
            }
            instance.trigger(data);
        }

        LOGGER.debug("octopus configuration:{} loading success", name);
    }

}
