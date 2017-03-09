package com.obsidian.octopus.vulcan.object;

import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.vulcan.interceptor.ActionInvocation;
import com.obsidian.octopus.vulcan.interceptor.Interceptor;
import com.obsidian.octopus.vulcan.interceptor.InterceptorGlobal;
import javax.inject.Inject;

/**
 *
 * @author alex
 */
public class ActionInvokerImpl implements ActionInvoker {

    @Inject
    private IocInstanceProvider provider;

    private Class<? extends Interceptor> globalClass = InterceptorGlobal.class;

    public Class<? extends Interceptor> getGlobalClass() {
        return globalClass;
    }

    public void setGlobalClass(Class<? extends Interceptor> globalClass) {
        this.globalClass = globalClass;
    }

    @Override
    public boolean execute() throws Exception {
        ActionInvocation actionInvocation = new ActionInvocation(provider);
        actionInvocation.addInterceptor(globalClass);
        return actionInvocation.invoke();
    }

}
