package com.obsidian.octopus.resolver;

import java.util.List;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class ModuleResolver {

    private List<ConfigResolver> configResolvers;
    private IocResolver iocResolver;
    private List<ListenerResolver> listenerResolvers;
    private List<FilterResolver> filterResolvers;
    private Boolean quartz;

    public List<ConfigResolver> getConfigResolvers() {
        return configResolvers;
    }

    public void setConfigResolvers(List<ConfigResolver> configResolvers) {
        this.configResolvers = configResolvers;
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

    public void setListenerResolvers(List<ListenerResolver> listenerResolvers) {
        this.listenerResolvers = listenerResolvers;
    }

    public List<FilterResolver> getFilterResolvers() {
        return filterResolvers;
    }

    public void setFilterResolvers(List<FilterResolver> filterResolvers) {
        this.filterResolvers = filterResolvers;
    }

    public Boolean isQuartz() {
        return quartz;
    }

    public void setQuartz(Boolean quartz) {
        this.quartz = quartz;
    }

}
