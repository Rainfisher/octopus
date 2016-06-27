package com.obsidian.octopus.ioc;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.obsidian.octopus.vulcan.core.Router;
import com.obsidian.octopus.vulcan.object.RouterImpl;

/**
 *
 * @author Alex Chou
 */
public abstract class IocGuiceProviderVulcanImpl extends IocGuiceProvider {

    @Override
    public Injector getInjector() {
        return Guice.createInjector(new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(Router.class).to(RouterImpl.class);
            }
        }, getModule());
    }

    public abstract Module getModule();

}
