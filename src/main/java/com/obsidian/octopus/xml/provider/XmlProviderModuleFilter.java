package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.FilterResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class XmlProviderModuleFilter implements XmlProviderInterface<ModuleResolver> {

    @Override
    public void process(ModuleResolver resolver, Element element)
            throws Exception {
        FilterResolver filterResolver = new FilterResolver();
        Element classElement = element.element("filter-class");
        if (classElement == null || classElement.getStringValue().isEmpty()) {
            throw new NullPointerException("octopus: filter-class is empty");
        }
        BeanUtils.setProperty(filterResolver, "clazz", classElement.getStringValue());
        Element group = element.element("group");
        BeanUtils.setProperty(filterResolver, "group", group.getStringValue());
        resolver.addFilterResolver(filterResolver);
    }

}
