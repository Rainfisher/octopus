package com.obsidian.octopus.filter;

import org.apache.mina.core.service.IoHandlerAdapter;

/**
 *
 * @author Alex Chou <xi.zhou at obsidian>
 */
public class OctopusMinaFilter extends IoHandlerAdapter {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
