package com.obsidian.octopus.vulcan.filter;

import com.obsidian.octopus.vulcan.codec.HttpRequestMessage;
import com.obsidian.octopus.vulcan.codec.HttpResponseMessage;
import com.obsidian.octopus.vulcan.codec.RequestMessage;
import com.obsidian.octopus.vulcan.object.ActionContext;
import com.obsidian.octopus.vulcan.utils.HttpUtils;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.MessageHandler;

/**
 *
 * @author alex
 */
public class HttpMessageSentEvent implements MessageHandler<HttpResponseMessage> {

    @Override
    public void handleMessage(IoSession ioSession, HttpResponseMessage message) throws Exception {
        RequestMessage requestMessage = ActionContext.getReqestMessage();
        if (requestMessage != null && requestMessage instanceof HttpRequestMessage) {
            HttpRequestMessage msg = (HttpRequestMessage) requestMessage;
            if (!HttpUtils.isKeepAlive(msg)) {
                ioSession.closeOnFlush();
            }
        }
    }

}
