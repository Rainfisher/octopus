package com.obsidian.octopus.xml;

import com.obsidian.octopus.resolver.ConfigResolver;
import com.obsidian.octopus.resolver.FilterResolver;
import com.obsidian.octopus.resolver.IocResolver;
import com.obsidian.octopus.resolver.ListenerResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ClassUtils;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class XmlModuleResolver {

    private String name;
    private final Element element;

    public XmlModuleResolver(Element element) {
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public ModuleResolver build() throws ClassNotFoundException {
        ModuleResolver moduleResolver = new ModuleResolver();
        name = element.attributeValue("name");
        if (name == null) {
            throw new NullPointerException("xml parse error: startup-name not set");
        }
        List<ConfigResolver> configList = _resolveConfig();
        moduleResolver.setConfigResolvers(configList);

        IocResolver iocResolver = _resolveIoc();
        moduleResolver.setIocResolver(iocResolver);

        List<ListenerResolver> listenerResolvers = _resolveListener();
        moduleResolver.setListenerResolvers(listenerResolvers);

        List<FilterResolver> filterResolvers = _resolveFilter();
        moduleResolver.setFilterResolvers(filterResolvers);

        return moduleResolver;
    }

    private List<ConfigResolver> _resolveConfig() {
        List<Element> elements = element.elements("config");
        List<ConfigResolver> list = new ArrayList<>();
        for (Element config : elements) {
            ConfigResolver configResolver = new ConfigResolver();
            try {
                BeanUtils.setProperty(configResolver, "name", config.attributeValue("name"));
            }
            catch (IllegalAccessException | InvocationTargetException e) {
            }
            List<Element> params = config.elements("param");
            for (Element param : params) {
                try {
                    BeanUtils.setProperty(configResolver, param.attributeValue("name"),
                            param.getStringValue());
                }
                catch (IllegalAccessException | InvocationTargetException e) {
                }
            }
            list.add(configResolver);
        }
        return list;
    }

    private IocResolver _resolveIoc() throws ClassNotFoundException {
        IocResolver iocResolver = null;
        Element ioc = element.element("ioc");
        if (ioc != null) {
            iocResolver = new IocResolver();
            String factory = ioc.attributeValue("factory");
            if (factory == null) {
                throw new NullPointerException("module ioc not set factory");
            }
            String iocClazz = ioc.attributeValue("class");
            if (iocClazz == null) {
                throw new NullPointerException("module ioc not set class");
            }
            Class clazz = ClassUtils.getClass(iocClazz);
            iocResolver.setFactory(factory);
            iocResolver.setIocClass(clazz);
        }
        return iocResolver;
    }

    private List<ListenerResolver> _resolveListener() throws ClassNotFoundException {
        List<Element> elements = element.elements("listener");
        List<ListenerResolver> list = new ArrayList<>();
        for (Element listener : elements) {
            Element clazz = listener.element("listener-class");
            if (clazz == null) {
                throw new NullPointerException("listenner class not set");
            }
            Class listenerClass = ClassUtils.getClass(clazz.getStringValue());

            ListenerResolver listenerResolver = new ListenerResolver();
            listenerResolver.setClazz(listenerClass);
            list.add(listenerResolver);
        }
        return list;
    }

    private List<FilterResolver> _resolveFilter() throws ClassNotFoundException {
        List<Element> elements = element.elements("filter");
        List<FilterResolver> list = new ArrayList<>();
        for (Element listener : elements) {
            Element clazz = listener.element("filter-class");
            if (clazz == null) {
                throw new NullPointerException("filter class not set");
            }
            Class listenerClass = ClassUtils.getClass(clazz.getStringValue());

            FilterResolver filterResolver = new FilterResolver();
            filterResolver.setClazz(listenerClass);
            list.add(filterResolver);
        }
        return list;
    }

}
