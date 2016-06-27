package com.obsidian.octopus.vulcan.object;

import com.google.inject.ImplementedBy;

/**
 *
 * @author alex
 */
@ImplementedBy(ActionInvokerImpl.class)
public interface ActionInvoker {

    boolean execute() throws Exception;

}
