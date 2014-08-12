package com.obsidian.octopus.ioc;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public interface IocInstanceProvider {

    <T> T getInstance(Class<? extends T> clazz);

    void injectMembers(Object instance);

}
