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
    private Boolean inner = false;
    private Boolean loadOnStart = false;
    private Boolean hotLoad = false;
    private Class callback;
    private boolean allowNull = false;

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

    public Boolean isInner() {
        return inner;
    }

    public void setInner(Boolean inner) {
        this.inner = inner;
        if (inner) {
            this.loadOnStart = true;
            this.hotLoad = false;
        }
    }

    public Boolean isLoadOnStart() {
        return loadOnStart;
    }

    public void setLoadOnStart(Boolean loadOnStart) {
        if (!inner) {
            this.loadOnStart = loadOnStart;
        } else {
            this.loadOnStart = true;
        }
    }

    public Boolean isHotLoad() {
        return hotLoad;
    }

    public void setHotLoad(Boolean hotLoad) {
        if (!inner) {
            this.hotLoad = hotLoad;
        } else {
            this.hotLoad = false;
        }
    }

    public Class getCallback() {
        return callback;
    }

    public void setCallback(Class callback) {
        this.callback = callback;
    }

    public boolean isAllowNull() {
        return allowNull;
    }

    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

}
