package com.obsidian.octopus.ioc;

/**
 *
 * @author Alex Chou
 */
public interface IocInstanceProvider {

    Object getInstance(Class clazz);

    Object getInstance(String name);

    void injectMembers(Object instance);

    boolean canInject(String clazz);

}
