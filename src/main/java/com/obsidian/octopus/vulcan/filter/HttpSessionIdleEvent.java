package com.obsidian.octopus.vulcan.filter;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author alex
 */
public class HttpSessionIdleEvent implements HandlerEvent<IdleStatus> {

    @Override
    public void trigger(IoSession ioSession, IdleStatus idleStatus)
            throws Exception {
        ioSession.closeNow();
    }

}
