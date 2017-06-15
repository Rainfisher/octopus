package com.obsidian.octopus.listener;

import com.obsidian.octopus.configuration.ConfigurationHotLoader;
import com.obsidian.octopus.configuration.ConfigurationLoader;
import com.obsidian.octopus.configuration.ConfigurationLoaderFactory;
import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.resolver.ConfigResolver;
import com.obsidian.octopus.resolver.ModuleResolver;
import com.obsidian.octopus.utils.Logger;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.BooleanUtils;

/**
 *
 * @author alex
 */
public class OctopusInnerListenerConfig implements OctopusInnerListener {

    private static final Logger LOGGER = Logger.getInstance(OctopusInnerListenerConfig.class);

    @Override
    public void onStart(Context context, ModuleResolver resolver) throws Exception {
        LOGGER.debug("octopus: process config........");
        List<ConfigResolver> configResolvers = resolver.getConfigResolvers();
        ConfigurationHotLoader hotLoader = context.getConfigurationHotLoader();
        for (ConfigResolver configResolver : configResolvers) {
            ConfigurationLoader loader = ConfigurationLoaderFactory.build(configResolver);
            if (loader == null) {
                continue;
            }
            if (BooleanUtils.isTrue(configResolver.isHotLoad())) {
                hotLoader.addConfigurationLoader(loader);
            }
            context.addConfigurationLoader(configResolver, loader);
        }
        Map<ConfigResolver, ConfigurationLoader> map = context.getConfigurationLoaderMap();
        for (Map.Entry<ConfigResolver, ConfigurationLoader> entry : map.entrySet()) {
            ConfigResolver configResolver = entry.getKey();
            ConfigurationLoader loader = entry.getValue();
            if (BooleanUtils.isTrue(configResolver.isHotLoad())) {
                if (BooleanUtils.isTrue(configResolver.isLoadOnStart())) {
                    loader.process(false);
                }
            } else {
                loader.process(false);
            }
        }
    }

    @Override
    public void onDestroy(Context context) {
    }

}
