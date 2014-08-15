package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.Resolver;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class XmlProviderModuleDefault implements XmlProviderInterface<Resolver> {

    @Override
    public void process(Resolver resolver, Element element) {
        if (resolver.getModuleName() == null) {
            String moduleName = element.getStringValue();
            resolver.setModuleName(moduleName);
        }
    }

}
