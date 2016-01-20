package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.ConfigResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.utils.FileUtils;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class XmlProviderModuleConfig implements XmlProviderInterface<ModuleResolver> {

    @Override
    public void process(ModuleResolver resolver, Element element)
            throws Exception {
        ConfigResolver configResolver = new ConfigResolver();
        BeanUtils.setProperty(configResolver, "name", element.attributeValue("name"));
        List<Element> params = element.elements("param");
        for (Element param : params) {
            String name = param.attributeValue("name");
            String value = param.getStringValue();

            if ("path".equals(name)) {
                value = FileUtils.getReplacePath(value);
            }
            BeanUtils.setProperty(configResolver, name, value);
        }
        resolver.addConfigResolver(configResolver);
    }

}
