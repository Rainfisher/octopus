package com.obsidian.octopus.vulcan.object;

import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author alex
 */
public class ActionRequest {

    private String context;
    private String token;
    private JSONObject parameters;

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

    public JSONObject getParameters() {
        return parameters;
    }

    public void setParameters(JSONObject parameters) {
        this.parameters = parameters;
    }

}
