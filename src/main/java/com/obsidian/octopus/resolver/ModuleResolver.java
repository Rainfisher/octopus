package com.obsidian.octopus.resolver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ModuleResolver {

    private final List<ConfigResolver> configResolvers;
    private IocResolver iocResolver;
    private final List<ListenerResolver> listenerResolvers;
    private final List<FilterResolver> filterResolvers;
    private final List<QuartzResolver> quartzResolvers;

    public ModuleResolver() {
        this.configResolvers = new ArrayList<>();
        this.listenerResolvers = new ArrayList<>();
        this.filterResolvers = new ArrayList<>();
        this.quartzResolvers = new ArrayList<>();
    }

    public List<ConfigResolver> getConfigResolvers() {
        return configResolvers;
    }

    public void addConfigResolver(ConfigResolver configResolver) {
        this.configResolvers.add(configResolver);
    }

    public IocResolver getIocResolver() {
        return iocResolver;
    }

    public void setIocResolver(IocResolver iocResolver) {
        this.iocResolver = iocResolver;
    }

    public List<ListenerResolver> getListenerResolvers() {
        return listenerResolvers;
    }

    public void addListenerResolver(ListenerResolver listenerResolver) {
        this.listenerResolvers.add(listenerResolver);
    }

    public List<FilterResolver> getFilterResolvers() {
        return filterResolvers;
    }

    public void addFilterResolver(FilterResolver filterResolver) {
        this.filterResolvers.add(filterResolver);
    }

    public List<QuartzResolver> getQuartzResolvers() {
        return quartzResolvers;
    }

    public void addQuartzResolver(QuartzResolver quartzResolver) {
        this.quartzResolvers.add(quartzResolver);
    }

}
