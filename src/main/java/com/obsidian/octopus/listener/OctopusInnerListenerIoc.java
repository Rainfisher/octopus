package com.obsidian.octopus.listener;

import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.resolver.IocResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.utils.Logger;

/**
 *
 * @author alex
 */
public class OctopusInnerListenerIoc implements OctopusInnerListener {

    private static final Logger LOGGER = Logger.getInstance(OctopusInnerListenerIoc.class);

    @Override
    public void onStart(Context context, ModuleResolver resolver) throws Exception {
        LOGGER.debug("octopus: process ioc........");
        IocResolver iocResolver = resolver.getIocResolver();
        Class clazz = iocResolver.getIocClass();
        if (clazz == null) {
            throw new NullPointerException("octopus: ioc class is empty");
        }
        IocInstanceProvider iocProvide = (IocInstanceProvider) clazz.newInstance();
        context.setIocProvide(iocProvide);
    }

    @Override
    public void onDestroy(Context context) {
    }

}
