package com.obsidian.octopus.vulcan.interceptor;

import com.obsidian.octopus.vulcan.core.Action;

/**
 *
 * @author alex
 */
public class EmptyInterceptor implements Interceptor {

    @Override
    public boolean intercept(ActionInvocation invocation) throws Exception {
        Action action = invocation.getAction();
        return action.execute();
    }

}
