/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package com.obsidian.octopus.vulcan.codec;

import com.alibaba.fastjson.JSONObject;
import java.util.Map;

/**
 * A HTTP request message.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 576402 $, $Date: 2007-09-17 21:37:27 +0900 (?, 17 9? 2007) $
 */
public class HttpRequestMessage extends RequestMessage {

    private String uri;
    private String method;
    private String context;
    private String protocol;

    private Map<String, String> headers = null;
    private Map<String, String[]> parameters = null;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String[] getParameter(String name) {
        String[] param = parameters.get(name);
        return param == null ? new String[]{} : param;
    }

    public Map<String, String[]> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("uri", uri);
        json.put("method", method);
        json.put("context", context);
        json.put("protocol", protocol);
        if (headers != null) {
            json.put("headers", headers);
        }
        if (parameters != null) {
            json.put("parameters", parameters);
        }
        return json.toString();
    }

}
