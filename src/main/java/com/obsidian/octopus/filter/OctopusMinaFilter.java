package com.obsidian.octopus.filter;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

/**
 *
 * @author Alex Chou
 */
public class OctopusMinaFilter extends IoHandlerAdapter {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
    }

}
