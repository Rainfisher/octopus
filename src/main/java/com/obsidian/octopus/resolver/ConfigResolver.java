package com.obsidian.octopus.resolver;

/**
 *
 * @author Alex Chou
 */
public class ConfigResolver {

    private String name;
    private String path;
    private String fileType;
    private String[] extensions;
    private Boolean loadOnStart;
    private Boolean hotLoad;
    private Class callback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String[] getExtensions() {
        return extensions;
    }

    public void setExtensions(String[] extensions) {
        this.extensions = extensions;
    }

    public Boolean isLoadOnStart() {
        return loadOnStart;
    }

    public void setLoadOnStart(Boolean loadOnStart) {
        this.loadOnStart = loadOnStart;
    }

    public Boolean isHotLoad() {
        return hotLoad;
    }

    public void setHotLoad(Boolean hotLoad) {
        this.hotLoad = hotLoad;
    }

    public Class getCallback() {
        return callback;
    }

    public void setCallback(Class callback) {
        this.callback = callback;
    }
}
