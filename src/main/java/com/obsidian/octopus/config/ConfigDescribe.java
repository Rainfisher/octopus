package com.obsidian.octopus.config;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ConfigDescribe {

    private String name;
    private String path;
    private String fileType;
    private String hotLoad;
    private String callback;

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

    public String getHotLoad() {
        return hotLoad;
    }

    public void setHotLoad(String hotLoad) {
        this.hotLoad = hotLoad;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

}
