package com.obsidian.octopus.vulcan.filter;

import javax.inject.Singleton;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Alex Chou
 */
@Singleton
public class HttpMessageFilter extends OctopusMultIoHandler {

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
        this.register(ioSession, HandlerEventType.MessageReceived, new HttpMessageReceivedEvent());
        this.register(ioSession, HandlerEventType.MessageSent, new HttpMessageSentEvent());
        this.register(ioSession, HandlerEventType.SessionIdle, new HttpSessionIdleEvent());

        super.sessionCreated(ioSession);
    }

}
