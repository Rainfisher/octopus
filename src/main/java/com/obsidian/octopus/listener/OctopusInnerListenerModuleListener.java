package com.obsidian.octopus.listener;

import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.resolver.ListenerResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.utils.Logger;
import java.util.List;

/**
 *
 * @author alex
 */
public class OctopusInnerListenerModuleListener implements OctopusInnerListener {

    private static final Logger LOGGER = Logger.getInstance(OctopusInnerListenerModuleListener.class);

    @Override
    public void onStart(Context context, ModuleResolver resolver) throws Exception {
        LOGGER.debug("octopus: process listener........");
        IocInstanceProvider iocProvide = context.getIocProvide();
        List<ListenerResolver> listenerResolvers = resolver.getListenerResolvers();
        for (ListenerResolver listenerResolver : listenerResolvers) {
            Class clazz = listenerResolver.getListenerClass();
            if (clazz == null) {
                throw new NullPointerException("octopus: listener class is empty");
            }
            OctopusListener listener = (OctopusListener) iocProvide.getInstance(clazz);
            context.addListener(listener);
        }
    }

    @Override
    public void onDestroy(Context context) {
    }

}
