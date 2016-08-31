package com.obsidian.octopus.vulcan.object;

import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.vulcan.interceptor.ActionInvocation;
import com.obsidian.octopus.vulcan.interceptor.InterceptorGlobal;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author alex
 */
@Singleton
public class ActionInvokerImpl implements ActionInvoker {

    @Inject
    private IocInstanceProvider provider;

    @Override
    public boolean execute() throws Exception {
        ActionInvocation actionInvocation = new ActionInvocation(provider);
        actionInvocation.addInterceptor(InterceptorGlobal.class);
        return actionInvocation.invoke();
    }

}
