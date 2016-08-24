package com.obsidian.octopus.vulcan.object;

import com.obsidian.octopus.vulcan.codec.RequestMessage;
import com.obsidian.octopus.vulcan.core.Action;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alex
 */
public class ActionContext {

    public static final String IO_SESSION = "IO_SESSION";
    public static final String REQUEST_MESSAGE = "REQUEST_MESSAGE";
    public static final String ACTION = "ACTION";
    public static final String ACTION_PRESET = "ACTION_PRESET";
    public static final String ACTION_REQUEST = "ACTION_REQUEST";
    public static final String REQUEST_HEADERS = "REQUEST_HEADERS";
    public static final String REQUEST_CONTEXT = "REQUEST_CONTEXT";
    public static final String REQUEST_PARAMETERS = "REQUEST_PARAMETERS";
    public static final String CONTEXT_MATCHER = "CONTEXT_MATCHER";

    public static final String RESPONSE_HEADERS = "RESPONSE_HEADERS";
    public static final String RESPONSE_CONTEXT = "RESPONSE_CONTEXT";
    public static final String RESPONSE_CODE = "RESPONSE_CODE";
    public static final String HTTP_RESPONSE_CODE = "HTTP_RESPONSE_CODE";
    public static final String BODY = "BODY";
    public static final String RESPONSE = "RESPONSE";

    private static final ThreadLocal<ActionContext> ACTION_CONTEXT = new ThreadLocal<ActionContext>() {

        @Override
        protected ActionContext initialValue() {
            return new ActionContext();
        }

    };

    public static ActionContext getActionContext() {
        return ACTION_CONTEXT.get();
    }

    public static void removeActionContext() {
        ACTION_CONTEXT.remove();
    }

    private final Map<String, Object> context = new HashMap<>();

    public final static void init() {
        getActionContext().context.clear();
    }

    public final static Object get(String key) {
        return getActionContext().context.get(key);
    }

    public final static void set(String key, Object o) {
        getActionContext().context.put(key, o);
    }

    public final static void remove(String key) {
        getActionContext().context.remove(key);
    }

    public static RequestMessage getReqestMessage() {
        return (RequestMessage) get(REQUEST_MESSAGE);
    }

    public static ActionRequest getActionRequest() {
        return (ActionRequest) get(ACTION_REQUEST);
    }

    public static Map<String, String> getRequestHeaders() {
        return (Map<String, String>) get(REQUEST_HEADERS);
    }

    public static Map<String, String> getResponseHeaders() {
        return (Map<String, String>) get(RESPONSE_HEADERS);
    }

    public static void addHeader(String key, Object value) {
        Map<String, String> headers = getResponseHeaders();
        if (headers == null) {
            headers = new HashMap<>();
            set(RESPONSE_HEADERS, headers);
        }
        headers.put(key, value.toString());
    }

    public static void addParameters(Map<String, Object> map) {
        Map<String, Object> maps = getParameters();
        if (maps == null) {
            maps = new HashMap<>();
            set(REQUEST_PARAMETERS, maps);
        }
        maps.putAll(map);
    }

    public static void addParameter(String name, Object value) {
        Map<String, Object> map = getParameters();
        if (map == null) {
            map = new HashMap<>();
            set(REQUEST_PARAMETERS, map);
        }
        map.put(name, value);
    }

    public static Object getParameter(String name) {
        Map<String, Object> map = getParameters();
        if (map == null) {
            return null;
        }
        return map.get(name);
    }

    public static Object removeParameter(String name) {
        Map<String, Object> map = getParameters();
        if (map == null) {
            return null;
        }
        return map.remove(name);
    }

    public static Map<String, Object> getParameters() {
        return (Map) get(REQUEST_PARAMETERS);
    }

    public static Action getAction() {
        return (Action) get(ACTION);
    }

    public static void setResponseCode(ResponseCode responseCode) {
        set(RESPONSE_CODE, responseCode);
    }

    public static String getResponseContext() {
        String responseContext = (String) get(RESPONSE_CONTEXT);
        if (responseContext == null) {
            String requestContext = (String) ActionContext.get(ActionContext.REQUEST_CONTEXT);
            if (requestContext == null) {
                requestContext = "";
            }
            responseContext = requestContext + "Response";
        }
        return responseContext;
    }

}
