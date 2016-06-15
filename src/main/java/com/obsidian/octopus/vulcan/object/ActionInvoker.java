package com.obsidian.octopus.vulcan.object;

import com.google.inject.ImplementedBy;
import com.obsidian.octopus.vulcan.core.Router;

/**
 *
 * @author alex
 */
@ImplementedBy(ActionInvokerImpl.class)
public interface ActionInvoker {

    boolean prepare(Router router);

    void action() throws Exception;

}
