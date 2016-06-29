package com.obsidian.octopus.vulcan.core;

import com.google.inject.ImplementedBy;
import com.obsidian.octopus.vulcan.object.RouterImpl;

/**
 *
 * @author alex
 */
@ImplementedBy(RouterImpl.class)
public interface Router {

    Action getAction(String context);

}
