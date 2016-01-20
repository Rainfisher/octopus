package com.obsidian.octopus.xml;

import com.obsidian.octopus.resolver.Resolver;
import com.obsidian.octopus.xml.provider.XmlProviderManager;
import com.obsidian.octopus.xml.provider.XmlProviderModule;
import com.obsidian.octopus.xml.provider.XmlProviderModuleDefault;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou
 */
public class XmlConfigurationProvider {

    private final String moduleName;

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

    public Resolver build() throws Exception {
        Document document = _getDocument("/octopus.xml");
        if (document == null) {
            document = _getDocument("/octopus-default.xml");
        }
        if (document == null) {
            throw new NullPointerException("cant find octopus.xml");
        }
        Resolver resolver = new Resolver();
        resolver.setModuleName(moduleName);

        Element octopus = document.getRootElement();

        XmlProviderManager<Resolver> xmlManager = _getXmlManager();
        xmlManager.reslove(resolver, octopus);
        return resolver;
    }

    private XmlProviderManager<Resolver> _getXmlManager() {
        XmlProviderManager<Resolver> manager = new XmlProviderManager<>();
        manager.register("default-module", new XmlProviderModuleDefault());
        manager.register("module", new XmlProviderModule());
        return manager;
    }

}
