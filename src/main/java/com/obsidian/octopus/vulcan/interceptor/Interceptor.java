package com.obsidian.octopus.vulcan.interceptor;

/**
 *
 * @author alex
 */
public interface Interceptor {

    boolean intercept(ActionInvocation invocation) throws Exception;

}
