package com.obsidian.octopus.vulcan.filter;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author alex
 */
public class OctopusMultIoHandler extends IoHandlerAdapter {

    public void register(IoSession ioSession, HandlerEventType type, HandlerEvent event) {
        ioSession.setAttributeIfAbsent(type, event);
    }

    public void trigger(IoSession ioSession, HandlerEventType type, Object o)
            throws Exception {
        HandlerEvent handlerEvent = (HandlerEvent) ioSession.getAttribute(type);
        if (handlerEvent != null) {
            handlerEvent.trigger(ioSession, o);
        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        this.trigger(session, HandlerEventType.SessionCreated, null);
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        this.trigger(session, HandlerEventType.SessionOpened, null);
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        this.trigger(session, HandlerEventType.SessionClosed, null);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        this.trigger(session, HandlerEventType.SessionIdle, status);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        this.trigger(session, HandlerEventType.ExceptionCaught, cause);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        this.trigger(session, HandlerEventType.MessageReceived, message);
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        this.trigger(session, HandlerEventType.MessageSent, message);
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        this.trigger(session, HandlerEventType.InputClosed, null);
    }

}
