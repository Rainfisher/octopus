package com.obsidian.octopus.vulcan.interceptor;

import javax.inject.Singleton;

/**
 *
 * @author alex
 */
@Singleton
public class InterceptorFinal implements Interceptor {

    @Override
    public boolean intercept(ActionInvocation invocation) throws Exception {
        return invocation.getAction().execute();
    }

}
