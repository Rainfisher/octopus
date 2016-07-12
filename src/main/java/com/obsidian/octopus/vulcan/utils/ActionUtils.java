package com.obsidian.octopus.vulcan.utils;

import com.alibaba.fastjson.JSONObject;
import com.obsidian.octopus.utils.Logger;
import com.obsidian.octopus.vulcan.annotation.Parameter;
import com.obsidian.octopus.vulcan.core.Action;
import com.obsidian.octopus.vulcan.exception.ActionInjectException;
import com.obsidian.octopus.vulcan.interceptor.InterceptorParameter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author alex
 */
public class ActionUtils {

    private static final Logger LOGGER = Logger.getInstance(ActionUtils.class);

    public static void inject(Action action, JSONObject param) {
        Class<? extends Action> actionClass = action.getClass();
        Field[] declaredFields = actionClass.getDeclaredFields();
        for (Field field : declaredFields) {
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
            Object value = param.get(name);
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
