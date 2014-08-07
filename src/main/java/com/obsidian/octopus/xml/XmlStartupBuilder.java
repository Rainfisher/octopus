package com.obsidian.octopus.xml;

import com.obsidian.octopus.config.ConfigDescribe;
import com.obsidian.octopus.dispatcher.DispatcherBuilder;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class XmlStartupBuilder {

    private String name;
    private final DispatcherBuilder dispatcherBuilder;
    private final Element element;
    private final DispatcherBuilder.DispatcherDetail dispatcherDetail;

    public XmlStartupBuilder(DispatcherBuilder dispatcherBuilder, Element element) {
        this.dispatcherBuilder = dispatcherBuilder;
        this.element = element;
        this.dispatcherDetail = new DispatcherBuilder.DispatcherDetail();
    }

    public void build() {
        name = element.attributeValue("name");
        if (name == null) {
            throw new NullPointerException("xml parse error: startup-name not set");
        }
        _parseConfig();

        dispatcherBuilder.putStartup(name, dispatcherDetail);
    }

    private void _parseConfig() {
        List<Element> elements = element.elements("config");
        for (Element config : elements) {
            ConfigDescribe configDescribe = new ConfigDescribe();
            try {
                BeanUtils.setProperty(configDescribe, "name", config.attributeValue("name"));
            } catch (IllegalAccessException | InvocationTargetException e) {
            }
            List<Element> params = config.elements("param");
            for (Element param : params) {
                try {
                    BeanUtils.setProperty(configDescribe, param.attributeValue("name"),
                            param.getStringValue());
                } catch (IllegalAccessException | InvocationTargetException e) {
                }
            }
            this.dispatcherDetail.addConfig(configDescribe);
        }
    }

}
