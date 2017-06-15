package com.obsidian.octopus.listener;

import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.resolver.ModuleResolver;

/**
 *
 * @author alex
 */
public interface OctopusInnerListener {
    
    void onStart(Context context, ModuleResolver resolver) throws Exception;
    
    void onDestroy(Context context);
    
}
