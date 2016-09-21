package com.obsidian.octopus.vulcan.object;

/**
 *
 * @author alex
 */
public class ActionRequest {

    private String context;
    private String token;
    private Object parameters;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Object getParameters() {
        return parameters;
    }

    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }

}
