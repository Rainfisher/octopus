package com.obsidian.octopus.vulcan.utils;

import com.obsidian.octopus.vulcan.codec.HttpRequestMessage;
import java.util.Map;

/**
 *
 * @author alex
 */
public class HttpUtils {

    public static boolean isKeepAlive(HttpRequestMessage httpRequestMessage) {
        String protocol = httpRequestMessage.getProtocol();
        Map<String, String> headers = httpRequestMessage.getHeaders();
        boolean ret = true;
        if ("HTTP/1.0".equals(protocol)) {
            ret = false;
        }

        String connection = headers.get("connection");
        if (connection == null) {
            return ret;
        }
        if ("Keep-Alive".equals(connection)) {
            ret = true;
        }
        return ret;
    }

}
