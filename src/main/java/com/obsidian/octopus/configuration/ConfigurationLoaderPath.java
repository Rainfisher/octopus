package com.obsidian.octopus.configuration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationLoaderPath extends ConfigurationLoaderFile {

    @Override
    public void process(boolean isHotReload) throws Exception {
        Map<String, Object> datas = new HashMap<>();
        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String extension = FilenameUtils.getExtension(name);
                return ArrayUtils.contains(configResolver.getExtensions(), extension);
            }
        };
        File file = (File) src;
        File[] files = file.listFiles(filter);
        for (File tmp : files) {
            Object object = processInputStream(tmp);
            if (object != null) {
                String name = _getName(tmp);
                datas.put(name, object);
                LOGGER.debug("octopus configuration:{} loading to memcache", name);
            }
        }
        for (Map.Entry<String, Object> entry : datas.entrySet()) {
            save(entry.getKey(), entry.getValue(), true);
        }
    }

    @Override
    public void reload() throws Exception {
        process(false);
    }

    public String _getName(File file) {
        String name = configResolver.getName();
        String tmp = FilenameUtils.getBaseName(file.getName());
        if (name == null) {
            name = tmp;
        } else {
            name = name + tmp;
        }
        return name;
    }

}
