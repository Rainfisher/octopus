package com.obsidian.octopus.vulcan.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.obsidian.octopus.vulcan.core.Action;
import com.obsidian.octopus.vulcan.object.ActionContext;
import com.obsidian.octopus.vulcan.object.ActionRequest;
import com.obsidian.octopus.vulcan.utils.ActionUtils;
import javax.inject.Singleton;

/**
 *
 * @author alex
 */
@Singleton
public class InterceptorParameter implements Interceptor {

    @Override
    public boolean intercept(ActionInvocation invocation) throws Exception {
        Action action = invocation.getAction();
        ActionRequest actionRequest = ActionContext.getActionRequest();
        JSONObject json = actionRequest.getParameters();
        String[] group = (String[]) ActionContext.get(ActionContext.CONTEXT_MATCHER);
        for (int i = 0; i < group.length; i++) {
            String string = group[i];
            json.put("@group" + i, string);
        }
        ActionUtils.inject(action, json);
        return invocation.invoke();
    }

}
