package com.obsidian.octopus.xml;

import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.resolver.Resolver;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class XmlConfigurationProvider {

    private String moduleName;

    public XmlConfigurationProvider(String moduleName) {
        this.moduleName = moduleName;
    }

    private Document _getDocument(String name) {
        Document document;
        try (InputStream inputStream = Thread.currentThread().getClass().getResourceAsStream(name)) {
            if (inputStream == null) {
                return null;
            }
            String xml = IOUtils.toString(inputStream);
            document = DocumentHelper.parseText(xml);
        }
        catch (DocumentException | IOException e) {
            document = null;
        }
        return document;
    }

    private void _resolveDefaultModule(Resolver resolver, Element octopus) {
        if (moduleName == null) {
            Element defaultModule = octopus.element("default-module");
            if (defaultModule == null) {
                throw new NullPointerException("xml parse error: default-module not set");
            }
            moduleName = defaultModule.getStringValue();
        }
        resolver.setModuleName(moduleName);
    }

    private void _resolveModules(Resolver resolver, Element octopus)
            throws Exception {
        List<Element> elements = octopus.elements("module");
        for (Element element : elements) {
            XmlConfigurationModuleProvider xmlModuleResolver = new XmlConfigurationModuleProvider(element);
            ModuleResolver moduleResolver = xmlModuleResolver.build();
            resolver.putModule(xmlModuleResolver.getName(), moduleResolver);
        }
    }

    public Resolver build() throws Exception {
        Document document = _getDocument("/octopus.xml");
        if (document == null) {
            document = _getDocument("/octopus-default.xml");
        }
        if (document == null) {
            throw new NullPointerException("cant find octopus.xml");
        }
        Resolver resolver = new Resolver();
        Element octopus = document.getRootElement();
        _resolveDefaultModule(resolver, octopus);
        _resolveModules(resolver, octopus);
        return resolver;
    }

}
