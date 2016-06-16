package com.obsidian.octopus.vulcan.object;

import com.alibaba.fastjson.JSONObject;
import com.google.inject.Injector;
import com.obsidian.octopus.utils.Logger;
import com.obsidian.octopus.vulcan.utils.ActionUtils;
import com.obsidian.octopus.vulcan.annotation.InterceptorBy;
import com.obsidian.octopus.vulcan.core.Action;
import com.obsidian.octopus.vulcan.core.Router;
import com.obsidian.octopus.vulcan.exception.ActionInjectException;
import com.obsidian.octopus.vulcan.filter.ResponseFilter;
import com.obsidian.octopus.vulcan.filter.ResponseFilterManager;
import com.obsidian.octopus.vulcan.interceptor.ActionInvocation;
import com.obsidian.octopus.vulcan.interceptor.Interceptor;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author alex
 */
@Singleton
public class ActionInvokerImpl implements ActionInvoker {

    private static final Logger LOGGER = Logger.getInstance(ActionInvoker.class);
    @Inject
    private Injector injector;
    @Inject
    private ResponseFilterManager responseFilterManager;

    @Override
    public boolean prepare(Router router) {
        String actionName = (String) ActionContext.get(ActionContext.REQUEST_CONTEXT);
        Action action = router.getAction(actionName);
        if (action == null) {
            ActionContext.setResponseCode(SystemResponseCode.UNKNOWN_API);
            return false;
        }

        ActionContext.set(ActionContext.ACTION, action);
        return true;
    }

    @Override
    public void action() {
        ActionRequest actionRequest = ActionContext.getActionRequest();
        JSONObject json = actionRequest.getParameters();

        Action action = ActionContext.getAction();
        try {
            ActionUtils.inject(action, json);
        }
        catch (ActionInjectException e) {
            ActionContext.setResponseCode(SystemResponseCode.INVALID_PARAMETER);
            return;
        }

        try {
            Object response = this._action(action);

            if (response != null) {
                ActionContext.setResponseCode(SystemResponseCode.SUCCESS);
                ActionContext.set(ActionContext.BODY, response);
            }
        }
        catch (Exception e) {
            LOGGER.error("ActionInvoke error:", e);
            ActionContext.setResponseCode(SystemResponseCode.UNKNOWN_ERROR);
        }
    }

    private Object _action(Action action) throws Exception {
        boolean result;

        Class<? extends Action> actionClass = action.getClass();
        InterceptorBy interceptorBy = actionClass.getAnnotation(InterceptorBy.class);
        ActionInvocation actionInvocation = new ActionInvocation();
        actionInvocation.setAction(action);

        if (interceptorBy != null) {
            Class<? extends Interceptor> interceptorClass = interceptorBy.value();
            Interceptor instance = injector.getInstance(interceptorClass);
            result = instance.intercept(actionInvocation);
        } else {
            result = action.execute();
        }

        ResponseFilter responseFilter = responseFilterManager.getFilter(action);

        return result ? responseFilter.filter(action) : null;
    }

}
