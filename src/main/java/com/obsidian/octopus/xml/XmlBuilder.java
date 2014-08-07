package com.obsidian.octopus.xml;

import com.obsidian.octopus.dispatcher.Dispatcher;
import com.obsidian.octopus.dispatcher.DispatcherBuilder;
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
public class XmlBuilder {

    private String startupName;
    private final DispatcherBuilder dispatcherBuilder;

    public XmlBuilder(String startupName) {
        this.startupName = startupName;
        this.dispatcherBuilder = new DispatcherBuilder();
        this.dispatcherBuilder.setStartupName(startupName);
    }

    private Document _getDocument(String name) {
        Document document;
        try (InputStream inputStream = Thread.currentThread().getClass().getResourceAsStream(name)) {
            if (inputStream == null) {
                return null;
            }
            String xml = IOUtils.toString(inputStream);
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException | IOException e) {
            document = null;
        }
        return document;
    }

    private void _parse() throws IOException, DocumentException {
        Document document = _getDocument("/octopus.xml");
        if (document == null) {
            document = _getDocument("/octopus-default.xml");
        }
        if (document == null) {
            throw new NullPointerException("cant find octopus.xml");
        }
        Element octopus = document.getRootElement();
        _parseDefaultStartup(octopus);
        _parseStartups(octopus);
    }

    private void _parseDefaultStartup(Element octopus) {
        if (startupName == null) {
            Element defaultStartup = octopus.element("default-startup");
            if (defaultStartup == null) {
                throw new NullPointerException("xml parse error: default-startup not set");
            }
            startupName = defaultStartup.getStringValue();
        }
        this.dispatcherBuilder.setStartupName(startupName);
    }

    private void _parseStartups(Element octopus) {
        List<Element> elements = octopus.elements("startup");
        for (Element element : elements) {
            XmlStartupBuilder startupBuilder = new XmlStartupBuilder(dispatcherBuilder, element);
            startupBuilder.build();
        }
    }

    public Dispatcher build() throws IOException, DocumentException {
        _parse();
        return dispatcherBuilder.build();
    }

}
