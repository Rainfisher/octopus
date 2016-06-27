package com.obsidian.octopus.vulcan.object;

import com.obsidian.octopus.vulcan.interceptor.ActionInvocation;
import com.obsidian.octopus.vulcan.interceptor.InterceptorGlobal;
import javax.inject.Singleton;

/**
 *
 * @author alex
 */
@Singleton
public class ActionInvokerImpl implements ActionInvoker {
    
    @Override
    public boolean execute() throws Exception {
        ActionInvocation actionInvocation = new ActionInvocation();
        actionInvocation.addInterceptor(InterceptorGlobal.class);
        return actionInvocation.invoke();
    }

}
