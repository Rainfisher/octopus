package com.obsidian.octopus.vulcan.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.obsidian.octopus.vulcan.core.Action;
import com.obsidian.octopus.vulcan.object.ActionContext;
import com.obsidian.octopus.vulcan.utils.ActionUtils;
import javax.inject.Singleton;

/**
 *
 * @author alex
 */
@Singleton
public class InterceptorParameter implements Interceptor {

    public static final String GROUP_NAME = "@group";

    @Override
    public boolean intercept(ActionInvocation invocation) throws Exception {
        Action action = invocation.getAction();
        String[] group = (String[]) ActionContext.get(ActionContext.CONTEXT_MATCHER);
        if (group != null) {
            for (int i = 0; i < group.length; i++) {
                String string = group[i];
                ActionContext.addParameter(GROUP_NAME + i, string);
            }
        }
        ActionUtils.inject(action, ActionContext.getParameters());
        return invocation.invoke();
    }

}
