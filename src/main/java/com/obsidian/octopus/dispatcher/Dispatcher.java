package com.obsidian.octopus.dispatcher;

import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.context.ContextProvider;
import com.obsidian.octopus.listener.OctopusInnerListener;
import com.obsidian.octopus.listener.OctopusInnerListenerManager;
import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.resolver.Resolver;
import java.util.List;

/**
 *
 * @author Alex Chou
 */
public abstract class Dispatcher {

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

            List<OctopusInnerListener> listeners = OctopusInnerListenerManager.getListeners();
            for (OctopusInnerListener listener : listeners) {
                listener.onStart(context, moduleResolver);
            }
        }

    }

}
