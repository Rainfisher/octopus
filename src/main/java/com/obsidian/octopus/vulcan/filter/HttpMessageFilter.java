package com.obsidian.octopus.vulcan.filter;

import com.obsidian.octopus.vulcan.codec.HttpRequestMessage;
import com.obsidian.octopus.vulcan.codec.HttpResponseMessage;
import com.obsidian.octopus.vulcan.codec.IoSessionType;
import com.obsidian.octopus.vulcan.utils.IoSessionUtils;
import javax.inject.Singleton;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.handler.demux.DemuxingIoHandler;

/**
 *
 * @author Alex Chou
 */
@Singleton
public class HttpMessageFilter extends DemuxingIoHandler {

    public HttpMessageFilter() {
        super.addReceivedMessageHandler(HttpRequestMessage.class, new HttpMessageReceivedEvent());
        super.addSentMessageHandler(HttpResponseMessage.class, new HttpMessageSentEvent());
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        if (IoSessionUtils.getSessionType(session) == IoSessionType.Http) {
            session.closeNow();
        }
    }

}
