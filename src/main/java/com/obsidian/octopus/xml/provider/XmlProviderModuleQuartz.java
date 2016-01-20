package com.obsidian.octopus.xml.provider;

import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.resolver.QuartzResolver;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang.ClassUtils;
import org.dom4j.Element;

/**
 *
 * @author Alex Chou
 */
public class XmlProviderModuleQuartz implements XmlProviderInterface<ModuleResolver> {

    private static final AtomicInteger atomicInteger = new AtomicInteger(1);

    @Override
    public void process(ModuleResolver resolver, Element element)
            throws Exception {
        QuartzResolver quartzResolver = new QuartzResolver();

        int i = atomicInteger.getAndIncrement();
        String groupName = element.attributeValue("id");
        if (groupName == null) {
            groupName = "group-" + i;
        }
        quartzResolver.setGroupName(groupName);

        List<Element> jobs = element.elements("job");
        for (int j = 0; j < jobs.size(); j++) {
            Element jobElement = jobs.get(j);
            String jobName = jobElement.attributeValue("id");
            if (jobName == null) {
                jobName = groupName + "-job-" + (j + 1);
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

        List<Element> triggers = element.elements("trigger");
        for (int j = 0; j < triggers.size(); j++) {
            Element triggerElement = triggers.get(j);
            String triggerName = triggerElement.attributeValue("id");
            if (triggerName == null) {
                triggerName = groupName + "-trigger-" + (j + 1);
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
        resolver.addQuartzResolver(quartzResolver);
    }

}
