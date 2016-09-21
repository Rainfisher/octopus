package com.obsidian.octopus.vulcan.utils;

import com.obsidian.octopus.utils.Logger;
import com.obsidian.octopus.vulcan.annotation.Parameter;
import com.obsidian.octopus.vulcan.core.Action;
import com.obsidian.octopus.vulcan.exception.ActionInjectException;
import com.obsidian.octopus.vulcan.interceptor.InterceptorParameter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author alex
 */
public class ActionUtils {

    private static final Logger LOGGER = Logger.getInstance(ActionUtils.class);

    public static void inject(Action action, Map<String, Object> parameters) {
        if (parameters == null) {
            return;
        }
        Class<? extends Action> actionClass = action.getClass();
        _inject(action, actionClass, parameters);
        _checkSuper(action, actionClass, parameters);
    }
    
    private static void _checkSuper(Action action, Class clazz, Map<String, Object> parameters) {
        Class superclass = clazz.getSuperclass();
        if (superclass != null) {
            _inject(action, superclass, parameters);
            _checkSuper(action, superclass, parameters);
        }
    }

    private static void _inject(Action action, Class clazz, Map<String, Object> parameters) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Parameter parameter = field.getAnnotation(Parameter.class);
            if (parameter == null) {
                continue;
            }
            String name = parameter.name();
            if (name.isEmpty()) {
                name = field.getName();
            }
            if (parameter.groupIndex() >= 0) {
                name = InterceptorParameter.GROUP_NAME + parameter.groupIndex();
            }
            Object value = parameters.get(name.toLowerCase());
            if (value == null) {
                if (!parameter.optional()) {
                    throw new ActionInjectException();
                }
            } else {
                try {
                    BeanUtils.setProperty(action, field.getName(), value);
                }
                catch (IllegalAccessException | InvocationTargetException e) {
                    LOGGER.debug("inject", e);
                    throw new ActionInjectException();
                }
            }
        }
    }

}
