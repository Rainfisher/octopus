package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.utils.FileUtils;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou
 */
public class XmlProviderModuleLog4j implements XmlProviderInterface<ModuleResolver> {

    @Override
    public void process(ModuleResolver resolver, Element element)
            throws Exception {
        String path = FileUtils.getReplacePath(element.getStringValue());
        resolver.setLog4j(path);
    }

}
