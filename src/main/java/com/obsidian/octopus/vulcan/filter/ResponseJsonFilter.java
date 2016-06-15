package com.obsidian.octopus.vulcan.filter;

import com.alibaba.fastjson.JSONObject;
import com.obsidian.octopus.utils.Logger;
import com.obsidian.octopus.vulcan.annotation.Response;
import com.obsidian.octopus.vulcan.core.Action;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alex
 */
public class ResponseJsonFilter implements ResponseFilter {

    private static final Logger LOGGER = Logger.getInstance(ResponseJsonFilter.class);

    @Override
    public Object filter(Action action) {
        JSONObject json = new JSONObject();

        Class<? extends Action> actionClass = action.getClass();
        Method[] methods = actionClass.getMethods();
        for (Method method : methods) {
            Response response = method.getAnnotation(Response.class);
            if (response == null) {
                continue;
            }
            Object o = null;
            try {
                o = MethodUtils.invokeMethod(action, method.getName(), null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                LOGGER.debug("response", e);
            }
            if (o != null) {
                if (response.root() && o instanceof JSONObject) {
                    json.putAll((JSONObject) o);
                } else {
                    String name = response.name();
                    if (name.isEmpty()) {
                        name = method.getName();
                        name = name.substring(3, name.length());
                        name = StringUtils.uncapitalize(name);
                    }
                    json.put(name, o);
                }
            }
        }

        return json;
    }

}
