package com.obsidian.octopus.dispatcher;

import com.obsidian.octopus.configuration.ConfigurationHotLoader;
import com.obsidian.octopus.configuration.ConfigurationLoader;
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
import com.obsidian.octopus.resolver.Resolver;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.BooleanUtils;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
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
            _processConfig();
            _processIoc();
            _processListener();
            _processFilter();

            _contextStart();
        }

        private void _processConfig() throws Exception {
            LOGGER.debug("octopus: process config........");
            List<ConfigResolver> configResolvers = moduleResolver.getConfigResolvers();
            ConfigurationHotLoader hotLoader = context.getConfigurationHotLoader();
            for (ConfigResolver configResolver : configResolvers) {
                ConfigurationLoader loader = new ConfigurationLoader(configResolver);
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

        private void _contextStart() throws Exception {
            IocInstanceProvider iocProvide = context.getIocProvide();

            Map<ConfigResolver, ConfigurationLoader> map = context.getConfigurationLoaderMap();
            for (Map.Entry<ConfigResolver, ConfigurationLoader> entry : map.entrySet()) {
                ConfigResolver configResolver = entry.getKey();
                ConfigurationLoader loader = entry.getValue();

                iocProvide.injectMembers(loader);
                if (BooleanUtils.isTrue(configResolver.isHotLoad())) {
                    if (BooleanUtils.isTrue(configResolver.isLoadOnStart())) {
                        loader.process();
                    }
                } else {
                    loader.process();
                }
            }

            List<OctopusListener> listeners = context.getListeners();
            for (OctopusListener octopusListener : listeners) {
                octopusListener.onStart(context);
            }

            if (moduleResolver.isQuartz()) {
                Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
                sched.setJobFactory(new GuiceJobFactory(iocProvide));
                sched.start();
            }

        }

    }

}
