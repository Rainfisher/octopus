package com.obsidian.octopus.xml.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou
 * @param <E> element
 */
public class XmlProviderManager<E> {

    private final Map<String, XmlProviderInterface<E>> mapping;

    public XmlProviderManager() {
        this.mapping = new HashMap<>();
    }

    public void register(String name, XmlProviderInterface<E> provider) {
        mapping.put(name, provider);
    }

    private void _process(String name, E e, Element element)
            throws Exception {
        if (mapping.containsKey(name)) {
            XmlProviderInterface<E> provider = mapping.get(name);
            provider.process(e, element);
        }
    }

    public void reslove(E e, Element element)
            throws Exception {
        List<Element> elements = element.elements();
        for (Element ele : elements) {
            String name = ele.getName();
            _process(name, e, ele);
        }
    }

}
