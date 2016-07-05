package com.obsidian.octopus.vulcan.utils;

import com.obsidian.octopus.vulcan.codec.HttpResponseMessage;
import com.obsidian.octopus.vulcan.codec.ResponseMessage;
import com.obsidian.octopus.vulcan.object.ActionContext;
import java.util.Map;

/**
 *
 * @author alex
 */
public class ResponseUtils {

    public static ResponseMessage encodeHttp() {
        Integer responseCode = (Integer) ActionContext.get(ActionContext.HTTP_RESPONSE_CODE);
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

    public static HttpResponseMessage getHttpResponse(ResponseMessage response) {
        Map<String, String> headers = response.getHeaders();
        Integer responseCode = response.getResponseCode();
        Object body = response.getBody();

        HttpResponseMessage httpResponse = new HttpResponseMessage();
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpResponse.setHeader(entry.getKey(), entry.getValue());
            }
        }
        if (responseCode != null) {
            httpResponse.setResponseCode(responseCode);
        }
        if (body != null) {
            if (body instanceof byte[]) {
                httpResponse.appendBody((byte[]) body);
            } else {
                httpResponse.appendBody(body.toString());
            }
        }
        return httpResponse;
    }

}
