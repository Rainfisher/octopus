package com.obsidian.octopus.vulcan.codec;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alex
 */
public class ResponseMessage {

    private Map<String, String> headers = new HashMap<>();
    private Integer responseCode;
    private Object body;
    private boolean push;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

}
