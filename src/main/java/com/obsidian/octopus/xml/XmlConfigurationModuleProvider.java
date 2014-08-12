package com.obsidian.octopus.xml;

import com.obsidian.octopus.resolver.ConfigResolver;
import com.obsidian.octopus.resolver.FilterResolver;
import com.obsidian.octopus.resolver.IocResolver;
import com.obsidian.octopus.resolver.ListenerResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.resolver.QuartzResolver;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ClassUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.quartz.SchedulerException;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class XmlConfigurationModuleProvider {

    private String name;
    private final Element element;

    public XmlConfigurationModuleProvider(Element element) {
        this.element = element;
    }

    public String getName() {
        return name;
    }

    public ModuleResolver build() throws Exception {
        ModuleResolver moduleResolver = new ModuleResolver();
        name = element.attributeValue("name");
        if (name == null) {
            throw new NullPointerException("xml parse error: startup-name not set");
        }
        List<ConfigResolver> configList = _resolveConfig();
        moduleResolver.setConfigResolvers(configList);

        IocResolver iocResolver = _resolveIoc();
        moduleResolver.setIocResolver(iocResolver);

        List<ListenerResolver> listenerResolvers = _resolveListener();
        moduleResolver.setListenerResolvers(listenerResolvers);

        List<FilterResolver> filterResolvers = _resolveFilter();
        moduleResolver.setFilterResolvers(filterResolvers);

        _resolveQuartzConfig();
        QuartzResolver quartzResolver = _resolveQuartz();
        moduleResolver.setQuartzResolver(quartzResolver);

        return moduleResolver;
    }

    private List<ConfigResolver> _resolveConfig() throws IllegalAccessException, InvocationTargetException {
        List<Element> elements = element.elements("config");
        List<ConfigResolver> list = new ArrayList<>();
        for (Element config : elements) {
            ConfigResolver configResolver = new ConfigResolver();
            BeanUtils.setProperty(configResolver, "name", config.attributeValue("name"));
            List<Element> params = config.elements("param");
            for (Element param : params) {
                BeanUtils.setProperty(configResolver, param.attributeValue("name"),
                        param.getStringValue());
            }
            list.add(configResolver);
        }
        return list;
    }

    private IocResolver _resolveIoc() throws IllegalAccessException, InvocationTargetException {
        IocResolver iocResolver = new IocResolver();
        Element ioc = element.element("ioc");
        if (ioc != null) {
            iocResolver = new IocResolver();
            Iterator it = ioc.attributeIterator();
            while (it.hasNext()) {
                Attribute attribute = (Attribute) it.next();
                BeanUtils.setProperty(iocResolver, attribute.getName(), attribute.getValue());
            }
        }
        return iocResolver;
    }

    private List<ListenerResolver> _resolveListener() throws IllegalAccessException, InvocationTargetException {
        List<Element> elements = element.elements("listener");
        List<ListenerResolver> list = new ArrayList<>();
        for (Element listener : elements) {
            ListenerResolver listenerResolver = new ListenerResolver();

            Element classElement = listener.element("listener-class");
            if (classElement == null || classElement.getStringValue().isEmpty()) {
                throw new NullPointerException("octopus: listener-class is empty");
            }
            BeanUtils.setProperty(listenerResolver, "listenerClass", classElement.getStringValue());
            list.add(listenerResolver);
        }
        return list;
    }

    private List<FilterResolver> _resolveFilter() throws IllegalAccessException, InvocationTargetException {
        List<Element> elements = element.elements("filter");
        List<FilterResolver> list = new ArrayList<>();
        for (Element filter : elements) {
            FilterResolver filterResolver = new FilterResolver();
            Element classElement = filter.element("filter-class");
            if (classElement == null || classElement.getStringValue().isEmpty()) {
                throw new NullPointerException("octopus: filter-class is empty");
            }
            BeanUtils.setProperty(filterResolver, "clazz", classElement.getStringValue());
            Element group = filter.element("group");
            BeanUtils.setProperty(filterResolver, "group", group.getStringValue());
            list.add(filterResolver);
        }
        return list;
    }

    private void _resolveQuartzConfig() {
        Element config = element.element("quartz-config");
        if (config != null) {
            System.setProperty("org.quartz.properties", config.getStringValue());
        }
    }

    private QuartzResolver _resolveQuartz() throws ClassNotFoundException, SchedulerException {
        QuartzResolver quartzResolver = new QuartzResolver();
        List<Element> elements = element.elements("quartz-group");
        for (int i = 0; i < elements.size(); i++) {
            Element group = elements.get(i);
            String groupName = group.attributeValue("id");
            if (groupName == null) {
                groupName = "group-" + (i + 1);
            }
            quartzResolver.setGroupName(groupName);

            List<Element> jobs = group.elements("job");
            for (int j = 0; j < jobs.size(); j++) {
                Element jobElement = jobs.get(j);
                String jobName = jobElement.attributeValue("id");
                if (jobName == null) {
                    jobName = "job-" + (j + 1);
                }
                Element clazz = jobElement.element("job-class");
                if (clazz == null) {
                    throw new NullPointerException("octopus: job class not set");
                }
                Class jobClass = ClassUtils.getClass(clazz.getStringValue());
                QuartzResolver.Job job = new QuartzResolver.Job();
                job.setName(jobName);
                job.setClazz(jobClass);
                quartzResolver.addJob(job);
            }

            List<Element> triggers = group.elements("trigger");
            for (int j = 0; j < triggers.size(); j++) {
                Element triggerElement = triggers.get(j);
                String triggerName = triggerElement.attributeValue("id");
                if (triggerName == null) {
                    triggerName = "trigger-" + (j + 1);
                }

                Element jobsElement = triggerElement.element("jobs");
                String[] jobTasks = null;
                if (jobsElement != null) {
                    jobTasks = jobsElement.getStringValue().split(",");
                }

                QuartzResolver.Trigger trigger = new QuartzResolver.Trigger();
                trigger.setName(triggerName);
                trigger.setJobs(jobTasks);

                Element triggerCron = triggerElement.element("trigger-cron");
                if (triggerCron != null) {
                    trigger.setCron(triggerCron.getStringValue());
                } else {
                    Element delay = triggerElement.element("trigger-delay");
                    int interval = Integer.valueOf(delay.getStringValue());
                    Element repeated = triggerElement.element("trigger-repeated");
                    int repeat = repeated == null ? 0 : Integer.valueOf(repeated.getStringValue());

                    trigger.setDelay(interval);
                    trigger.setRepeated(repeat);
                }
                quartzResolver.addTrigger(trigger);
            }
        }
        return quartzResolver;
    }

}
