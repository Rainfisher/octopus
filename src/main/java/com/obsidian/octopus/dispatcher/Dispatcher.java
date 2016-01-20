package com.obsidian.octopus.dispatcher;

import com.obsidian.octopus.configuration.ConfigurationHotLoader;
import com.obsidian.octopus.configuration.ConfigurationLoader;
import com.obsidian.octopus.configuration.ConfigurationLoaderFactory;
import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.context.ContextProvider;
import com.obsidian.octopus.filter.OctopusMinaFilter;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.listener.OctopusListener;
import com.obsidian.octopus.quartz.GuiceJobFactory;
import com.obsidian.octopus.resolver.ConfigResolver;
import com.obsidian.octopus.resolver.FilterResolver;
import com.obsidian.octopus.resolver.IocResolver;
import com.obsidian.octopus.resolver.ListenerResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.resolver.QuartzResolver;
import com.obsidian.octopus.resolver.Resolver;
import com.obsidian.octopus.utils.FileUtils;
import com.obsidian.octopus.utils.QuartzUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.BooleanUtils;
import org.apache.log4j.PropertyConfigurator;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex Chou
 */
public abstract class Dispatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);

    public static Dispatcher createDispatcher() {
        return new DispatcherImpl();
    }

    public abstract void start(Resolver resolver) throws Exception;

    static class DispatcherImpl extends Dispatcher {

        private ModuleResolver moduleResolver;
        private Context context;

        @Override
        public void start(Resolver resolver) throws Exception {
            moduleResolver = resolver.getModuleResolver();
            context = ContextProvider.getInstance();
            _processLog4j();
            _processConfig();

            _contextConfig();

            _processIoc();
            _processListener();
            _processFilter();
            _processQuartz();

            _contextStart();
        }

        private void _processLog4j() {
            LOGGER.debug("octopus: process log4j........");
            String log4j = moduleResolver.getLog4j();
            if (log4j != null) {
                log4j = FileUtils.getReplacePath(log4j);
                PropertyConfigurator.configure(log4j);
            }
        }

        private void _processConfig() throws Exception {
            LOGGER.debug("octopus: process config........");
            List<ConfigResolver> configResolvers = moduleResolver.getConfigResolvers();
            ConfigurationHotLoader hotLoader = context.getConfigurationHotLoader();
            for (ConfigResolver configResolver : configResolvers) {
                ConfigurationLoader loader = ConfigurationLoaderFactory.build(configResolver);
                if (BooleanUtils.isTrue(configResolver.isHotLoad())) {
                    hotLoader.addConfigurationLoader(loader);
                }
                context.addConfigurationLoader(configResolver, loader);
            }
        }

        private void _processIoc() throws Exception {
            LOGGER.debug("octopus: process ioc........");
            IocResolver iocResolver = moduleResolver.getIocResolver();
            Class clazz = iocResolver.getIocClass();
            if (clazz == null) {
                throw new NullPointerException("octopus: ioc class is empty");
            }
            IocInstanceProvider iocProvide = (IocInstanceProvider) clazz.newInstance();
            context.setIocProvide(iocProvide);
        }

        private void _processListener() throws Exception {
            LOGGER.debug("octopus: process listener........");
            IocInstanceProvider iocProvide = context.getIocProvide();
            List<ListenerResolver> listenerResolvers = moduleResolver.getListenerResolvers();
            for (ListenerResolver listenerResolver : listenerResolvers) {
                Class clazz = listenerResolver.getListenerClass();
                if (clazz == null) {
                    throw new NullPointerException("octopus: listener class is empty");
                }
                OctopusListener listener = (OctopusListener) iocProvide.getInstance(clazz);
                context.addListener(listener);
            }
        }

        private void _processFilter() throws Exception {
            LOGGER.debug("octopus: process filter........");
            IocInstanceProvider iocProvide = context.getIocProvide();
            List<FilterResolver> filterResolvers = moduleResolver.getFilterResolvers();
            for (FilterResolver filterResolver : filterResolvers) {
                Class clazz = filterResolver.getClazz();
                if (clazz == null) {
                    throw new NullPointerException("octopus: listener class is empty");
                }
                OctopusMinaFilter minaFilter = (OctopusMinaFilter) iocProvide.getInstance(clazz);
                minaFilter.setName(filterResolver.getGroup());
                context.addFilter(minaFilter);
            }
        }

        private void _processQuartz() throws Exception {
            LOGGER.debug("octopus: process quartz........");
            if (moduleResolver.getQuartzConfig() != null) {
                System.setProperty("org.quartz.properties", moduleResolver.getQuartzConfig());
            }
            List<QuartzResolver> quartzResolvers = moduleResolver.getQuartzResolvers();

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

        private void _contextConfig() throws Exception {
            Map<ConfigResolver, ConfigurationLoader> map = context.getConfigurationLoaderMap();
            for (Map.Entry<ConfigResolver, ConfigurationLoader> entry : map.entrySet()) {
                ConfigResolver configResolver = entry.getKey();
                ConfigurationLoader loader = entry.getValue();
                if (BooleanUtils.isTrue(configResolver.isHotLoad())) {
                    if (BooleanUtils.isTrue(configResolver.isLoadOnStart())) {
                        loader.process();
                    }
                } else {
                    loader.process();
                }
            }
        }

        private void _contextStart() throws Exception {
            IocInstanceProvider iocProvide = context.getIocProvide();

            List<OctopusListener> listeners = context.getListeners();
            for (OctopusListener octopusListener : listeners) {
                octopusListener.onStart(context);
            }

            boolean hotload = false;
            Map<ConfigResolver, ConfigurationLoader> map = context.getConfigurationLoaderMap();
            for (Map.Entry<ConfigResolver, ConfigurationLoader> entry : map.entrySet()) {
                ConfigResolver configResolver = entry.getKey();
                ConfigurationLoader loader = entry.getValue();
                loader.setIocInstanceProvider(iocProvide);

                if (BooleanUtils.isTrue(configResolver.isHotLoad())) {
                    hotload = true;
                }
                loader.triggerCallback(null);
            }

            if (hotload) {
                context.getConfigurationHotLoader().start();
            }

            if (System.getProperty("org.quartz.properties") != null) {
                Scheduler sched = context.getScheduler(true);
                sched.setJobFactory(new GuiceJobFactory(iocProvide));
                sched.start();
            }
        }

    }

}
