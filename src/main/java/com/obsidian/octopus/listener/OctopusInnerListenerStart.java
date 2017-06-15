package com.obsidian.octopus.listener;

import com.obsidian.octopus.configuration.ConfigurationLoader;
import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.quartz.GuiceJobFactory;
import com.obsidian.octopus.resolver.ConfigResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.BooleanUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author alex
 */
public class OctopusInnerListenerStart implements OctopusInnerListener {

    private Scheduler scheduler;

    @Override
    public void onStart(final Context context, ModuleResolver resolver) throws Exception {
        IocInstanceProvider iocProvide = context.getIocProvide();

        boolean hotload = false;
        Map<ConfigResolver, ConfigurationLoader> map = context.getConfigurationLoaderMap();
        for (Map.Entry<ConfigResolver, ConfigurationLoader> entry : map.entrySet()) {
            ConfigResolver configResolver = entry.getKey();
            if (BooleanUtils.isTrue(configResolver.isHotLoad())) {
                hotload = true;
            }
        }

        if (hotload) {
            context.getConfigurationHotLoader().start();
        }

        List<OctopusListener> listeners = context.getListeners();
        for (OctopusListener octopusListener : listeners) {
            octopusListener.onStart(context);
        }

        if (System.getProperty("org.quartz.properties") != null) {
            this.scheduler = StdSchedulerFactory.getDefaultScheduler();
            this.scheduler.setJobFactory(new GuiceJobFactory(iocProvide));
            this.scheduler.start();
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                List<OctopusInnerListener> listeners = OctopusInnerListenerManager.getListeners();
                for (OctopusInnerListener listener : listeners) {
                    listener.onDestroy(context);
                }
            }

        });
    }

    @Override
    public void onDestroy(Context context) {
        List<OctopusListener> listeners = context.getListeners();
        for (OctopusListener octopusListener : listeners) {
            octopusListener.onDestroy(context);
        }
        if (this.scheduler != null) {
            try {
                this.scheduler.shutdown();
            }
            catch (SchedulerException e) {
            }
        }
    }

}
