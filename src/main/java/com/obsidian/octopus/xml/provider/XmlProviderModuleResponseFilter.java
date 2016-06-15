package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.ModuleResolver;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou
 */
public class XmlProviderModuleResponseFilter implements XmlProviderInterface<ModuleResolver> {

    @Override
    public void process(ModuleResolver resolver, Element element)
            throws Exception {
        BeanUtils.setProperty(resolver, "responseFilter", element.getStringValue());
    }

}
