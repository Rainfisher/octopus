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
import com.obsidian.octopus.utils.Logger;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * A HTTP response message.
 *
 * @author The Apache MINA Project (dev@mina.apache.org)
 * @version $Rev: 581234 $, $Date: 2007-10-02 22:39:48 +0900 (?, 02 10? 2007) $
 */
public class HttpResponseMessage {

    private static final Logger LOGGER = Logger.getInstance(HttpResponseMessage.class);
    /**
     * HTTP response codes
     */
    public static final int HTTP_STATUS_SUCCESS = 200;
    public static final int HTTP_STATUS_BAD_REQUEST = 400;
    public static final int HTTP_STATUS_FORBIDDEN = 403;
    public static final int HTTP_STATUS_NOT_FOUND = 404;
    public static final int HTTP_STATUS_INTERNAL_ERROR = 500;
    /**
     * Map<String, String>
     */
    private final Map<String, String> headers = new HashMap<>();
    /**
     * Storage for body of HTTP response.
     */
    private final ByteArrayOutputStream body = new ByteArrayOutputStream(1024);
    private int responseCode = HTTP_STATUS_SUCCESS;

    public HttpResponseMessage() {
//          headers.put("Server", "HttpServer (" + Server.VERSION_STRING + ')');
        headers.put("Cache-Control", "private");
        headers.put("Content-Type", "text/html; charset=iso-8859-1");
        headers.put("Connection", "keep-alive");
        headers.put("Keep-Alive", "200");
        headers.put("Date", new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss zzz").format(new Date()));
        headers.put("Last-Modified", new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss zzz").format(new Date()));
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
    
    public void setHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setContentType(String contentType) {
        headers.put("Content-Type", contentType);
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public void appendBody(byte[] b) {
        try {
            body.write(b);
        } catch (IOException ex) {
            LOGGER.warn("appendBody byte", ex);
        }
    }

    public void appendBody(String s) {
        try {
            body.write(s.getBytes());
        } catch (IOException ex) {
            LOGGER.warn("appendBody String", ex);
        }
    }

    public IoBuffer getBody() {
        return IoBuffer.wrap(body.toByteArray());
    }

    public int getBodyLength() {
        return body.size();
    }

    @Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("headers", headers);
        obj.put("body", body.toString());
        return obj.toString();
    }
}