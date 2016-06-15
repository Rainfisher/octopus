package com.obsidian.octopus.vulcan.filter;

import com.obsidian.octopus.vulcan.core.Action;

/**
 *
 * @author alex
 */
public class ResponseDefaultFilter implements ResponseFilter {

    @Override
    public Object filter(Action action) {
        return "";
    }

}
