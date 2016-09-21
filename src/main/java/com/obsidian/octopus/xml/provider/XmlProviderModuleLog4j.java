package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.utils.FileUtils;
import com.obsidian.octopus.utils.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou
 */
public class XmlProviderModuleLog4j implements XmlProviderInterface<ModuleResolver> {

    private static final Logger LOGGER = Logger.getInstance(XmlProviderModuleLog4j.class);

    @Override
    public void process(ModuleResolver resolver, Element element)
            throws Exception {
        LOGGER.debug("octopus: process log4j........");
        String path = FileUtils.getReplacePath(element.getStringValue());
        PropertyConfigurator.configure(path);
    }

}
