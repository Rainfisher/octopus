package com.obsidian.octopus.vulcan.filter;

import org.apache.mina.core.session.IoSession;

/**
 *
 * @author alex
 */
public class HandlerListener {

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

}
