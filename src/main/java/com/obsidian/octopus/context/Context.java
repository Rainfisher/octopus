package com.obsidian.octopus.context;

import com.obsidian.octopus.configuration.ConfigurationHotLoader;
import com.obsidian.octopus.configuration.ConfigurationLoader;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.listener.OctopusListener;
import com.obsidian.octopus.resolver.ConfigResolver;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Alex Chou
 */
public interface Context {
    
    ConfigurationHotLoader getConfigurationHotLoader();

    Map<ConfigResolver, ConfigurationLoader> getConfigurationLoaderMap();

    void addConfigurationLoader(ConfigResolver configResolver, ConfigurationLoader loader);

    void setIocProvide(IocInstanceProvider iocProvide);

    IocInstanceProvider getIocProvide();

    void addListener(OctopusListener listener);

    List<OctopusListener> getListeners();

}
