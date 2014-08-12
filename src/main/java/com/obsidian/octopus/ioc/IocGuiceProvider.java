package com.obsidian.octopus.ioc;

import com.google.inject.Injector;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public abstract class IocGuiceProvider implements IocInstanceProvider {

    private Injector injector;

    public abstract Injector getInjector();

    @Override
    public <T> T getInstance(Class<? extends T> clazz) {
        if (injector == null) {
            injector = getInjector();
        }
        return injector.getInstance(clazz);
    }

    @Override
    public void injectMembers(Object instance) {
        if (injector == null) {
            injector = getInjector();
        }
        injector.injectMembers(instance);
    }

}
