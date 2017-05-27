package com.obsidian.octopus.configuration;

import com.obsidian.octopus.resolver.ConfigResolver;

/**
 *
 * @author Alex Chou
 */
public interface ConfigurationCallback {

    void trigger(ConfigResolver resolver, String name, Object data, boolean hotLoad);

}
