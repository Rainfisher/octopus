package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.ListenerResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class XmlProviderModuleListener implements XmlProviderInterface<ModuleResolver> {

    @Override
    public void process(ModuleResolver resolver, Element element)
            throws Exception {
        ListenerResolver listenerResolver = new ListenerResolver();

        Element classElement = element.element("listener-class");
        if (classElement == null || classElement.getStringValue().isEmpty()) {
            throw new NullPointerException("octopus: listener-class is empty");
        }
        BeanUtils.setProperty(listenerResolver, "listenerClass", classElement.getStringValue());
        resolver.addListenerResolver(listenerResolver);
    }

}
