package com.obsidian.octopus.resolver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex Chou
 */
public class ModuleResolver {

    private String log4j;
    private final List<ConfigResolver> configResolvers;
    private IocResolver iocResolver;
    private final List<ListenerResolver> listenerResolvers;
    private final List<FilterResolver> filterResolvers;
    private String quartzConfig;
    private final List<QuartzResolver> quartzResolvers;
    private Class responseFilter;
    private final List<ProcessorResolver> processorResolvers;

    public ModuleResolver() {
        this.configResolvers = new ArrayList<>();
        this.listenerResolvers = new ArrayList<>();
        this.filterResolvers = new ArrayList<>();
        this.quartzResolvers = new ArrayList<>();
        this.processorResolvers = new ArrayList<>();
    }

    public String getLog4j() {
        return log4j;
    }

    public void setLog4j(String log4j) {
        this.log4j = log4j;
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

    public String getQuartzConfig() {
        return quartzConfig;
    }

    public void setQuartzConfig(String quartzConfig) {
        this.quartzConfig = quartzConfig;
    }

    public List<QuartzResolver> getQuartzResolvers() {
        return quartzResolvers;
    }

    public void addQuartzResolver(QuartzResolver quartzResolver) {
        this.quartzResolvers.add(quartzResolver);
    }

    public Class getResponseFilter() {
        return responseFilter;
    }

    public void setResponseFilter(Class responseFilter) {
        this.responseFilter = responseFilter;
    }

    public List<ProcessorResolver> getProcessorResolvers() {
        return processorResolvers;
    }

    public void addProcessorResolver(ProcessorResolver processorResolver) {
        this.processorResolvers.add(processorResolver);
    }

}
