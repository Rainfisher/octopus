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
 * @author Alex Chou
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
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            try {
                octopusMinaFilter.sessionCreated(session);
            }
            catch (Exception e) {
                octopusMinaFilter.exceptionCaught(session, e);
            }
        }
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            try {
                octopusMinaFilter.sessionOpened(session);
            }
            catch (Exception e) {
                octopusMinaFilter.exceptionCaught(session, e);
            }
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            try {
                octopusMinaFilter.sessionClosed(session);
            }
            catch (Exception e) {
                octopusMinaFilter.exceptionCaught(session, e);
            }
        }
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            try {
                octopusMinaFilter.sessionIdle(session, status);
            }
            catch (Exception e) {
                octopusMinaFilter.exceptionCaught(session, e);
            }
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            octopusMinaFilter.exceptionCaught(session, cause);
        }
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            try {
                octopusMinaFilter.messageReceived(session, message);
            }
            catch (Exception e) {
                octopusMinaFilter.exceptionCaught(session, e);
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        for (OctopusMinaFilter octopusMinaFilter : filters) {
            try {
                octopusMinaFilter.messageSent(session, message);
            }
            catch (Exception e) {
                octopusMinaFilter.exceptionCaught(session, e);
            }
        }
    }

}
