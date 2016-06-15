package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.resolver.Resolver;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou
 */
public class XmlProviderModule implements XmlProviderInterface<Resolver> {

    @Override
    public void process(Resolver resolver, Element element)
            throws Exception {
        ModuleResolver moduleResolver = new ModuleResolver();
        String name = element.attributeValue("name");
        if (name == null) {
            throw new NullPointerException("xml parse error: startup-name not set");
        }

        XmlProviderManager<ModuleResolver> manager = _getManager();
        manager.reslove(moduleResolver, element);

        resolver.putModule(name, moduleResolver);
    }

    private XmlProviderManager<ModuleResolver> _getManager() {
        XmlProviderManager<ModuleResolver> manager = new XmlProviderManager<>();
        manager.register("log4j", new XmlProviderModuleLog4j());
        manager.register("config", new XmlProviderModuleConfig());
        manager.register("ioc", new XmlProviderModuleIoc());
        manager.register("listener", new XmlProviderModuleListener());
        manager.register("filter", new XmlProviderModuleFilter());
        manager.register("quartz-config", new XmlProviderModuleQuartzConfig());
        manager.register("quartz-group", new XmlProviderModuleQuartz());
        manager.register("response-filter", new XmlProviderModuleResponseFilter());
        manager.register("processor", new XmlProviderModuleProcessor());
        return manager;
    }

}
