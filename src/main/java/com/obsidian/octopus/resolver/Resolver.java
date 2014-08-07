package com.obsidian.octopus.resolver;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class Resolver {

    private String moduleName;
    private final Map<String, ModuleResolver> moduleMap;

    public Resolver() {
        this.moduleMap = new HashMap<>();
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

    public Map<String, ModuleResolver> getModuleMap() {
        return moduleMap;
    }

}
