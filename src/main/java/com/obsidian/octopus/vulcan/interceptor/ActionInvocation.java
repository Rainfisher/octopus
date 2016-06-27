package com.obsidian.octopus.vulcan.interceptor;

import com.obsidian.octopus.context.ContextProvider;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.vulcan.core.Action;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
public class ActionInvocation {

    private Action action;
    private final List<Class<? extends Interceptor>> list = new ArrayList<>();
    private int interceptorIndex = 0;

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void addInterceptor(Class<? extends Interceptor> interceptor) {
        list.add(interceptor);
    }

    public boolean invoke() throws Exception {
        IocInstanceProvider iocProvide = ContextProvider.getInstance().getIocProvide();

        boolean ret = true;
        if (interceptorIndex < list.size()) {
            Class<? extends Interceptor> interceptor = list.get(interceptorIndex++);
            Interceptor instance = iocProvide.getInstance(interceptor);
            ret = instance.intercept(this);
        }
        return ret;
    }

}
