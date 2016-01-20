package com.obsidian.octopus.resolver;

/**
 *
 * @author Alex Chou
 */
public class IocResolver {

    private String factory;
    private Class iocClass;

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public Class getIocClass() {
        return iocClass;
    }

    public void setIocClass(Class iocClass) {
        this.iocClass = iocClass;
    }

}
