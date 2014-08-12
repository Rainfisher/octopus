package com.obsidian.octopus.quartz;

import com.obsidian.octopus.ioc.IocInstanceProvider;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.SimpleJobFactory;
import org.quartz.spi.TriggerFiredBundle;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class GuiceJobFactory extends SimpleJobFactory {

    private final IocInstanceProvider iocInstanceProvider;

    public GuiceJobFactory(IocInstanceProvider iocInstanceProvider) {
        this.iocInstanceProvider = iocInstanceProvider;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = bundle.getJobDetail();
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        return iocInstanceProvider.getInstance(jobClass);
    }

}
