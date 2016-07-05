package com.obsidian.octopus.vulcan.filter;

import org.apache.mina.core.session.IoSession;

/**
 *
 * @author alex
 */
public class HttpEventUtils {

    public static void register(HandlerListener handlerListener, IoSession ioSession) {
        handlerListener.register(ioSession, HandlerEventType.MessageReceived, new HttpMessageReceivedEvent());
        handlerListener.register(ioSession, HandlerEventType.MessageSent, new HttpMessageSentEvent());
        handlerListener.register(ioSession, HandlerEventType.SessionIdle, new HttpMessageSentEvent());
    }

}
