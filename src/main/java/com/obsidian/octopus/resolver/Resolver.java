package com.obsidian.octopus.resolver;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alex Chou
 */
public class Resolver {

    private String[] args;
    private String moduleName;
    private final Map<String, ModuleResolver> moduleMap;

    public Resolver() {
        this.moduleMap = new HashMap<>();
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void putModule(String name, ModuleResolver moduleResolver) {
        this.moduleMap.put(name, moduleResolver);
    }

    public ModuleResolver getModuleResolver() {
        return getModuleResolver(moduleName);
    }

    public ModuleResolver getModuleResolver(String name) {
        return moduleMap.get(name);
    }

}
