package com.obsidian.octopus.utils;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class QuartzUtils {

    public static void ss(JobDetail jobDetail, Trigger trigger)
            throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
    }

}
