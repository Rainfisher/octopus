package com.obsidian.octopus.utils;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Alex Chou
 */
public class QuartzUtils {

    private static final Logger LOGGER = Logger.getInstance(QuartzUtils.class);

    public static void scheduleJob(JobDetail jobDetail, Trigger trigger)
            throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public static void runNow(JobDetail jobDetail) {
        try {
            SimpleScheduleBuilder simpleSchedule = SimpleScheduleBuilder.simpleSchedule();
            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.startNow();
            triggerBuilder.withSchedule(simpleSchedule);
            com.obsidian.octopus.utils.QuartzUtils.scheduleJob(jobDetail, triggerBuilder.build());
        }
        catch (SchedulerException e) {
            LOGGER.error("QuartzUtils runNow", e);
        }
    }

}
