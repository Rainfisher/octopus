package com.obsidian.octopus.listener;

import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.resolver.QuartzResolver;
import com.obsidian.octopus.utils.Logger;
import com.obsidian.octopus.utils.QuartzUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;

/**
 *
 * @author alex
 */
public class OctopusInnerListenerQuartz implements OctopusInnerListener {

    private static final Logger LOGGER = Logger.getInstance(OctopusInnerListenerQuartz.class);

    @Override
    public void onStart(Context context, ModuleResolver resolver) throws Exception {
        LOGGER.debug("octopus: process quartz........");
        if (resolver.getQuartzConfig() != null) {
            System.setProperty("org.quartz.properties", resolver.getQuartzConfig());
        }
        List<QuartzResolver> quartzResolvers = resolver.getQuartzResolvers();

        for (QuartzResolver quartzResolver : quartzResolvers) {
            Map<String, JobDetail> jobMap = new HashMap<>();
            for (QuartzResolver.Job job : quartzResolver.getJobs()) {
                JobBuilder jobBuilder = JobBuilder.newJob(job.getClazz());
                jobBuilder.withIdentity(job.getName(), quartzResolver.getGroupName());
                JobDetail jobDetail = jobBuilder.build();
                jobMap.put(job.getName(), jobDetail);
            }

            for (QuartzResolver.Trigger trigger : quartzResolver.getTriggers()) {
                ScheduleBuilder scheduleBuilder;
                if (trigger.getCron() == null) {
                    SimpleScheduleBuilder simple = SimpleScheduleBuilder.simpleSchedule();
                    simple.withIntervalInMilliseconds(trigger.getDelay());
                    if (trigger.getRepeated() == 0) {
                        simple.repeatForever();
                    } else {
                        simple.withRepeatCount(trigger.getRepeated());
                    }
                    scheduleBuilder = simple;
                } else {
                    scheduleBuilder = CronScheduleBuilder.cronSchedule(trigger.getCron());
                }

                String[] jobs = trigger.getJobs();
                if (jobs != null) {
                    for (int i = 0; i < jobs.length; i++) {
                        String jobName = jobs[i];
                        JobDetail jobDetail = jobMap.get(jobName);
                        if (jobDetail != null) {
                            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
                            triggerBuilder.withIdentity(trigger.getName() + "-" + i, quartzResolver.getGroupName());
                            triggerBuilder.startNow();
                            triggerBuilder.withSchedule(scheduleBuilder);
                            QuartzUtils.scheduleJob(jobDetail, triggerBuilder.build());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy(Context context) {
    }

}
