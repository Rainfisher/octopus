package com.obsidian.octopus.vulcan.utils;

import com.alibaba.fastjson.JSONObject;
import com.obsidian.octopus.vulcan.codec.RequestMessage;
import com.obsidian.octopus.vulcan.codec.ResponseMessage;
import com.obsidian.octopus.vulcan.object.ActionContext;
import com.obsidian.octopus.vulcan.object.ActionRequest;

/**
 *
 * @author alex
 */
public class MessageLogUtils {

    public static JSONObject getLog(ActionRequest request, ResponseMessage response) {
        JSONObject json = new JSONObject();
        if (request != null) {
            JSONObject requestJson = new JSONObject();
            requestJson.put("headers", ActionContext.getRequestHeaders());
            requestJson.put("context", request.getContext());
            requestJson.put("token", request.getToken());
            requestJson.put("parameters", request.getParameters());
            json.put("request", requestJson);
        }
        if (response != null) {
            JSONObject responseJson = new JSONObject();
            responseJson.put("headers", response.getHeaders());
            if (response.getResponseCode() != null) {
                responseJson.put("response_code", response.getResponseCode());
            }
            if (response.getBody() != null) {
                responseJson.putAll((JSONObject) response.getBody());
            }
            json.put("response", responseJson);
        }
        RequestMessage reqestMessage = ActionContext.getReqestMessage();
        json.put("receive_time", System.currentTimeMillis() - reqestMessage.getReceivedAt());
        json.put("execute_time", System.currentTimeMillis() - reqestMessage.getExecuteAt());
        return json;
    }

}
