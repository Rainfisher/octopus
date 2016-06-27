package com.obsidian.octopus.vulcan.filter;

import com.alibaba.fastjson.JSONObject;
import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.context.ContextProvider;
import com.obsidian.octopus.filter.OctopusMinaFilter;
import com.obsidian.octopus.ioc.IocInstanceProvider;
import com.obsidian.octopus.vulcan.codec.HttpRequestMessage;
import com.obsidian.octopus.vulcan.codec.HttpResponseMessage;
import com.obsidian.octopus.vulcan.codec.IoSessionType;
import com.obsidian.octopus.vulcan.codec.RequestMessage;
import com.obsidian.octopus.vulcan.codec.ResponseMessage;
import com.obsidian.octopus.vulcan.object.ActionContext;
import com.obsidian.octopus.vulcan.object.ActionInvoker;
import com.obsidian.octopus.vulcan.object.ActionRequest;
import com.obsidian.octopus.vulcan.utils.HttpUtils;
import com.obsidian.octopus.vulcan.utils.IoSessionUtils;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Alex Chou
 */
@Singleton
public class HttpMessageFilter extends OctopusMinaFilter {

    @Override
    public void messageReceived(IoSession ioSession, Object message)
            throws Exception {
        if (message instanceof HttpRequestMessage) {
            Context context = ContextProvider.getInstance();
            IocInstanceProvider iocProvide = context.getIocProvide();
            ActionInvoker invoker = iocProvide.getInstance(ActionInvoker.class);

            HttpRequestMessage requestMessage = (HttpRequestMessage) message;
            requestMessage.setExecuteAt(System.currentTimeMillis());
            ActionContext.init();
            ActionContext.set(ActionContext.IO_SESSION, ioSession);
            ActionContext.set(ActionContext.REQUEST_MESSAGE, requestMessage);

            ioSession.setAttribute("type", IoSessionType.Http);

            JSONObject json = new JSONObject();
            Map<String, String> headers = new HashMap<>();

            for (Map.Entry<String, String> entry : requestMessage.getHeaders().entrySet()) {
                headers.put(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, String[]> entry : requestMessage.getParameters().entrySet()) {
                String key = entry.getKey();
                String[] value = entry.getValue();
                if (value.length == 1) {
                    json.put(key, value[0]);
                } else {
                    json.put(key, Arrays.asList(value));
                }
            }
            ActionContext.set(ActionContext.REQUEST_HEADERS, headers);

            ActionRequest actionRequest = new ActionRequest();
            actionRequest.setContext(requestMessage.getContext());
            actionRequest.setParameters(json);
            ActionContext.set(ActionContext.ACTION_REQUEST, actionRequest);
            ActionContext.set(ActionContext.HTTP_RESPONSE_CODE, HttpResponseMessage.HTTP_STATUS_SUCCESS);
            ActionContext.set(ActionContext.REQUEST_CONTEXT, actionRequest.getContext());

            invoker.execute();

            Object response = ActionContext.get(ActionContext.RESPONSE);
            if (response != null && response instanceof ResponseMessage) {
                IoSessionUtils.write(ioSession, actionRequest, (ResponseMessage) response);
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        RequestMessage requestMessage = ActionContext.getReqestMessage();
        if (requestMessage != null && requestMessage instanceof HttpRequestMessage) {
            HttpRequestMessage msg = (HttpRequestMessage) requestMessage;
            if (!HttpUtils.isKeepAlive(msg)) {
                session.closeOnFlush();
            }
        }
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        IoSessionType sessionType = IoSessionUtils.getSessionType(session);
        if (sessionType == IoSessionType.Http) {
            session.closeNow();
        }
    }

}
