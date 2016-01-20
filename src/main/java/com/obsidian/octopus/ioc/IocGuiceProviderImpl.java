package com.obsidian.octopus.ioc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.obsidian.octopus.ioc.IocGuiceProvider;

/**
 *
 * @author Alex Chou
 */
public class IocGuiceProviderImpl extends IocGuiceProvider {

    @Override
    public Injector getInjector() {
        return Guice.createInjector();
    }

}
