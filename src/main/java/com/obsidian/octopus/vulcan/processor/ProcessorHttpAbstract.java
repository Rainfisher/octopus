package com.obsidian.octopus.vulcan.processor;

import com.alibaba.fastjson.JSONObject;
import com.obsidian.octopus.vulcan.codec.HttpRequestMessage;
import com.obsidian.octopus.vulcan.codec.IoSessionType;
import com.obsidian.octopus.vulcan.codec.RequestMessage;
import com.obsidian.octopus.vulcan.object.ActionContext;
import com.obsidian.octopus.vulcan.object.ActionRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author alex
 */
public abstract class ProcessorHttpAbstract implements Processor {

    @Override
    public void execute(IoSession session, RequestMessage requestMessage) throws Exception {
        session.setAttribute("type", IoSessionType.Http);
        HttpRequestMessage message = (HttpRequestMessage) requestMessage;

        JSONObject json = new JSONObject();
        Map<String, String> headers = new HashMap<>();
        
        for (Map.Entry<String, String[]> entry : message.getHeaders().entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            if (key.startsWith(HttpRequestMessage.PARAMETER_KEY)) {
                json.put(key.substring(1), value[0]);
            } else {
                headers.put(key, value[0]);
            }
        }
        ActionContext.set(ActionContext.REQUEST_HEADERS, headers);

        ActionRequest actionRequest = new ActionRequest();
        actionRequest.setContext(message.getContext());
        actionRequest.setParameters(json);
        ActionContext.set(ActionContext.ACTION_REQUEST, actionRequest);

        this.execute();
    }

    public abstract void execute() throws Exception;

}
