package com.obsidian.octopus.vulcan.utils;

import com.alibaba.fastjson.JSONObject;
import com.obsidian.octopus.vulcan.codec.HttpResponseMessage;
import com.obsidian.octopus.vulcan.codec.ResponseMessage;
import com.obsidian.octopus.vulcan.object.ActionContext;
import com.obsidian.octopus.vulcan.object.ResponseCode;
import java.util.Map;

/**
 *
 * @author alex
 */
public class ResponseUtils {

    public static ResponseMessage error(ResponseCode responseCode) {
        return encode("LogicError", null, responseCode, null);
    }

    public static ResponseMessage response() {
        String context = ActionContext.getResponseContext();
        Map<String, String> headers = ActionContext.getResponseHeaders();
        ResponseCode responseCode = (ResponseCode) ActionContext.get(ActionContext.RESPONSE_CODE);
        JSONObject body = (JSONObject) ActionContext.get(ActionContext.BODY);
        return encode(context, headers, responseCode, body);
    }

    public static ResponseMessage encodeHttp() {
        Integer responseCode = (Integer) ActionContext.get(ActionContext.RESPONSE_CODE);
        Object body = ActionContext.get(ActionContext.BODY);

        ResponseMessage responseMessage = new ResponseMessage();
        if (responseCode != null) {
            responseMessage.setResponseCode(responseCode);
        }

        Map<String, String> headers = ActionContext.getResponseHeaders();
        if (headers != null) {
            responseMessage.setHeaders(headers);
        }
        if (body != null) {
            responseMessage.setBody(body);
        }
        return responseMessage;
    }

    public static ResponseMessage encode(String context, Map<String, String> headers,
            ResponseCode responseCode, JSONObject param) {
        ResponseMessage message = new ResponseMessage();

        JSONObject json = new JSONObject();
        if (context != null) {
            json.put("context", context);
        }
        if (responseCode != null) {
            json.put("response_code", responseCode.getId());
            json.put("response_code_desc", responseCode.name());
        }
        if (param != null) {
            json.put("body", param);
        }

        if (headers != null && !headers.isEmpty()) {
            message.setHeaders(headers);
        }
        message.setBody(json);
        return message;
    }

    public static HttpResponseMessage getHttpResponse(ResponseMessage response) {
        Map<String, String> headers = response.getHeaders();
        Integer responseCode = response.getResponseCode();
        Object body = response.getBody();

        HttpResponseMessage httpResponse = new HttpResponseMessage();
        httpResponse.setContentType("application/json; charset=utf-8");
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpResponse.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if (responseCode != null) {
            httpResponse.setResponseCode(responseCode);
        }
        if (body != null) {
            httpResponse.appendBody(body.toString());
        }
        return httpResponse;
    }

}
