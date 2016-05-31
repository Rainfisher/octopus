package com.obsidian.octopus.configuration;

import com.obsidian.octopus.configuration.type.ConfigurationTypeInterface;
import com.obsidian.octopus.configuration.type.ConfigurationTypeManager;
import java.io.InputStream;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author alex
 */
public class ConfigurationLoaderInnerFile extends ConfigurationLoader {

    @Override
    public void process(boolean isHotReload) throws Exception {
        String fileType = configResolver.getFileType();
        ConfigurationTypeInterface instance = ConfigurationTypeManager.getInstance(fileType);
        Object object = instance.parse((InputStream) src);
        if (object != null) {
            save(_getName(), object, true);
        }
    }

    private String _getName() {
        String name = configResolver.getName();
        if (name == null) {
            name = FilenameUtils.getBaseName(configResolver.getPath());
        }
        return name;
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
        process(false);
    }

}
