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
    private boolean recursive = false;
    private boolean inner = false;
    private boolean loadOnStart = false;
    private boolean hotLoad = false;
    private boolean save = true;
    private String callback;
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

    public boolean isRecursive() {
        return recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    public boolean isInner() {
        return inner;
    }

    public void setInner(boolean inner) {
        this.inner = inner;
        if (inner) {
            this.loadOnStart = true;
            this.hotLoad = false;
        }
    }

    public boolean isLoadOnStart() {
        return loadOnStart;
    }

    public void setLoadOnStart(boolean loadOnStart) {
        if (!inner) {
            this.loadOnStart = loadOnStart;
        } else {
            this.loadOnStart = true;
        }
    }

    public boolean isHotLoad() {
        return hotLoad;
    }

    public void setHotLoad(boolean hotLoad) {
        if (!inner) {
            this.hotLoad = hotLoad;
        } else {
            this.hotLoad = false;
        }
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public boolean isAllowNull() {
        return allowNull;
    }

    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

}
