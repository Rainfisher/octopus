package com.obsidian.octopus.vulcan.interceptor;

import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.utils.Logger;
import com.obsidian.octopus.vulcan.core.Action;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alex
 */
public class ActionInvocation {

    public static final Logger LOGGER = Logger.getInstance(ActionInvocation.class);

    private final IocInstanceProvider iocProvide;
    private Action action;
    private final List<Interceptor> list = new ArrayList<>();
    private int interceptorIndex = 0;

    public ActionInvocation(IocInstanceProvider iocProvide) {
        this.iocProvide = iocProvide;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void addInterceptor(Class<? extends Interceptor> interceptor) {
        Interceptor instance = iocProvide.getInstance(interceptor);
        list.add(instance);
    }

    public boolean invoke() throws Exception {
        boolean ret = true;
        if (interceptorIndex < list.size()) {
            Interceptor interceptor = list.get(interceptorIndex++);
            LOGGER.trace("interceptor intercept: {} action: {}",
                    new Object[]{interceptor, action});
            ret = interceptor.intercept(this);
        }
        return ret;
    }

}
