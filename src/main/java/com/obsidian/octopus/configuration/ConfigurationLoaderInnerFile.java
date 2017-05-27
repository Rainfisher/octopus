package com.obsidian.octopus.configuration;

import com.obsidian.octopus.configuration.type.ConfigurationTypeInterface;
import com.obsidian.octopus.configuration.type.ConfigurationTypeManager;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author alex
 */
public class ConfigurationLoaderInnerFile extends ConfigurationLoader {

    @Override
    public void process(boolean isHotReload) throws Exception {
        String path = configResolver.getPath();
        String fileType = configResolver.getFileType();
        InputStream inputStream = Thread.class.getResourceAsStream(path);
        if (inputStream == null) {
            if (!configResolver.isAllowNull()) {
                throw new FileNotFoundException(path);
            }
            return;
        }
        ConfigurationTypeInterface instance = ConfigurationTypeManager.getInstance(fileType);
        instance.parse(this, _getName(), inputStream, isHotReload);
    }

    private String _getName() {
        String name = configResolver.getName();
        if (name == null) {
            name = FilenameUtils.getBaseName(configResolver.getPath());
        }
        return name;
    }

}
