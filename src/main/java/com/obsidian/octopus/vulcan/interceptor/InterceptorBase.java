package com.obsidian.octopus.vulcan.interceptor;

import com.obsidian.octopus.vulcan.annotation.ActionBy;
import com.obsidian.octopus.vulcan.core.Action;
import com.obsidian.octopus.vulcan.core.Router;
import com.obsidian.octopus.vulcan.exception.ActionNotFoundException;
import com.obsidian.octopus.vulcan.object.ActionContext;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author alex
 */
@Singleton
public class InterceptorBase implements Interceptor {

    @Inject
    private Router router;

    @Override
    public boolean intercept(ActionInvocation invocation) throws Exception {
        String actionName = (String) ActionContext.get(ActionContext.REQUEST_CONTEXT);
        Action action = router.getAction(actionName);
        if (action == null) {
            throw new ActionNotFoundException();
        }
        invocation.setAction(action);
        ActionContext.set(ActionContext.ACTION, action);

        Class<? extends Action> clazz = action.getClass();
        ActionBy actionBy = clazz.getAnnotation(ActionBy.class);
        if (actionBy != null) {
            Class<? extends Interceptor>[] interceptors = actionBy.interceptors();
            for (Class<? extends Interceptor> interceptor : interceptors) {
                invocation.addInterceptor(interceptor);
            }
        }

        return invocation.invoke();
    }

}
