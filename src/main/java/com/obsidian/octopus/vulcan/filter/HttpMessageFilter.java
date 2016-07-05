package com.obsidian.octopus.vulcan.filter;

import javax.inject.Singleton;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Alex Chou
 */
@Singleton
public class HttpMessageFilter extends IoHandlerAdapter {

    private final HandlerListener handlerListener = new HandlerListener();

    @Override
    public void sessionCreated(IoSession ioSession) throws Exception {
        HttpEventUtils.register(handlerListener, ioSession);
    }

    @Override
    public void messageReceived(IoSession ioSession, Object message)
            throws Exception {
        handlerListener.trigger(ioSession, HandlerEventType.MessageReceived, message);
    }

    @Override
    public void messageSent(IoSession ioSession, Object message) throws Exception {
        handlerListener.trigger(ioSession, HandlerEventType.MessageSent, message);
    }

    @Override
    public void sessionIdle(IoSession ioSession, IdleStatus status) throws Exception {
        handlerListener.trigger(ioSession, HandlerEventType.SessionIdle, status);
    }

}
