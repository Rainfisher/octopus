package com.obsidian.octopus.vulcan.filter;

import com.obsidian.octopus.vulcan.core.Action;

/**
 *
 * @author alex
 */
public interface ResponseFilter {
    
    Object filter(Action action);
    
}
