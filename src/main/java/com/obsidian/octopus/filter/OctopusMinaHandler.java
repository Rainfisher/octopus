package com.obsidian.octopus.filter;

import com.obsidian.octopus.context.Context;
import com.obsidian.octopus.context.ContextProvider;
import java.util.ArrayList;
import java.util.List;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class OctopusMinaHandler extends IoHandlerAdapter {

    private final List<OctopusMinaFilter> filters;

    public OctopusMinaHandler(String group) {
        this.filters = new ArrayList<>();

        Context context = ContextProvider.getInstance();
        List<OctopusMinaFilter> contextFilters = context.getFilters();

        for (OctopusMinaFilter octopusMinaFilter : contextFilters) {
            if (octopusMinaFilter.getName().equals(group)) {
                filters.add(octopusMinaFilter);
            }
        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            octopusMinaFilter.sessionCreated(session);
        }
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            octopusMinaFilter.sessionOpened(session);
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            octopusMinaFilter.sessionClosed(session);
        }
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            octopusMinaFilter.sessionIdle(session, status);
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            octopusMinaFilter.exceptionCaught(session, cause);
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            octopusMinaFilter.messageReceived(session, message);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            octopusMinaFilter.messageSent(session, message);
        }
    }

}
