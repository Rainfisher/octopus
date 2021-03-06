package com.obsidian.octopus.xml.provider;

import org.dom4j.Element;

/**
 *
 * @author Alex Chou
 * @param <E> element
 */
public interface XmlProviderInterface<E> {

    void process(E e, Element element) throws Exception;

}
