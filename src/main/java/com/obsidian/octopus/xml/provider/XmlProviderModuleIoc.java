package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.IocResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import java.util.Iterator;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou
 */
public class XmlProviderModuleIoc implements XmlProviderInterface<ModuleResolver> {

    @Override
    public void process(ModuleResolver resolver, Element element)
            throws Exception {
        IocResolver iocResolver = new IocResolver();
        Iterator it = element.attributeIterator();
        while (it.hasNext()) {
            Attribute attribute = (Attribute) it.next();
            BeanUtils.setProperty(iocResolver, attribute.getName(), attribute.getValue());
        }
        resolver.setIocResolver(iocResolver);
    }

}
