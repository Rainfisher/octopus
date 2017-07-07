package com.obsidian.octopus.ioc;

import com.google.inject.ConfigurationException;
import com.google.inject.Injector;
import org.apache.commons.lang.ClassUtils;

/**
 *
 * @author Alex Chou
 */
public abstract class IocGuiceProvider implements IocInstanceProvider {

    private Injector injector;

    public abstract Injector getInjector();

    @Override
    public Object getInstance(String name) {
        Class clazz;
        try {
            clazz = ClassUtils.getClass(name);
        }
        catch (ClassNotFoundException e) {
            return null;
        }
        return getInstance(clazz);
    }

    @Override
    public Object getInstance(Class clazz) {
        if (injector == null) {
            injector = getInjector();
        }
        if (clazz == IocInstanceProvider.class) {
            return this;
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

    @Override
    public boolean canInject(String clazz) {
        Object instance = null;
        try {
            instance = getInstance(clazz);
        }
        catch (ConfigurationException e) {
        }
        return instance != null;
    }
    
}
