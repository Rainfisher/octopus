package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.resolver.ProcessorResolver;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ClassUtils;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou
 */
public class XmlProviderModuleProcessor implements XmlProviderInterface<ModuleResolver> {

    @Override
    public void process(ModuleResolver resolver, Element element)
            throws Exception {
        ProcessorResolver processorResolver = new ProcessorResolver();
        Element key = element.element("key");
        if (key == null || key.getStringValue().isEmpty()) {
            throw new NullPointerException("octopus: processor-key is empty");
        }
        BeanUtils.setProperty(processorResolver, "key", key.getStringValue());
        List<Element> elements = element.elements("stack");
        for (Element stack : elements) {
            String clazz = stack.getStringValue();
            if (clazz != null && !clazz.isEmpty()) {
                processorResolver.addStack(ClassUtils.getClass(clazz));
            }
        }
        resolver.addProcessorResolver(processorResolver);
    }

}
