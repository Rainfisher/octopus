package com.obsidian.octopus.listener;

import com.obsidian.octopus.configuration.ConfigurationLoader;
import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.resolver.ConfigResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author alex
 */
public class OctopusInnerListenerConfigCallback implements OctopusInnerListener {

    @Override
    public void onStart(Context context, ModuleResolver resolver) throws Exception {
        IocInstanceProvider iocProvide = context.getIocProvide();
        Map<ConfigResolver, ConfigurationLoader> map = context.getConfigurationLoaderMap();
        for (Map.Entry<ConfigResolver, ConfigurationLoader> entry : map.entrySet()) {
            ConfigurationLoader loader = entry.getValue();
            loader.setIocInstanceProvider(iocProvide);

            Set<String> names = loader.getNames();
            for (String name : names) {
                loader.triggerCallback(name, null, false);
            }
        }
    }

    @Override
    public void onDestroy(Context context) {
    }

}
