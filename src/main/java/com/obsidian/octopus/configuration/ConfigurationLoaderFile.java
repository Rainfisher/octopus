package com.obsidian.octopus.configuration;

import com.obsidian.octopus.configuration.type.ConfigurationTypeInterface;
import com.obsidian.octopus.configuration.type.ConfigurationTypeManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Alex Chou
 */
public class ConfigurationLoaderFile extends ConfigurationLoader {

    protected final Map<String, Long> loadingTimestamp = new HashMap<>();

    @Override
    public void process(boolean isHotReload) throws Exception {
        String path = configResolver.getPath();
        File file = new File(path);
        if (!file.exists()) {
            if (!configResolver.isAllowNull()) {
                throw new FileNotFoundException(path);
            }
            return;
        }
        if (file.isDirectory()) {
            _processDirectory(file, isHotReload);
        } else {
            _processFile(file, false, isHotReload);
        }
    }

    private void _processDirectory(File dir, boolean isHotReload) throws Exception {
        boolean recursive = configResolver.isRecursive();
        Collection<File> files = FileUtils.listFiles(dir, configResolver.getExtensions(), recursive);
        for (File file : files) {
            _processFile(file, true, isHotReload);
        }
    }

    private void _processFile(File file, boolean dir, boolean isHotReload) throws Exception {
        if (this._checkTime(file)) {
            String name = _getFileName(file, dir);
            processInputStream(name, file, isHotReload);
        }
    }

    private String _getFileName(File file, boolean dir) {
        String name = configResolver.getName();
        String fileName = FilenameUtils.getBaseName(file.getName());
        if (name == null) {
            name = fileName;
        } else if (dir) {
            name += fileName;
        }
        return name;
    }

    protected void processInputStream(String name, File file, boolean hotLoad) throws Exception {
        String fileType = configResolver.getFileType();
        try (InputStream inputStream = new FileInputStream(file)) {
            ConfigurationTypeInterface instance = ConfigurationTypeManager.getInstance(fileType);
            instance.parse(this, name, inputStream, hotLoad);
        }
    }

    private boolean _checkTime(File file) {
        String fileName = file.getName();
        long lastModified = file.lastModified();
        if (loadingTimestamp.containsKey(fileName)
                && loadingTimestamp.get(fileName) == lastModified) {
            return false;
        }
        loadingTimestamp.put(fileName, lastModified);
        return true;
    }

}
