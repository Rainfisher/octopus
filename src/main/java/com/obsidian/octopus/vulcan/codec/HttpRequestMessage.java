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

import java.util.Map;

/**
 * A HTTP request message.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 576402 $, $Date: 2007-09-17 21:37:27 +0900 (?, 17 9? 2007) $
 */
public class HttpRequestMessage extends RequestMessage {

    public static String PARAMETER_KEY = "@";
    private Map<String, String[]> headers = null;

    public void setHeaders(Map<String, String[]> headers) {
        this.headers = headers;
    }

    public Map<String, String[]> getHeaders() {
        return headers;
    }

    public String getContext() {
        String[] context = headers.get("Context");
        return context == null ? "" : context[0];
    }

    public String getParameter(String name) {
        String[] param = headers.get(PARAMETER_KEY.concat(name));
        return param == null || param.length == 0 ? null : param[0];
    }

    public String[] getParameters(String name) {
        String[] param = headers.get(PARAMETER_KEY.concat(name));
        return param == null ? new String[]{} : param;
    }

    public String[] getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        for (Map.Entry<String, String[]> e : headers.entrySet()) {
            str.append(e.getKey()).append(": ").append(arrayToString(e.getValue(), ',')).append(", ");
        }
        str.append("}");
        return str.toString();
    }

    public static String arrayToString(String[] s, char sep) {
        if (s == null || s.length == 0) {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < s.length; i++) {
            if (i > 0) {
                buf.append(sep);
            }
            buf.append(s[i]);
        }
        return buf.toString();
    }
}
