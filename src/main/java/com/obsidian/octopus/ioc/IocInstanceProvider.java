package com.obsidian.octopus.ioc;

/**
 *
 * @author Alex Chou
 */
public interface IocInstanceProvider {

    <T> T getInstance(Class<? extends T> clazz);

    void injectMembers(Object instance);

}
